package com.example.hieustore.domain.entity;

import com.example.hieustore.domain.entity.common.UserDateAuditing;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "payment_methods")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentMethods extends UserDateAuditing {
    @Id
    @GeneratedValue(generator = "custom-random-id")
    @GenericGenerator(name = "custom-random-id", strategy = "com.example.hieustore.domain.entity.CustomRandomId")
    @Column(insertable = false, updatable = false, nullable = false, length = 7)
    private String id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String avatar;

    @Column(nullable = false)
    private Boolean status;
}
