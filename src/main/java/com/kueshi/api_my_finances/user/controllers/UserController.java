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

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody @Valid UserRegisterDTO user) {
        var registeredUser = this.userService.register(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .replacePath("/user/details/{cpf}")
                .buildAndExpand(registeredUser.getCpf())
                .toUri();
        return ResponseEntity.created(location).body(registeredUser);
    }

    @PutMapping("/update/{cpf}")
    public ResponseEntity updateUser(@PathVariable String cpf, @RequestBody @Valid UserUpdateDTO userUpdate, @AuthenticationPrincipal User authenticatedUser) {

        //var user = this.userService.getUserDetails(cpf);

        /*if (userUpdate.role() != null && authenticatedUser.getRole() != UserRole.ADMIN) {
            return buildErrorResponse("Você não tem permissão para atualizar sua role, peça a um adm", HttpStatus.FORBIDDEN);
        }

        if (hasNoPermission(user.id(), authenticatedUser)) {
            return buildErrorResponse("Você não tem permissão para atualizar este usuário.", HttpStatus.FORBIDDEN);
        }*/

        var registeredUser = this.userService.update(userUpdate, cpf, authenticatedUser);

        return ResponseEntity.ok().body(registeredUser);
    }

    @GetMapping("/details/{cpf}")
    public ResponseEntity userDetails(@PathVariable String cpf, @AuthenticationPrincipal User authenticatedUser) {
        var registeredUser = this.userService.getUserDetails(cpf);

        if (hasNoPermission(registeredUser.id(), authenticatedUser)) {
            return buildErrorResponse("Você não tem permissão para ver este usuário.", HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok().body(registeredUser);
    }

    @GetMapping("/list")
    public ResponseEntity listUsers() {
        var users = this.userService.listUsers();
        return ResponseEntity.ok().body(users);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(message, status.value());
        return new ResponseEntity<>(errorResponse, status);
    }

    private boolean hasNoPermission(String userId, User authenticatedUser) {
        String authenticatedUsername = authenticatedUser.getId();
        UserRole userRole = authenticatedUser.getRole();
        return !authenticatedUsername.equals(userId) && userRole != UserRole.ADMIN;
    }


}
