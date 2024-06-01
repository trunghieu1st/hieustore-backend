package com.example.hieustore.domain.entity;

import com.example.hieustore.domain.entity.common.UserDateAuditing;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "news")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class News extends UserDateAuditing {
    @Id
    @GeneratedValue(generator = "custom-random-id")
    @GenericGenerator(name = "custom-random-id", strategy = "com.example.hieustore.domain.entity.CustomRandomId")
    @Column(insertable = false, updatable = false, nullable = false, length = 7)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String avatar;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_CATEGORY_NEWS"))
    private Category category;

}
