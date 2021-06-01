package com.example.jwt.authprojectjwt.controller;

import com.example.jwt.authprojectjwt.model.UserDto;
import com.example.jwt.authprojectjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserDto user){
        return ResponseEntity.ok(userService.save(user));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.update(userDto,id));
    }

    @GetMapping
    public ResponseEntity<List<?>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return ResponseEntity.ok(userService.finById(id));
    }
}
