package com.example.hieustore.domain.dto.response;

import com.example.hieustore.domain.dto.common.UserDateAuditingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentMethodsDto extends UserDateAuditingDto {
    private String id;
    private String code;
    private String name;
    private String avatar;
    private String description;
    private Boolean status;
}
