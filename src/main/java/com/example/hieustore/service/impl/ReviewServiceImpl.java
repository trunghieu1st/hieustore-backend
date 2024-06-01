package com.example.hieustore.service.impl;

import com.example.hieustore.constant.ErrorMessage;
import com.example.hieustore.constant.MessageConstant;
import com.example.hieustore.constant.SortByDataConstant;
import com.example.hieustore.constant.StatusConstant;
import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.pagination.PagingMeta;
import com.example.hieustore.domain.dto.request.ReviewCreateDto;
import com.example.hieustore.domain.dto.request.ReviewUpdateDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;
import com.example.hieustore.domain.dto.response.ReviewDto;
import com.example.hieustore.domain.entity.OrderDetail;
import com.example.hieustore.domain.entity.Review;
import com.example.hieustore.domain.entity.ReviewFile;
import com.example.hieustore.domain.entity.User;
import com.example.hieustore.domain.mapper.ReviewMapper;
import com.example.hieustore.exception.AlreadyExistException;
import com.example.hieustore.exception.ForbiddenException;
import com.example.hieustore.exception.NotFoundException;
import com.example.hieustore.repository.*;
import com.example.hieustore.service.ReviewFileService;
import com.example.hieustore.service.ReviewService;
import com.example.hieustore.util.PaginationUtil;
import com.example.hieustore.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ReviewFileRepository reviewFileRepository;
    private final ReviewFileService reviewFileService;
    private final ReviewMapper reviewMapper;
    private final UploadFileUtil uploadFileUtil;

    @Override
    public ReviewDto getById(String id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Review.ERR_NOT_FOUND_ID, new String[]{id}));
        return reviewMapper.mapReviewToReviewDto(review);
    }

    @Override
    public PaginationResponseDto<ReviewDto> search(String productId, Integer star, PaginationFullRequestDto paginationFullRequestDto) {
        if (productId != null) {
            productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{productId}));
        }
        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.REVIEW);
        Page<Review> reviewPage = reviewRepository.search(productId, star, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.REVIEW, reviewPage);
        List<ReviewDto> reviewDtos = reviewMapper.mapReviewsToReviewDtos(reviewPage.getContent());
        return new PaginationResponseDto<>(meta, reviewDtos);
    }

    @Override
    public ReviewDto create(String userId, ReviewCreateDto reviewCreateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
        OrderDetail orderDetail = orderDetailRepository.findById(reviewCreateDto.getOrderDetailId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Order.ERR_NOT_FOUND_ID, new String[]{reviewCreateDto.getOrderDetailId()}));
        if (!orderDetail.getOrder().getStatus().getId().equals(StatusConstant.DELIVERED.getId())) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }
        if (orderDetail.getReview() != null) {
            throw new AlreadyExistException(ErrorMessage.Review.ERR_ALREADY_EXIST_WITH_ORDER_DETAIL);
        }
        if (!orderDetail.getCreatedBy().equals(userId)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }
        Review review = reviewMapper.mapReviewCreateDtoToReview(reviewCreateDto);
        review.setOrderDetail(orderDetail);
        review.setUser(user);
        reviewRepository.save(review);
        List<ReviewFile> reviewFiles = new ArrayList<>();
        if (reviewCreateDto.getFiles() != null && !reviewCreateDto.getFiles().isEmpty()) {
            reviewFiles = reviewFileService.create(review.getId(), reviewCreateDto.getFiles());
        }
        review.setFiles(reviewFiles);
        return reviewMapper.mapReviewToReviewDto(reviewRepository.save(review));
    }

    @Override
    public ReviewDto updateById(String userId, String id, ReviewUpdateDto reviewUpdateDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Review.ERR_NOT_FOUND_ID, new String[]{id}));
        if (!review.getCreatedBy().equals(userId)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
        }
        reviewMapper.update(review, reviewUpdateDto);
        if (review.getFiles() != null && !review.getFiles().isEmpty()) {
            for (ReviewFile reviewFile : review.getFiles()) {
                uploadFileUtil.destroyFileWithUrl(reviewFile.getPath());
            }
            reviewFileRepository.deleteAllByReviewId(review.getId());
        }
        List<ReviewFile> reviewFiles = new ArrayList<>();
        if (reviewUpdateDto.getFiles() != null && !reviewUpdateDto.getFiles().isEmpty()) {
            reviewFiles = reviewFileService.create(review.getId(), reviewUpdateDto.getFiles());
        }
        review.setFiles(reviewFiles);
        return reviewMapper.mapReviewToReviewDto(reviewRepository.save(review));
    }

    @Override
    public CommonResponseDto deleteById(String userId, String id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Review.ERR_NOT_FOUND_ID, new String[]{id}));
        if (!review.getCreatedBy().equals(userId)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
        }
        review.getOrderDetail().setReview(null);
        reviewRepository.delete(review);
        return new CommonResponseDto(true, MessageConstant.DELETE_REVIEW_SUCCESSFULLY);
    }
}
