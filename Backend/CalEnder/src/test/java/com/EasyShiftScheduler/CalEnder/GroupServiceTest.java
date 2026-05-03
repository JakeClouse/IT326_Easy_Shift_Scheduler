package com.EasyShiftScheduler.CalEnder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.EasyShiftScheduler.CalEnder.Entities.Group;
import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Services.GroupService;
import com.EasyShiftScheduler.CalEnder.Services.UserService;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
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
        employee.setUsername("DELETE_ME1");
        employee.setEmail("Email1@mail.com");
        employee.setPassword("jfid8s9fu(*#U*(JF*9-2jisdjfio1NJAKDHKJASDSJ");

        employer = new User();
        employer.setUsername("DELETE_ME2");
        employer.setEmail("Email2@mail.com");
        employer.setPassword("jfid8s9fu(*#U*(JF*9-2jisdjfio1NJAKDHKJASDSJ");

        employee = userService.save(employee);
        employer = userService.save(employer);

        List<Long> userIds = new ArrayList<>();

        userIds.add(employee.getId());
        userIds.add(employer.getId());

        mainGroup = groupService.createGroup(userIds);
    }

    @Test
    public void testEmployeeNotFound(){
        userService.deleteAccount(employee.getId());
        String message = groupService.removeEmployee(mainGroup.getId(), employee.getId(), employer.getId());
        assertEquals("Error: employee not found", message);
    }

    @Test
    public void testEmployerNotFound(){
        userService.deleteAccount(employer.getId());
        String message = groupService.removeEmployee(mainGroup.getId(), employee.getId(), employer.getId());
        assertEquals("Error: employer not found", message);
    }

    @Test
    public void testGroupNotFound(){
        String message = groupService.removeEmployee(-1L, employee.getId(), employer.getId());
        assertEquals("Error: group not found", message);
    }

    @Test
    public void testGroupsDiffer(){
        User u = new User();
        u.setUsername("DELETE_ME3");
        employee.setEmail("Email3@mail.com");
        u.setPassword("jfid8s9fu(*#U*(JF*9-2jisdjfio1NJAKDHKJASDSJ");

        userService.save(u);//new user not part of group
        String message = groupService.removeEmployee(mainGroup.getId(), u.getId(), employer.getId());

        assertEquals("Error: employee and employer not in same group", message);
    }

    @Test
    public void testNormalOperations(){
        String message = groupService.removeEmployee(mainGroup.getId(), employee.getId(), employer.getId());
        assertEquals("Successfully remove employee from group", message);
    }



}