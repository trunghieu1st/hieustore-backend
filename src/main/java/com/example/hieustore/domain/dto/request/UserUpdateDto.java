package com.example.hieustore.domain.dto.request;

import com.example.hieustore.constant.ErrorMessage;
import com.example.hieustore.constant.RegexConstant;
import com.example.hieustore.validator.annotation.ValidDate;
import com.example.hieustore.validator.annotation.ValidFileImage;
import com.example.hieustore.validator.annotation.ValidPhone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDto {
    private String fullName;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @Pattern(regexp = RegexConstant.USERNAME, message = ErrorMessage.INVALID_FORMAT_USERNAME)
    private String username;
    private Boolean gender;
    @ValidDate
    private String birthday;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @ValidPhone
    private String phone;
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @Email(message = ErrorMessage.INVALID_FORMAT_EMAIL)
    private String email;
    @ValidFileImage
    private MultipartFile avatar;

}
