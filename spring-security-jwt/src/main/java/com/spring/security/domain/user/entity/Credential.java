package com.spring.security.domain.user.entity;

import com.spring.security.global.common.BaseTimeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@Table(name = "credentials")
@SQLDelete(sql = "UPDATE credentials SET deleted_at = NOW() where id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(hidden = true)
public class Credential extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int4", nullable = false)
    private Long id;

    @Column(name = "password", nullable = false)
    private String password;

    @Builder
    public Credential(String hashedPassword) {
        this.password = hashedPassword;
    }
}
