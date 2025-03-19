package com.kueshi.api_my_finances.user.controllers;

import com.kueshi.api_my_finances.config.errors.ErrorResponse;
import com.kueshi.api_my_finances.user.documents.User;
import com.kueshi.api_my_finances.user.dto.UserRegisterDTO;
import com.kueshi.api_my_finances.user.dto.UserResponseDTO;
import com.kueshi.api_my_finances.user.dto.UserUpdateDTO;
import com.kueshi.api_my_finances.user.enums.UserRole;
import com.kueshi.api_my_finances.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRegisterDTO user) {
        var registeredUser = this.userService.register(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .replacePath("/user/details/{cpf}")
                .buildAndExpand(registeredUser.cpf())
                .toUri();
        return ResponseEntity.created(location).body(registeredUser);
    }

    @PutMapping("/update/{cpf}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String cpf, @RequestBody @Valid UserUpdateDTO userUpdate, @AuthenticationPrincipal User authenticatedUser) {
        var registeredUser = this.userService.update(userUpdate, cpf, authenticatedUser);
        return ResponseEntity.ok().body(registeredUser);
    }

    @GetMapping("/details/{cpf}")
    public ResponseEntity<UserResponseDTO> userDetails(@PathVariable String cpf, @AuthenticationPrincipal User authenticatedUser) {
        var registeredUser = this.userService.getUserDetails(cpf, authenticatedUser);
        return ResponseEntity.ok().body(registeredUser);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDTO>> listUsers() {
        var users = this.userService.listUsers();
        return ResponseEntity.ok().body(users);
    }

    @DeleteMapping("/delete/{cpf}")
    public ResponseEntity<Void> deleteUser(@PathVariable String cpf, @AuthenticationPrincipal User authenticatedUser) {
        this.userService.delete(cpf, authenticatedUser);
        return ResponseEntity.noContent().build();
    }

}
