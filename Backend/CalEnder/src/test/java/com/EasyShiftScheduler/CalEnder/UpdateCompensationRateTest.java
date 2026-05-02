package com.EasyShiftScheduler.CalEnder;

import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UpdateCompensationRateTest {

    @Autowired
    private UserService userService;

    // Path 1: User does not exist, should return "User not found"
    @Test
    public void testUpdateCompensationRate_UserNotFound() {
        String result = userService.updateCompensationRate(-1L, 20.00);
        assertEquals("User not found", result);
    }

    // Path 2: User exists, compensation rate is updated successfully
    @Test
    public void testUpdateCompensationRate_Success() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@test.com");
        user.setPassword("TestPass1!");

        User savedUser = userService.save(user);

        String result = userService.updateCompensationRate(savedUser.getId(), 20.00);
        assertEquals("Compensation rate updated", result);
    }
}