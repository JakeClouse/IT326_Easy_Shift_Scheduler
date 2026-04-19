package com.EasyShiftScheduler.CalEnder.Services;

import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Helpers.UserOperations;
import com.EasyShiftScheduler.CalEnder.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserOperations userOperations;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, UserOperations userOperations, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userOperations = userOperations;
        this.encoder = encoder;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public String save(User user) {
        // Check if password is strong enough before saving user
        if (!userOperations.checkPasswordStrength(user.getPassword())) {
            return "Error: Password is not strong enough!";
        }

        // Encode user password
        user.setPassword(encoder.encode(user.getPassword()));

        // Saves user to DB
        userRepository.save(user);
        return "User registered successfully!";
    }
}
