package com.EasyShiftScheduler.CalEnder.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import com.EasyShiftScheduler.CalEnder.Entities.Group;
import com.EasyShiftScheduler.CalEnder.Entities.Notifications.EmailDetails;
import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Helpers.GroupOperations;
import com.EasyShiftScheduler.CalEnder.Repositories.GroupRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GroupService {
    private GroupOperations groupOperations;
    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private final EmailService emailService;

    public String removeEmployee(long groupID, long employeeID, long employerID) {
        Optional<User> employee = userRepository.findById(employeeID);
        Optional<User> employer = userRepository.findById(employerID);
        Optional<Group> group = groupRepository.findById(groupID);

        if (employee.isEmpty()){
            return "Error: employee not found";
        }

        if (employer.isEmpty()) {
            return "Error: employer not found";
        }

        if (group.isEmpty()) {
            return "Error: group not found";
        }

        List<User> userGroup = group.get().getUsers();



        if (userGroup.contains(employee.get()) && userGroup.contains(employer.get())){
            userGroup.remove(employee.get());

            // Update group's members
            group.get().setUsers(userGroup);

            // Update employee's groups
            List<Group> employeesGroup = employee.get().getGroups();
            employeesGroup.remove(group.get());
            employee.get().setGroups(employeesGroup);

        }
        else {
            return "Error: employee and employer not in same group";
        }

        // Save Group and employee back to DB
        groupRepository.save(group.get());
        userRepository.save(employee.get());

        return "Successfully remove employee from group";
    }

    public Group createGroup(List<Long> userIds){
        Group group = new Group();

        for(int i = 0; i < userIds.size(); i++){
            Optional<User> user = userRepository.findById(userIds.get(i));

            if (!user.isEmpty()){
                group.getUsers().add(user.get());
            }
        }

        Group newGroup = groupRepository.save(group);

        //cleanpup so users have reference as well.
        for(int i = 0; i < userIds.size(); i++){
            Optional<User> user = userRepository.findById(userIds.get(i));

            if (!user.isEmpty()){
                user.get().getGroups().add(newGroup);
                userRepository.save(user.get());
            }
        }
        return newGroup;
    }


    public String addUser(User user, Group group) {
        List<User> groupUsers = group.getUsers();
        groupUsers.add(user);
        group.setUsers(groupUsers);

        groupRepository.save(group);

        return "User added to group";

    }

    public String publishSchedule(Long groupID) {
        Optional<Group> groupOptional = groupRepository.findById(groupID);
        if (groupOptional.isEmpty()){
            return "Group Not Found";
        }
        Group group = groupOptional.get();

        List<User> groupUsers = group.getUsers();
        for(User user : groupUsers){
            String msgBody = "Hello, " + user.getUsername() + "!\n\nYour next work schedule has been posted by your employer.\n\n" +
                    "Please log in to the employee portal to view your schedule. For help, please contact your manager.\n\n" +
                    "Best regards,\nthe EasyShiftScheduler team";
            EmailDetails details = new EmailDetails(user.getEmail(), msgBody, "Your Work Schedule is Available!");
            byte[] serializedDetails = SerializationUtils.serialize(details);
            try {
                emailService.sendNotification(serializedDetails);
            } catch (Exception e) {
                return "Error sending email";
            }
        }

        return "Schedule notification email sent to all group members";
    }

    public String generateReport(Long groupID) {
        Optional<Group> groupOptional = groupRepository.findById(groupID);
        if (groupOptional.isEmpty()){
            return "Group Not Found";
        }
        Group group = groupOptional.get();
        
        List<User> groupUsers = group.getUsers();
        String report = "";
        double totalComp = 0.0;
        for(User user : groupUsers){
            double hours = user.getUser_timecard().getWorked_hours();
            double compensation = user.getCompensation_rate() * hours;
            totalComp += compensation;
            report += "User: " + user.getUsername() +"\nHours worked: " + hours + "\nCompensation: " + compensation + "\n\n";
        }
        report += "Total group compensation: " + totalComp;

        return report;
    }


    public String getGroup(Long groupID){
        Optional<Group> groupOptional = groupRepository.findById(groupID);
        if (groupOptional.isEmpty()){
            return "Group Not Found";
        }
        Group group = groupOptional.get();

        return group.toString();
    }

    public String getGroups(){
        List<Group> groupList = groupRepository.findAll();
        String s = "";
        for (Group g : groupList){
            s += g.toString() + ", ";
        }
        return s;
    }




}