package com.example.hieustore.service.impl;

import com.example.hieustore.constant.ErrorMessage;
import com.example.hieustore.domain.entity.User;
import com.example.hieustore.exception.NotFoundException;
import com.example.hieustore.repository.UserRepository;
import com.example.hieustore.security.UserPrincipal;
import com.example.hieustore.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USERNAME,
                        new String[]{username}));
        return UserPrincipal.create(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{id}));
        return UserPrincipal.create(user);
    }

}
