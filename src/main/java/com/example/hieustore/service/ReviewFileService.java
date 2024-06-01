package com.example.hieustore.service;

import com.example.hieustore.domain.entity.ReviewFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewFileService {

    List<ReviewFile> create(String reviewId, List<MultipartFile> files);

}
