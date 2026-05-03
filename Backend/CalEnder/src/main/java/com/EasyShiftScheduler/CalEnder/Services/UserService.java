package com.EasyShiftScheduler.CalEnder.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import com.EasyShiftScheduler.CalEnder.Entities.Group;
import com.EasyShiftScheduler.CalEnder.Entities.Notifications.EmailDetails;
import com.EasyShiftScheduler.CalEnder.Entities.Punch;
import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Entities.UserAvailabilitySchedule;
import com.EasyShiftScheduler.CalEnder.Entities.UserTimecard;
import com.EasyShiftScheduler.CalEnder.Entities.UserWorkSchedule;
import com.EasyShiftScheduler.CalEnder.Helpers.CompensationReport;
import com.EasyShiftScheduler.CalEnder.Helpers.UserOperations;
import com.EasyShiftScheduler.CalEnder.Repositories.GroupRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.PunchRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.UserAvailabilityScheduleRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.UserRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.UserWorkScheduleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final PunchRepository punchRepository;
    private final UserRepository userRepository;
    private final UserOperations userOperations;
    private final UserAvailabilityScheduleRepository availabilityScheduleRepository;
    private final PasswordEncoder encoder;
    private final UserWorkScheduleRepository userWorkScheduleRepository;
    private final EmailService emailService;
    private final GroupRepository groupRepository;
    private final GroupService groupService;


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User save(User user) {
        // Check if password is strong enough before saving user
        if (!userOperations.checkPasswordStrength(user.getPassword())) {
            return null;
        }

        // Check if same email

        if (userRepository.existsByEmail(user.getEmail())){
            return null;
        }
        // Encode user password
        user.setPassword(encoder.encode(user.getPassword()));

        // Saves user to DB
        return userRepository.save(user);
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
            UserAvailabilitySchedule availabilitySchedule = user.get().getAvailability_schedule();
            if(availabilitySchedule == null){
                return "No availability schedule found for user";
            }else{
                return availabilitySchedule.toString();
            }
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

    public String getPunchByUser(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()){
            return "User Not Found";
        }
        User user = userOptional.get();
        UserTimecard t = user.getUser_timecard();

        List<Punch> punchList = t.getPunch_times();
        String s = "";
        for (Punch p : punchList){
            s += p.toString() + ", ";
        }
        return s;
    }



    public String submitTimeOffRequest(long userID, UserTimecard user_timecard) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()){
            user.get().setUser_timecard(user_timecard);
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
            user.get().setUser_timecard(userTimecard);
            userRepository.save(user.get());
            return "User user_timecard updated";
        }
        else {
            return "User not found";
        }
    }

    // Create automatic schedule - auto-generate a work schedule from the user's availability
    public String createAutoSchedule(long userID) {
        Optional<User> userOpt = userRepository.findById(userID);
        if (userOpt.isEmpty())
            return "User not found";

        User user = userOpt.get();

        UserAvailabilitySchedule avail = user.getAvailability_schedule();
        if (avail == null)
            return "No availability set for user";

        UserWorkSchedule newSchedule = new UserWorkSchedule();

        newSchedule.setWork_schedule(
            new ArrayList<>(avail.getAvailability_schedule())
        );

        userWorkScheduleRepository.save(newSchedule);

        user.setWork_schedule(newSchedule);
        userRepository.save(user);

        return "Work schedule created from availability";
    }
    // Generate a compensation report for a user
    public String generateCompensationReport(long userID) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isEmpty())
            return "User not found";
        if (user.get().getUser_timecard() == null)
            return "No user_timecard found";
        
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

    // Return the user's user_timecard
    public String getTimecard(long userID) {
        Optional<User> user = userRepository.findById(userID);
        return user.map(User::getUser_timecard).orElse(null).toString();
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
        try {
            userRepository.deleteById(userID);
        }
        catch (Exception e){
            return "Error deleting user";
        }
        return "User deleted successfully";
    }

    public String joinGroup(long userID, long groupID) {
        Optional<User> user = userRepository.findById(userID);
        Optional<Group> groupToJoin = groupRepository.findById(userID);
        if (user.isEmpty())
            return "User not found";
        if (groupToJoin.isEmpty())
            return "No group found";

        groupService.addUser(user.get(), groupToJoin.get());

        User gotUser = user.get();

        List<Group> userGroups = gotUser.getGroups();
        userGroups.add(groupToJoin.get());
        gotUser.setGroups(userGroups);

        userRepository.save(gotUser);

        return "Added user to group";
    }

    public String getAccount(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            return "Punch Not Found";
        }
        User user = userOptional.get();

        return user.toString();
    }

    public String getAllUsers() {
        List<User> users = userRepository.findAll();
        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append(user.getUsername() + " - " + user.getId()).append("\n");
        }
        return sb.toString();
    }
}
