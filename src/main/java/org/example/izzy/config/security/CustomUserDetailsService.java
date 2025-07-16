package org.example.izzy.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final org.example.izzy.repo.UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        log.debug("Attempting to load user by phone number: {}", phoneNumber);
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> {
            log.warn("User not found with phone number: {}", phoneNumber);
            return new UsernameNotFoundException("User not found with phone number: " + phoneNumber);
        });
    }
}
