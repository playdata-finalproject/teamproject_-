package com.finalproject.shelter.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"categoryList","answerList"})
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long kakaoId;

    private String userId;

    private String password;

    private String name;

    private String email;

    private LocalDateTime lastLoginAt;

    private int loginFailCount; // integer의 용량 이 int보다 큼 바꿀라면 나중에 바꾸자

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime uncreatedAt;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "useranwser")
    private List<Answer> answerList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "usercategory")
    private List<Category> categoryList;
}
