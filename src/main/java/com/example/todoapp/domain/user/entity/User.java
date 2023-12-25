package com.example.todoapp.domain.user.entity;

import com.example.todoapp.global.entity.BaseTimeEntity;
import com.example.todoapp.global.security.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter @Entity
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        this.role = UserRoleEnum.ROLE_USER;
    }
}
