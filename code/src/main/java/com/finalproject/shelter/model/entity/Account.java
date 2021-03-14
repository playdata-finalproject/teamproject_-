package com.finalproject.shelter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@ToString(exclude = {"categoryList","answerList"})
//@Accessors(chain = true)
//@EntityListeners(AuditingEntityListener.class)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @NotNull(message = "Can't be empty")
//    @Size(min = 2, max = 30, message = "Must be more than 2 and less than 30 symbols")
    private Long id;

    private Long kakaoId;

    private String nickname;

//    @NotNull(message = "Can't be empty")
//    @Size(min = 2, max = 30, message = "Must be more than 2 and less than 30 symbols")
    private String identity;
//    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})",
//            message = "Enter valid password")
    private String password;
//    private String password2;

    //private String password2;
    private String username;

    @Email(message = "Enter valid e-mail" )
    private String email;

    private Boolean enabled;

    private String emailCheckToken;
    private LocalDateTime emailCheckTokenGeneratedAt;

    private LocalDateTime lastLoginAt;


    private int loginFailCount; // integer의 용량 이 int보다 큼 바꿀라면 나중에 바꾸자

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime uncreatedAt;

    //private Long adminUserId;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns =  @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles = new ArrayList<>();

//    @AssertTrue(message = "Passwords should match")
//    public boolean isPasswordsEqual() {
//        return (password == null) ? false : password.equals(password2);
//    }

    //@OneToMany(mappedBy = "User")
    //private List<Board> Board = new ArrayList<>();

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public void completeSignUp() {
        enabled = true;
        createdAt = LocalDateTime.now();
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public boolean canSendConfirmEmail() {
        return emailCheckTokenGeneratedAt.isBefore(LocalDateTime.now().minusHours(1L));
    }





}
