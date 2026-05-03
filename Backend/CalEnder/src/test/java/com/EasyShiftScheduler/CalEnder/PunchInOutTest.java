package com.EasyShiftScheduler.CalEnder;

import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Services.UserService;
import com.EasyShiftScheduler.CalEnder.Services.UserTimeCardService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PunchInOutTest {

    @Autowired
    private UserTimeCardService timecardService;

    @Autowired
    private UserService userService;

    private User demoUser;

    @BeforeEach
    public void setup() {
        User newDemoUser = new User();
        newDemoUser.setUsername("clockOperationsDemo");
        newDemoUser.setPassword("FGXDhfgf%$%$%%435GDDGDgdcgd");
        demoUser = userService.save(newDemoUser);
    }

    @Test
    public void clockInSuccess() {
        String result = timecardService.clockIn(demoUser.getId(), LocalDateTime.now());
        assertEquals("Employee has clocked in", result);
    }

    @Test
    public void alreadyClockedIn() {
        timecardService.clockIn(demoUser.getId(), LocalDateTime.now());
        String result = timecardService.clockIn(demoUser.getId(), LocalDateTime.now());
        assertEquals("Error: Employee is already clocked in", result);
    }

    @Test
    public void clockOutSuccess() {
        timecardService.clockIn(demoUser.getId(), LocalDateTime.now());
        String result = timecardService.clockOut(demoUser.getId(), LocalDateTime.now(), "End of shift");
        assertEquals("Employee has clocked out", result);
    }

    @Test
    public void notClockedIn() {
        timecardService.clockIn(demoUser.getId(), LocalDateTime.now());
        timecardService.clockOut(demoUser.getId(), LocalDateTime.now(), "End of shift");
        String result = timecardService.clockOut(demoUser.getId(), LocalDateTime.now(), "End of shift");
        assertEquals("Error: Employee is not clocked in", result);
    }

    @Test
    public void clockOutWhenEmpty() {
        String result = timecardService.clockOut(demoUser.getId(), LocalDateTime.now(), "End of shift");
        assertEquals("Error: Employee is not clocked in", result);
    }

    @Test
    public void invalidUserClockIn() {
        String result = timecardService.clockIn(demoUser.getId() + 9999, LocalDateTime.now());
        assertEquals("User not found", result);
    }

    @Test
    public void invalidUserClockOut() {
        String result = timecardService.clockIn(demoUser.getId() + 9999, LocalDateTime.now());
        assertEquals("User not found", result);
    }

}
