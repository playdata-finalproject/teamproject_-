package com.finalproject.shelter.domain.model.entity.userDomain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@ToString(exclude = {"roles"})
//@Accessors(chain = true)
//@EntityListeners(AuditingEntityListener.class)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long kakaoId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String identity;

    private String password;

    @Email(message = "Enter valid e-mail" )
    @Column(unique = true, nullable = false)
    private String email;
    private Boolean enabled;
    private String nickname;
    private String emailCheckToken;

    private LocalDateTime emailCheckTokenGeneratedAt;

    private LocalDate lastLoginAt;

    private int loginFailCount;

    @LastModifiedDate
    private LocalDate updatedAt;

    @LastModifiedBy
    private String updatedBy;

    @CreatedDate
    private LocalDate createdAt;

    private LocalDate uncreatedAt;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns =  @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))

    private List<Role> roles = new ArrayList<>();

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }
    public void completeSignUp() {
        this.enabled = true;
        this.createdAt = LocalDate.now();
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public boolean canSendConfirmEmail() {
        return emailCheckTokenGeneratedAt.isBefore(LocalDateTime.now().minusHours(1L));
    }

}
