package com.EasyShiftScheduler.CalEnder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import com.EasyShiftScheduler.CalEnder.Entities.Group;
import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Services.EmailService;
import com.EasyShiftScheduler.CalEnder.Services.GroupService;
import com.EasyShiftScheduler.CalEnder.Services.UserService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    private User employee;
    private User employer;
    private Group mainGroup;

    @BeforeEach
    public void setUp(){
        employee = new User();
        employer = new User();

        employee = userService.save(employee);
        employer = userService.save(employer);

        List<Long> userIds = new ArrayList<>();

        userIds.add(employee.getId());
        userIds.add(employer.getId());

        mainGroup = groupService.createGroup(userIds);
    }

    @Test
    public void testEmployeeNotFound() throws Exception{
        Exception e = assertThrows(Exception.class, () -> {
            userService.deleteAccount(employee.getId());
            groupService.removeEmployee(mainGroup.getId(), employee.getId(), employer.getId());
        });
        assertEquals("Error: employee not found", e.getMessage());
    }

    @Test
    public void testEmployerNotFound(){
        Exception e = assertThrows(Exception.class, () -> {
            userService.deleteAccount(employer.getId());
            groupService.removeEmployee(mainGroup.getId(), employee.getId(), employer.getId());
        });
        assertEquals("Error: employer not found", e.getMessage());
    }

    @Test
    public void testGroupNotFound(){
        Exception e = assertThrows(Exception.class, () -> {
            groupService.removeEmployee(-1L, employee.getId(), employer.getId());
        });
        assertEquals("Error: group not found", e.getMessage());
    }

    @Test
    public void testGroupsDiffer(){
        Exception e = assertThrows(Exception.class, () -> {
            User u = new User();
            userService.save(u);//new user not part of group
            groupService.removeEmployee(mainGroup.getId(), u.getId(), employer.getId());
        });
        assertEquals("Error: employee and employer not in same group", e.getMessage());
    }

    @Test
    public void testNormalOperations(){
        String message = groupService.removeEmployee(mainGroup.getId(), employee.getId(), employer.getId());
        assertEquals("Successfully remove employee from group", message);
    }




    
}
