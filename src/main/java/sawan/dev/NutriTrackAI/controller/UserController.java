// UserController.java
package sawan.dev.NutriTrackAI.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sawan.dev.NutriTrackAI.dto.requestDto.UserRequestDTO;
import sawan.dev.NutriTrackAI.dto.responseDto.UserResponseDTO;
import sawan.dev.NutriTrackAI.entity.User;
import sawan.dev.NutriTrackAI.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO dto) {
        User user = modelMapper.map(dto, User.class);
        User saved = userService.createUser(user);
        return ResponseEntity.ok(modelMapper.map(saved, UserResponseDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id); // already throws exception
        return ResponseEntity.ok(modelMapper.map(user, UserResponseDTO.class));
    }


    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers().stream()
                .map(u -> modelMapper.map(u, UserResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO dto) {

        User updated = modelMapper.map(dto, User.class);
        User saved = userService.updateUser(id, updated);
        return ResponseEntity.ok(modelMapper.map(saved, UserResponseDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
