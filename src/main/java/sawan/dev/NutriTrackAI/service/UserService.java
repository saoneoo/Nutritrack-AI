package sawan.dev.NutriTrackAI.service;


import sawan.dev.NutriTrackAI.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}