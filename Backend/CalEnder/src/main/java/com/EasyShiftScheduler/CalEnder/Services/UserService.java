package com.EasyShiftScheduler.CalEnder.Services;

import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Entities.UserAvailabilitySchedule;
import com.EasyShiftScheduler.CalEnder.Entities.UserTimecard;
import com.EasyShiftScheduler.CalEnder.Helpers.UserOperations;
import com.EasyShiftScheduler.CalEnder.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public String deleteSchedule(long userID) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()){
            user.get().setWork_schedule(null);
            userRepository.save(user.get());
            return "User Work Schedule Deleted";
        }
        else {
            return "User not found";
        }
    }

    public String setSchedule(long userID, UserAvailabilitySchedule availabilitySchedule) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()){
            user.get().setAvailability_schedule(availabilitySchedule);
            userRepository.save(user.get());
            return "User Work Availability Schedule Updated";
        }
        else {
            return "User not found";
        }
    }

    public String updateAccountInfo(long userID, User newUser){
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()){
            user.get().setEmail(newUser.getEmail());
            user.get().setUsername(newUser.getUsername());
            userRepository.save(user.get());
            return "Account information updated";
        }
        else {
            return "User not found";
        }
    }

    public String overrideTimecard(long userID, UserTimecard userTimecard) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()){
            user.get().setTimecard(userTimecard);
            userRepository.save(user.get());
            return "User timecard updated";
        }
        else {
            return "User not found";
        }
    }
}
