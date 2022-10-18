package com.sim.jwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

// 실제 서비스시 롬복 이렇게 사용하면 안됌
@Entity
@Table(name = "user")
@Getter
@Setter // entity에 setter 생성하면 안됨
@Builder
@AllArgsConstructor         // 모든 필드 값을 파라미터로 받는 생성자 생성
@NoArgsConstructor          // 파라미터가 없는 기본 생성자를 생성
//@RequiredArgsConstructor    // final이나 @NonNull인 필드 값만 파라미터로 받는 생성자 생성
//@Data                       //  @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode을 한꺼번에 설정
public class User {

    @JsonIgnore
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;      // 활성화 여부

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
