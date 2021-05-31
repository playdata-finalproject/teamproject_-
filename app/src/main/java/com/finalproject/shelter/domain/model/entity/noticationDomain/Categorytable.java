package com.finalproject.shelter.domain.model.entity.noticationDomain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "categoryList")
@Builder
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class Categorytable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "categorytable")
    @JsonIgnore
    private List<Category> categoryList;
}
