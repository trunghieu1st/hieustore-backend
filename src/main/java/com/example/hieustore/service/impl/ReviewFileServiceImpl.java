package com.example.hieustore.service.impl;

import com.example.hieustore.constant.ErrorMessage;
import com.example.hieustore.domain.entity.Review;
import com.example.hieustore.domain.entity.ReviewFile;
import com.example.hieustore.exception.NotFoundException;
import com.example.hieustore.repository.ReviewFileRepository;
import com.example.hieustore.repository.ReviewRepository;
import com.example.hieustore.service.ReviewFileService;
import com.example.hieustore.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewFileServiceImpl implements ReviewFileService {

    private final ReviewFileRepository reviewFileRepository;
    private final ReviewRepository reviewRepository;
    private final UploadFileUtil uploadFileUtil;

    @Override
    public List<ReviewFile> create(String reviewId, List<MultipartFile> files) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Review.ERR_NOT_FOUND_ID, new String[]{reviewId}));
        List<ReviewFile> reviewFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            ReviewFile reviewFile = new ReviewFile();
            reviewFile.setPath(uploadFileUtil.uploadFile(file));
            reviewFile.setName(file.getOriginalFilename());
            reviewFile.setSize(file.getSize());
            reviewFile.setReview(review);
            reviewFileRepository.save(reviewFile);
            reviewFiles.add(reviewFile);
        }
        return reviewFiles;
    }
}
