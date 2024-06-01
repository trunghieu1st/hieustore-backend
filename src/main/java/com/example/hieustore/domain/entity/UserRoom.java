package com.example.hieustore.domain.entity;

import com.example.hieustore.domain.entity.common.DateAuditing;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_rooms")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRoom extends DateAuditing {
    @EmbeddedId
    private UserRoomId id;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
}