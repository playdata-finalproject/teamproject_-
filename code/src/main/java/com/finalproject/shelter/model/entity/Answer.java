package com.finalproject.shelter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    @Column(columnDefinition = "Text")
    private String answerText;

    private int goodAnswer;

    private int hateAnswer;

    @CreatedDate
    private LocalDate registeredAt;

    private LocalDate unregisteredAt;

    @LastModifiedDate
    private LocalDate updatedAt;

    @LastModifiedBy
    private String updatedBy;

    @ManyToOne
    private Board board;
}
