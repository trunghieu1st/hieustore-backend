package com.example.hieustore.service.impl;

import com.example.hieustore.constant.ErrorMessage;
import com.example.hieustore.constant.MessageConstant;
import com.example.hieustore.domain.dto.request.ProductOptionCreateDto;
import com.example.hieustore.domain.dto.request.ProductOptionUpdateDto;
import com.example.hieustore.domain.dto.response.CommonResponseDto;
import com.example.hieustore.domain.dto.response.ProductOptionDto;
import com.example.hieustore.domain.entity.Category;
import com.example.hieustore.domain.entity.Product;
import com.example.hieustore.domain.entity.ProductOption;
import com.example.hieustore.domain.mapper.ProductOptionMapper;
import com.example.hieustore.exception.NotFoundException;
import com.example.hieustore.repository.CategoryRepository;
import com.example.hieustore.repository.ProductOptionRepository;
import com.example.hieustore.repository.ProductRepository;
import com.example.hieustore.service.ProductOptionService;
import com.example.hieustore.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {
    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;
    private final ProductOptionMapper productOptionMapper;
    private final UploadFileUtil uploadFileUtil;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductOptionDto getById(String id) {
        ProductOption productOption = productOptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ProductOption.ERR_NOT_FOUND_ID, new String[]{id}));
        return productOptionMapper.mapProductOptionToProductOptionDto(productOption);
    }

    @Override
    public List<ProductOptionDto> getAllByProductId(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{productId}));

        List<ProductOption> productOptions = productOptionRepository.getAllByProductId(productId);
        return productOptionMapper.mapProductOptionsToProductOptionDtos(productOptions);
    }
    @Override
    public List<ProductOptionDto> getAll() {
        List<ProductOption> productOptions = productOptionRepository.getAll();
        return productOptionMapper.mapProductOptionsToProductOptionDtos(productOptions);
    }

    @Override
    public List<ProductOptionDto> getAllByCategoryId(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID, new String[]{categoryId}));
        List<Product> products = productRepository.findAllByCategory(category);
        List<ProductOption> optionsByCategory = new ArrayList<ProductOption>();
        for (Product product: products){
            List<ProductOption> options1 = productOptionRepository.getAllByProductId(product.getId());
            for (ProductOption op: options1){
                optionsByCategory.add(op);
            }
        }
        return productOptionMapper.mapProductOptionsToProductOptionDtos(optionsByCategory);
    }
    @Override
    public ProductOptionDto create(ProductOptionCreateDto productOptionCreateDto) {
        Product product = productRepository.findById(productOptionCreateDto.getProductId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{productOptionCreateDto.getProductId()}));
        ProductOption productOption = productOptionMapper.mapProductOptionCreateDtoToProductOption(productOptionCreateDto);
        productOption.setImage(uploadFileUtil.uploadImage(productOptionCreateDto.getImage()));
        productOption.setProduct(product);
        return productOptionMapper.mapProductOptionToProductOptionDto(productOptionRepository.save(productOption));
    }

    @Override
    public ProductOptionDto updateById(String id, ProductOptionUpdateDto productOptionUpdateDto) {
        ProductOption productOption = productOptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ProductOption.ERR_NOT_FOUND_ID, new String[]{id}));
        productOptionMapper.update(productOption, productOptionUpdateDto);
        if (productOptionUpdateDto.getImage() != null) {
            uploadFileUtil.destroyImageWithUrl(productOption.getImage());
            productOption.setImage(uploadFileUtil.uploadImage(productOptionUpdateDto.getImage()));
        }
        return productOptionMapper.mapProductOptionToProductOptionDto(productOptionRepository.save(productOption));
    }

    @Override
    public CommonResponseDto deleteById(String id) {
        ProductOption productOption = productOptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ProductOption.ERR_NOT_FOUND_ID, new String[]{id}));
        productOptionRepository.delete(productOption);
        return new CommonResponseDto(true, MessageConstant.DELETE_PRODUCT_OPTION_SUCCESSFULLY);
    }

}
