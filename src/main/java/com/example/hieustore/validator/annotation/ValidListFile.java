package com.example.hieustore.validator.annotation;


import com.example.hieustore.validator.ListFileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Constraint(validatedBy = {ListFileValidator.class})
public @interface ValidListFile {

    String message() default "invalid.number.file";

    String fileTypeNotAllowedMessage() default "invalid.file.type";

    String fileSizeExceededMessage() default "invalid.file.size";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
