package com.example.jwt.authprojectjwt.controller;

import com.example.jwt.authprojectjwt.config.TokenProvider;
import com.example.jwt.authprojectjwt.model.AuthToken;
import com.example.jwt.authprojectjwt.model.LoginUser;
import com.example.jwt.authprojectjwt.model.User;
import com.example.jwt.authprojectjwt.model.UserDto;
import com.example.jwt.authprojectjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('USER')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getOne(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto user){
        return ResponseEntity.ok(userService.save(user));
    }
}