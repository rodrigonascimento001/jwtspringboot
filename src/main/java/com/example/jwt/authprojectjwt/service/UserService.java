package com.example.jwt.authprojectjwt.service;

import com.example.jwt.authprojectjwt.exception.JwtException;
import com.example.jwt.authprojectjwt.model.User;
import com.example.jwt.authprojectjwt.model.UserDto;
import com.example.jwt.authprojectjwt.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream().map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public void delete(long id) {
        userRepository.findById(id).orElseThrow(() -> new JwtException("user not found"));
        userRepository.deleteById(id);
    }

    public UserDto findOne(String username) {
        return modelMapper.map(userRepository.findByUsername(username), UserDto.class);
    }

    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new JwtException("user not found"));
    }

    public UserDto save(UserDto user) {
        if(Optional.ofNullable(userRepository
                .findByUsername(user.getUsername()))
                .isPresent()){
                throw new JwtException("Username already exists");
        }
        User newUser = modelMapper.map(user, User.class);
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return modelMapper.map(userRepository.save(newUser), UserDto.class);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        System.out.println(b.encode("teste123"));
    }
}