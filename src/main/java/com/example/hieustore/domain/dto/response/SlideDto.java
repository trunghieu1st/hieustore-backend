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
public class SlideDto extends UserDateAuditingDto {
    private String id;
    private String avatar;
    private Integer position;
    private String description;
    private Boolean status;
    private String productName;
    private String productId;
}
