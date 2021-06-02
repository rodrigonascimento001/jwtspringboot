package com.example.jwt.authprojectjwt.service;

import com.example.jwt.authprojectjwt.entity.User;
import com.example.jwt.authprojectjwt.exception.JwtException;
import com.example.jwt.authprojectjwt.exception.ResourceNotFoundException;
import com.example.jwt.authprojectjwt.model.UserDto;
import com.example.jwt.authprojectjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    public static final String RESOURCE_NOT_FOUND = "resource not found";
    @Autowired
    UserRepository repository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDto save(UserDto userDto){
        validateUsername(userDto.getUsername());
        User userNew = modelMapper.map(userDto, User.class);
        userNew.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User newUser = repository.save(userNew);
        return modelMapper.map(newUser,UserDto.class);
    }

    public void delete(long id){
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
        repository.delete(user);
    }

    public UserDto update(UserDto userDto, long id){
        User userUpdated = repository.findById(id)
                .map(user -> modelMapper.map(userDto, User.class))
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
        userUpdated.setId(id);
        repository.saveAndFlush(userUpdated);
        return modelMapper.map(userUpdated,UserDto.class);
    }

    public List<UserDto> findAll(){
        return repository.findAll().stream()
                .map(userEntity -> modelMapper.map(userEntity,UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto finById(long id){
        return repository.findById(id).map(user -> modelMapper.map(user,UserDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }

    public void validateUsername(String username){
        if(Optional.ofNullable(repository.findByUsername(username)).isPresent()){
            throw new JwtException("Invalid username");
        }
    }
}
