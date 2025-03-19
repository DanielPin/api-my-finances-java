package com.kueshi.api_my_finances.user.services;


import com.kueshi.api_my_finances.config.errors.ErrorResponse;
import com.kueshi.api_my_finances.user.documents.User;
import com.kueshi.api_my_finances.user.dto.UserRegisterDTO;
import com.kueshi.api_my_finances.user.dto.UserResponseDTO;
import com.kueshi.api_my_finances.user.dto.UserUpdateDTO;
import com.kueshi.api_my_finances.user.enums.UserRole;
import com.kueshi.api_my_finances.user.mappers.UserMapper;
import com.kueshi.api_my_finances.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserResponseDTO register(UserRegisterDTO registerUser) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerUser.password());

        User newUser = new User(registerUser, encryptedPassword);
        var savedUser = this.userRepository.save(newUser);

        return userMapper.userToUserResponseDTO(savedUser);
    }

    public UserResponseDTO update(UserUpdateDTO userUpdate, String cpf, User authenticatedUser) {

        User existingUser = this.userRepository.findByCpf(cpf.replaceAll("\\D", ""));

        if (existingUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não localizado");
        }

        if (userUpdate.role() != null && authenticatedUser.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para atualizar sua role, peça a um adm");
        }

        if (hasNoPermission(existingUser.getId(), authenticatedUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para atualizar este usuário.");
        }

        userMapper.updateUserFromDto(userUpdate, existingUser);

        var updatedUser = this.userRepository.save(existingUser);
        return userMapper.userToUserResponseDTO(updatedUser);
    }

    public void delete(String cpf, User authenticatedUser) {

        User existingUser = this.userRepository.findByCpf(cpf.replaceAll("\\D", ""));

        if (existingUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não localizado");
        }

        if (hasNoPermission(existingUser.getId(), authenticatedUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para deletar este usuário.");
        }

        this.userRepository.delete(existingUser);
    }

    public UserResponseDTO getUserDetails(String cpf, User authenticatedUser) {
        User user = this.userRepository.findByCpf(cpf.replaceAll("\\D", ""));

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (hasNoPermission(user.getId(), authenticatedUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para ver este usuário.");
        }

        return userMapper.userToUserResponseDTO(user);
    }

    public List<UserResponseDTO> listUsers() {
        var users = this.userRepository.findAll();

        return users.stream()
                .map(userMapper::userToUserResponseDTO)
                .collect(Collectors.toList());
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
