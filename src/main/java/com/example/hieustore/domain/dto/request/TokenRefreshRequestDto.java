package com.example.hieustore.domain.dto.request;

import com.example.hieustore.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TokenRefreshRequestDto {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String refreshToken;

}
