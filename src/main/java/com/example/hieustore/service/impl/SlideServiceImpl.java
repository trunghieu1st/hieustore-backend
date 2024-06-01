package com.example.hieustore.service.impl;

import com.example.hieustore.constant.ErrorMessage;
import com.example.hieustore.constant.MessageConstant;
import com.example.hieustore.constant.SortByDataConstant;
import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.pagination.PagingMeta;
import com.example.hieustore.domain.dto.request.SlideRequestDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;
import com.example.hieustore.domain.dto.response.SlideDto;
import com.example.hieustore.domain.entity.Product;
import com.example.hieustore.domain.entity.Slide;
import com.example.hieustore.domain.mapper.SlideMapper;
import com.example.hieustore.exception.AlreadyExistException;
import com.example.hieustore.exception.NotFoundException;
import com.example.hieustore.repository.ProductRepository;
import com.example.hieustore.repository.SlideRepository;
import com.example.hieustore.service.SlideService;
import com.example.hieustore.util.PaginationUtil;
import com.example.hieustore.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SlideServiceImpl implements SlideService {
    private final SlideRepository slideRepository;
    private final SlideMapper slideMapper;
    private final ProductRepository productRepository;
    private final UploadFileUtil uploadFileUtil;

    @Override
    public SlideDto getById(String id) {
        Slide slide = slideRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Slide.ERR_NOT_FOUND_ID, new String[]{id}));
        return slideMapper.mapSlideToSlideDto(slide);
    }

    @Override
    public PaginationResponseDto<SlideDto> getAll(PaginationFullRequestDto paginationFullRequestDto) {
        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.SLIDE);
        Page<Slide> slidePage = slideRepository.getAll(pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.SLIDE, slidePage);

        List<SlideDto> slideDtoList = slideMapper.mapSlideToSlideDto(slidePage.getContent());
        return new PaginationResponseDto<>(meta, slideDtoList);
    }

    @Override
    public PaginationResponseDto<SlideDto> getByStatus(PaginationFullRequestDto paginationFullRequestDto, Boolean status) {
        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.SLIDE);
        Page<Slide> slidePage = slideRepository.getByStatus(status, pageable);

        PagingMeta meta = PaginationUtil.buildPagingMeta(paginationFullRequestDto, SortByDataConstant.SLIDE, slidePage);
        List<SlideDto> slideDtoList = slideMapper.mapSlideToSlideDto(slidePage.getContent());
        return new PaginationResponseDto<>(meta, slideDtoList);
    }

    @Override
    public SlideDto create(SlideRequestDto createDto) {
        Product product = productRepository.findById(createDto.getProductId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID,
                        new String[]{createDto.getProductId()}));

        if (slideRepository.findByPosition(createDto.getPosition()) != null) {
            throw new AlreadyExistException(ErrorMessage.Slide.ERR_POSITION_ALREADY_EXIST, new String[]{createDto.getPosition().toString()});
        }
        Slide slide = slideMapper.mapSlideRequestDtoToSlide(createDto);
        slide.setAvatar(uploadFileUtil.uploadImage(createDto.getAvatar()));
        slide.setProduct(product);
        return slideMapper.mapSlideToSlideDto(slideRepository.save(slide));
    }

    @Override
    public SlideDto update(String id, SlideRequestDto updateDto) {
        Slide slide = slideRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Slide.ERR_NOT_FOUND_ID, new String[]{id}));
        Product product = productRepository.findById(updateDto.getProductId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID,
                        new String[]{updateDto.getProductId()}));
        slideMapper.updateSlide(slide, updateDto);

        MultipartFile multipartFile = updateDto.getAvatar();
        if (multipartFile != null && !multipartFile.isEmpty()) {
            uploadFileUtil.destroyImageWithUrl(slide.getAvatar());
            slide.setAvatar(uploadFileUtil.uploadImage(updateDto.getAvatar()));
        }
        slide.setProduct(product);
        return slideMapper.mapSlideToSlideDto(slideRepository.save(slide));
    }

    @Override
    public CommonResponseDto deleteById(String id) {
        Slide slide = slideRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Slide.ERR_NOT_FOUND_ID, new String[]{id}));
        slideRepository.delete(slide);
        return new CommonResponseDto(true, MessageConstant.DELETE_SLIDE_SUCCESSFULLY);
    }
}
