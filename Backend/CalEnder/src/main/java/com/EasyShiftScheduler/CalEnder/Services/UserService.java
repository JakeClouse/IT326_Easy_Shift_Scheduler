package com.EasyShiftScheduler.CalEnder.Services;

import com.EasyShiftScheduler.CalEnder.Entities.Notifications.EmailDetails;
import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Entities.UserAvailabilitySchedule;
import com.EasyShiftScheduler.CalEnder.Entities.UserTimecard;
import com.EasyShiftScheduler.CalEnder.Entities.UserWorkSchedule;
import com.EasyShiftScheduler.CalEnder.Helpers.UserOperations;
import com.EasyShiftScheduler.CalEnder.Repositories.UserAvailabilityScheduleRepository;
import com.EasyShiftScheduler.CalEnder.Helpers.CompensationReport;
import com.EasyShiftScheduler.CalEnder.Repositories.UserRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.UserWorkScheduleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserOperations userOperations;
    private final UserAvailabilityScheduleRepository availabilityScheduleRepository;
    private final PasswordEncoder encoder;
    private final UserWorkScheduleRepository userWorkScheduleRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, UserOperations userOperations, UserAvailabilityScheduleRepository availabilityScheduleRepository, UserWorkScheduleRepository userWorkScheduleRepository, PasswordEncoder encoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.userOperations = userOperations;
        this.availabilityScheduleRepository = availabilityScheduleRepository;
        this.encoder = encoder;
        this.userWorkScheduleRepository = userWorkScheduleRepository;
        this.emailService = emailService;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public String save(User user) {
        // Check if password is strong enough before saving user
        if (!userOperations.checkPasswordStrength(user.getPassword())) {
            return "Error: Password is not strong enough!";
        }

        // Check if same email

        if (userRepository.existsByEmail(user.getEmail())){
            return "Email already associated with account";
        }
        // Encode user password
        user.setPassword(encoder.encode(user.getPassword()));

        // Saves user to DB
        userRepository.save(user);
        return "User registered successfully!";
    }

    public String deleteWorkSchedule(long userID) {
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

    public String setAvailabilitySchedule(long userID, UserAvailabilitySchedule availabilitySchedule) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()){
            if(availabilitySchedule.getAvailability_schedule().size()%2 != 0){
                return "Error: Availability schedule must contain an even number of entries (start and end times)";
            }
            UserAvailabilitySchedule availabilitySchedule2 = availabilityScheduleRepository.save(availabilitySchedule);
            user.get().setAvailability_schedule(availabilitySchedule2);
            User user2 = userRepository.save(user.get());
            return user2.getAvailability_schedule().toString();
        }
        else {
            return "User not found";
        }
    }

    public String getAvailabilitySchedule(long userID) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()){
            return user.get().getAvailability_schedule().toString();
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

    public String submitTimeOffRequest(long userID, UserTimecard timecard) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()){
            user.get().setTimecard(timecard);
            userRepository.save(user.get());
            return "Time off request submitted";
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

    // Create automatic schedule - auto-generate a work schedule from the user's availability
    public String createAutoSchedule(long userID) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isEmpty())
            return "User not found";

        UserAvailabilitySchedule avail = user.get().getAvailability_schedule();
        if (avail == null)
            return "No availability set for user";

        UserWorkSchedule newSchedule = new UserWorkSchedule();
        newSchedule.setWork_schedule(avail.getAvailability_schedule());
        userWorkScheduleRepository.save(newSchedule);

        user.get().setWork_schedule(newSchedule);
        userRepository.save(user.get());

        return "Work schedule created from availability";
    }

    // Generate a compensation report for a user
    public String generateCompensationReport(long userID) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isEmpty())
            return "User not found";
        if (user.get().getTimecard() == null)
            return "No timecard found";
        
        CompensationReport report = new CompensationReport();
        return report.generateReport(user.get());
    }

    // Update a user's compensation rate (employer action)
    public String updateCompensationRate(long userID, double newRate) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isEmpty())
            return "User not found";
        
        user.get().setCompensation_rate(newRate);
        userRepository.save(user.get());
        return "Compensation rate updated";
    }

    public String updatePassword(long userID, String newPassword) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()){
            user.get().setPassword(newPassword);
            save(user.get());

            EmailDetails details = new EmailDetails(user.get().getEmail(), "Your password has been updated", "Password Update");
            byte[] serializedDetails = SerializationUtils.serialize(details);

            try {
                emailService.sendNotification(serializedDetails);
            } catch (Exception e) {
                return "Error sending email";
            }

            return "Password Updated";
        }
        else {
            return "Error Updating Password";
        }
    }

    public String deleteAccount(long userID) {
        userRepository.deleteById(userID);
        return "User deleted successfully";
    }
}
