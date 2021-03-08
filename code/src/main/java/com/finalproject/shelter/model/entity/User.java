package com.finalproject.shelter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"boardList"})
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long kakaoId;

    private String identity;

    private String nickname;

    private String password;

    private String username;

    private String email;

    private boolean enabled;

    private LocalDateTime lastLoginAt;

    @CreatedDate
    private LocalDate createdAt;

    private LocalDate uncreatedAt;

    @LastModifiedDate
    private LocalDate updatedAt;

    @LastModifiedBy
    private String updatedBy;

    @ManyToOne
    private AdminUser adminUser;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    @JsonIgnore
    private List<Board> boardList;
}
