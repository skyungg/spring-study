package com.example.demo.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 100)
    @NonNull
    private String socialId;

    @Column(length = 20)
    @NonNull
    private String name;

    @Column(length = 10)
    @NonNull
    private String platform;
}
