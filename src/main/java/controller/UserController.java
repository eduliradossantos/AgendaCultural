package controller;

import dto.UserCreateDto;
import dto.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserResponseDTO create(@RequestBody @Valid UserCreateDto userCreateDTO) {
        return userService.create(userCreateDTO);
    }
}