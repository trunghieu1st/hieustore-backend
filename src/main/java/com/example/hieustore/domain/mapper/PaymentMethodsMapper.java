package com.example.hieustore.domain.mapper;

import com.example.hieustore.domain.dto.request.PaymentMethodsRequestDto;
import com.example.hieustore.domain.dto.response.PaymentMethodsDto;
import com.example.hieustore.domain.entity.PaymentMethods;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentMethodsMapper {
    @Mappings({
            @Mapping(target = "avatar", ignore = true)
    })
    PaymentMethods mapPaymentMethodsCreateDtoToPaymentMethods(PaymentMethodsRequestDto createDto);

    @Mappings({
            @Mapping(target = "avatar", ignore = true)
    })
    void updatePaymentMethods(@MappingTarget PaymentMethods paymentMethods, PaymentMethodsRequestDto updateDto);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "code", source = "code"),
            @Mapping(target = "avatar", source = "avatar"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "status", source = "status"),
    })
    PaymentMethodsDto mapPaymentMethodsToPaymentMethodsDto(PaymentMethods paymentMethods);

    List<PaymentMethodsDto> mapPaymentMethodsToPaymentMethodsDto(List<PaymentMethods> paymentMethods);

}
