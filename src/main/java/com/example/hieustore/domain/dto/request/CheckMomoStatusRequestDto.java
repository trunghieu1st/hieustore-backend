package com.example.hieustore.domain.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckMomoStatusRequestDto {
    private String orderId;
}
