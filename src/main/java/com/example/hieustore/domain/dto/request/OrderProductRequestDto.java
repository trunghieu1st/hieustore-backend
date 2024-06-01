package com.example.hieustore.domain.dto.request;

import com.example.hieustore.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderProductRequestDto {
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String productOptionId;
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private Integer quantity;
}
