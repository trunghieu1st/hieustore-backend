package com.example.hieustore.domain.dto.request;

import com.example.hieustore.constant.ErrorMessage;
import com.example.hieustore.constant.RegexConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangePasswordRequestDto {
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String currentPassword;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @Pattern(regexp = RegexConstant.PASSWORD, message = ErrorMessage.INVALID_FORMAT_PASSWORD)
    private String newPassword;

}
