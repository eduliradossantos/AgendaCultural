package com.agenda.agendacultural.service;

import com.agenda.agendacultural.dto.UserCreateDto;
import com.agenda.agendacultural.dto.UserResponseDTO;
import com.agenda.agendacultural.exception.ResourceNotFoundException;
import com.agenda.agendacultural.model.User;
import com.agenda.agendacultural.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO createUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setIdUser(UUID.randomUUID());
        user.setName(userCreateDto.getName());
        user.setEmail(userCreateDto.getEmail());
        user.setPassword(userCreateDto.getPassword()); // Sem criptografia, conforme solicitado
        user.setRegistrationDate(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return convertToResponseDto(savedUser);
    }

    public UserResponseDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
        return convertToResponseDto(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDTO updateUser(UUID id, UserCreateDto userCreateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        user.setName(userCreateDto.getName());
        user.setEmail(userCreateDto.getEmail());
        
        // Atualiza a senha apenas se fornecida
        if (userCreateDto.getPassword() != null && !userCreateDto.getPassword().isEmpty()) {
            user.setPassword(userCreateDto.getPassword()); // Sem criptografia, conforme solicitado
        }

        User updatedUser = userRepository.save(user);
        return convertToResponseDto(updatedUser);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }
        userRepository.deleteById(id);
    }

    // Helper method for DTO conversion
    private UserResponseDTO convertToResponseDto(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setIdUser(user.getIdUser());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRegistrationDate(user.getRegistrationDate());
        return dto;
    }
}
