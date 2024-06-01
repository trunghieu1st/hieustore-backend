package com.example.hieustore.domain.entity;

import com.example.hieustore.domain.entity.common.UserDateAuditing;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "review_files")
public class ReviewFile extends UserDateAuditing {
    @Id
    @GeneratedValue(generator = "custom-random-id")
    @GenericGenerator(name = "custom-random-id", strategy = "com.example.hieustore.domain.entity.CustomRandomId")
    @Column(insertable = false, updatable = false, nullable = false, length = 7)
    private String id;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long size;

    @ManyToOne
    @JoinColumn(name = "review_id", foreignKey = @ForeignKey(name = "FK_FILE_REVIEW"))
    private Review review;

}
