package com.example.jwt.authprojectjwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class JwtResponse implements Serializable {

private static final long serialVersionUID = -8091879091924046844L;
private final String jwttoken;
}
