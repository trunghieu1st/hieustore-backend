package com.example.hieustore.domain.dto.request;

import com.example.hieustore.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryRequestDto {
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String name;

//  @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    private MultipartFile avatar;

    private String description;
}
