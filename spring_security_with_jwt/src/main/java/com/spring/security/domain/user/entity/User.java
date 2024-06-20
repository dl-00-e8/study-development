package com.spring.security.domain.user.entity;

import com.spring.security.domain.user.enumerate.UserRole;
import com.spring.security.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() where id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int4", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credential_id", referencedColumnName = "id")
    private Credential credential;

    @Column(name = "name", columnDefinition = "varchar(255)", nullable = false)
    private String name;

    @Column(name = "email", columnDefinition = "varchar(255)", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "varchar(255)", nullable = false)
    private UserRole role;

    @Builder
    public User(Long id, Credential credential, String name, String email) {
        this.id = id;
        this.credential = credential;
        this.name = name;
        this.email = email;
        this.role = UserRole.GENERAL;
    }
}
