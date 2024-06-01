package com.example.hieustore.domain.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ZaloPayRequestDto {
    private String appUser;
    private int amount;
    private String description;
    private String bankCode;
}
