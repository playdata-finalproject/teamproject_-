package com.finalproject.shelter.model.network.request;

import com.finalproject.shelter.model.entity.Category;
import com.finalproject.shelter.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUserApiRequest {

    private Long id;

    private String name;

    private String nickname;

    private String password;

    private LocalDateTime lastLoginAt;

    private LocalDateTime createdAt;

    private LocalDateTime uncreatedAt;

    private LocalDateTime updatedAt;

    private String updatedBy;

}
