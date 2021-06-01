package com.example.jwt.authprojectjwt.controller;

import com.example.jwt.authprojectjwt.config.JwtTokenUtil;
import com.example.jwt.authprojectjwt.model.JwtRequest;
import com.example.jwt.authprojectjwt.model.JwtResponse;
import com.example.jwt.authprojectjwt.model.JwtTokenRequest;
import com.example.jwt.authprojectjwt.service.AuthorizationService;
import com.example.jwt.authprojectjwt.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authorizationService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping("/getUsernameFromToken")
    public ResponseEntity<?> getUsernameFromToken(@RequestBody JwtTokenRequest jwtTokenRequest){
        return ResponseEntity.ok(jwtTokenUtil.getUsernameFromToken(jwtTokenRequest.getToken()));
    }


}