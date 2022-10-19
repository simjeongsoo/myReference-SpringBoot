package com.sim.jwt.service;

import com.sim.jwt.dto.UserDto;
import com.sim.jwt.entity.Authority;
import com.sim.jwt.entity.User;
import com.sim.jwt.repository.UserRepository;
import com.sim.jwt.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // UserRepository, PasswordEncoder 주입
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User signup(UserDto userDto) {
        //--회원가입 로직을 수행하는 메서드--//

        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            // username 중복성 체크
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        // Authority 정보 생성
        // signup() 메서드를 통해 가입한 회원은 default 로 ROLE_USER 를 가지게 됨
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        // userDto 에 있는 유저 정보를 생성
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        // DB 에 유저 정보 저장
        return userRepository.save(user);
    }

    //--유저, 권한정보를 가져오는 두가지 메서드
    // 이 두가지 메서드의 허용권한을 다르게 하여 권한 검증에 대한 부분을 테스트--//
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        // username 을 기준으로 정보를 가져옴
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        // SecurityContext에 저장된 username의 정보만 가져옴
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }
}
