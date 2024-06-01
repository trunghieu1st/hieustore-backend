package com.example.hieustore.service.impl;

import com.example.hieustore.constant.ErrorMessage;
import com.example.hieustore.constant.MessageConstant;
import com.example.hieustore.constant.SortByDataConstant;
import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.pagination.PagingMeta;
import com.example.hieustore.domain.dto.request.NewsRequestDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;
import com.example.hieustore.domain.dto.response.NewsDto;
import com.example.hieustore.domain.entity.Category;
import com.example.hieustore.domain.entity.News;
import com.example.hieustore.domain.mapper.NewsMapper;
import com.example.hieustore.exception.NotFoundException;
import com.example.hieustore.repository.CategoryRepository;
import com.example.hieustore.repository.NewsRepository;
import com.example.hieustore.service.NewsService;
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
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;
    private final UploadFileUtil uploadFileUtil;
    private final NewsMapper newsMapper;

    @Override
    public NewsDto getById(String id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID, new String[]{id}));
        return newsMapper.mapNewsToNewsDto(news);
    }

    @Override
    public PaginationResponseDto<NewsDto> getByStatus(PaginationFullRequestDto paginationFullRequestDto, Boolean status) {
        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.NEWS);
        Page<News> newsPage = newsRepository.getByStatus(status, pageable);

        PagingMeta meta = PaginationUtil.buildPagingMeta(paginationFullRequestDto, SortByDataConstant.NEWS, newsPage);
        List<NewsDto> newsDtoList = newsMapper.mapNewsToNewsDto(newsPage.getContent());
        return new PaginationResponseDto<>(meta, newsDtoList);
    }

    @Override
    public PaginationResponseDto<NewsDto> getAll(PaginationFullRequestDto paginationFullRequestDto) {
        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.NEWS);
        Page<News> newsPage = newsRepository.getAll(pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.NEWS, newsPage);

        List<NewsDto> newsDto = newsMapper.mapNewsToNewsDto(newsPage.getContent());
        return new PaginationResponseDto<>(meta, newsDto);
    }

    @Override
    public NewsDto create(NewsRequestDto createDto) {
        Category category = categoryRepository.findById(createDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID,
                        new String[]{createDto.getCategoryId()}));

        News news = newsMapper.mapNewsCreateDtoToNews(createDto);
        news.setAvatar(uploadFileUtil.uploadImage(createDto.getAvatar()));
        news.setCategory(category);
        return newsMapper.mapNewsToNewsDto(newsRepository.save(news));
    }

    @Override
    public NewsDto update(String id, NewsRequestDto updateDto) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.News.ERR_NOT_FOUND_ID, new String[]{id}));
        Category category = categoryRepository.findById(updateDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID,
                        new String[]{updateDto.getCategoryId()}));
        newsMapper.updateNews(news, updateDto);

        MultipartFile multipartFile = updateDto.getAvatar();
        if (multipartFile != null && !multipartFile.isEmpty()) {
            uploadFileUtil.destroyImageWithUrl(news.getAvatar());
            news.setAvatar(uploadFileUtil.uploadImage(updateDto.getAvatar()));
            news.setCategory(category);
        }
        return newsMapper.mapNewsToNewsDto(newsRepository.save(news));
    }

    @Override
    public CommonResponseDto deleteById(String id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.News.ERR_NOT_FOUND_ID, new String[]{id}));
        newsRepository.delete(news);
        return new CommonResponseDto(true, MessageConstant.DELETE_NEWS_SUCCESSFULLY);
    }
}
