package com.finalproject.shelter.domainModelLayer.model.entity.noticationDomain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.shelter.domainModelLayer.model.entity.userDomain.Account;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "answerList")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    private String nickname;

    @Column(columnDefinition = "Text")
    @NotBlank(message = "내용을 입력해주세요")
    private String contents;

    private int viewBoard;

    private int goodBoard;

    private int hateBoard;

    @CreatedDate
    private LocalDate registeredAt;

    private LocalDate unregisteredAt;

    @LastModifiedDate
    private LocalDate updatedAt;

    @LastModifiedBy
    private String updatedBy;

    @ManyToOne
    @JsonIgnore
    private Category category;

    @ManyToOne
    @JsonIgnore
    private Account user;

    @OneToMany(mappedBy = "board",fetch = FetchType.LAZY )
    @JsonIgnore
    private List<Answer> answerList;
}
