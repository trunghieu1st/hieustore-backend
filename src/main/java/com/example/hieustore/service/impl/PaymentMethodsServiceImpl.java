package com.example.hieustore.service.impl;

import com.example.hieustore.constant.ErrorMessage;
import com.example.hieustore.constant.MessageConstant;
import com.example.hieustore.constant.SortByDataConstant;
import com.example.hieustore.domain.dto.pagination.PaginationFullRequestDto;
import com.example.hieustore.domain.dto.pagination.PaginationResponseDto;
import com.example.hieustore.domain.dto.pagination.PagingMeta;
import com.example.hieustore.domain.dto.request.PaymentMethodsRequestDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;
import com.example.hieustore.domain.dto.response.PaymentMethodsDto;
import com.example.hieustore.domain.entity.PaymentMethods;
import com.example.hieustore.domain.mapper.PaymentMethodsMapper;
import com.example.hieustore.exception.NotFoundException;
import com.example.hieustore.repository.PaymentMethodsRepository;
import com.example.hieustore.service.PaymentMethodsService;
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
public class PaymentMethodsServiceImpl implements PaymentMethodsService {
    private final PaymentMethodsRepository paymentMethodsRepository;
    private final UploadFileUtil uploadFileUtil;
    private final PaymentMethodsMapper paymentMethodsMapper;

    @Override
    public PaymentMethodsDto getById(String id) {
        PaymentMethods paymentMethods = paymentMethodsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.PaymentMethods.ERR_NOT_FOUND_ID, new String[]{id}));
        return paymentMethodsMapper.mapPaymentMethodsToPaymentMethodsDto(paymentMethods);
    }

    @Override
    public PaginationResponseDto<PaymentMethodsDto> getByStatus(PaginationFullRequestDto paginationFullRequestDto, Boolean status) {
        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.PAYMENT_METHODS);
        Page<PaymentMethods> paymentMethodsPage = paymentMethodsRepository.getByStatus(status, pageable);

        PagingMeta meta = PaginationUtil.buildPagingMeta(paginationFullRequestDto, SortByDataConstant.PAYMENT_METHODS, paymentMethodsPage);
        List<PaymentMethodsDto> paymentMethodsDtoList = paymentMethodsMapper.mapPaymentMethodsToPaymentMethodsDto(paymentMethodsPage.getContent());
        return new PaginationResponseDto<>(meta, paymentMethodsDtoList);
    }

    @Override
    public PaginationResponseDto<PaymentMethodsDto> getAll(PaginationFullRequestDto paginationFullRequestDto) {
        Pageable pageable = PaginationUtil.buildPageable(paginationFullRequestDto, SortByDataConstant.PAYMENT_METHODS);
        Page<PaymentMethods> PaymentMethodsPage = paymentMethodsRepository.getAll(pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.PAYMENT_METHODS, PaymentMethodsPage);

        List<PaymentMethodsDto> paymentMethodsDto = paymentMethodsMapper.mapPaymentMethodsToPaymentMethodsDto(PaymentMethodsPage.getContent());
        return new PaginationResponseDto<>(meta, paymentMethodsDto);
    }

    @Override
    public PaymentMethodsDto create(PaymentMethodsRequestDto createDto) {
        PaymentMethods PaymentMethods = paymentMethodsMapper.mapPaymentMethodsCreateDtoToPaymentMethods(createDto);
        PaymentMethods.setAvatar(uploadFileUtil.uploadImage(createDto.getAvatar()));
        return paymentMethodsMapper.mapPaymentMethodsToPaymentMethodsDto(paymentMethodsRepository.save(PaymentMethods));
    }

    @Override
    public PaymentMethodsDto update(String id, PaymentMethodsRequestDto updateDto) {
        PaymentMethods paymentMethods = paymentMethodsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.PaymentMethods.ERR_NOT_FOUND_ID, new String[]{id}));
        paymentMethodsMapper.updatePaymentMethods(paymentMethods, updateDto);

        MultipartFile multipartFile = updateDto.getAvatar();
        if (multipartFile != null && !multipartFile.isEmpty()) {
            uploadFileUtil.destroyImageWithUrl(paymentMethods.getAvatar());
            paymentMethods.setAvatar(uploadFileUtil.uploadImage(updateDto.getAvatar()));
        }
        return paymentMethodsMapper.mapPaymentMethodsToPaymentMethodsDto(paymentMethodsRepository.save(paymentMethods));
    }

    @Override
    public CommonResponseDto deleteById(String id) {
        PaymentMethods PaymentMethods = paymentMethodsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.PaymentMethods.ERR_NOT_FOUND_ID, new String[]{id}));
        paymentMethodsRepository.delete(PaymentMethods);
        return new CommonResponseDto(true, MessageConstant.DELETE_PAYMENT_METHODS_SUCCESSFULLY);
    }
}
