package com.example.hieustore.domain.dto.response;

import com.example.hieustore.domain.dto.common.DateAuditingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomDto extends DateAuditingDto {
    private String id;
}
