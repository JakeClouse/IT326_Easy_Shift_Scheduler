package com.EasyShiftScheduler.CalEnder;

import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class DeleteUserTest {

    @Autowired
    UserService userService;

    @Test
    void userDeleteSuccess() throws Exception {
        // Make user to delete
        User userToDelete = new User();
        userToDelete.setUsername("DELETE_ME");
        userToDelete.setPassword("jfid8s9fu(*#U*(JF*9-2jisdjfio1NJAKDHKJASDSJ");

        // Add user to DB
        User savedUser = userService.save(userToDelete);

        // Delete user from DB
        userService.deleteAccount(savedUser.getId());

        boolean result = userService.existsByUsername("DELETE_ME");

        assertFalse(result);
    }

    @Test
    void userDeleteFail() throws Exception {
        // Make user to delete
        User userToDelete = new User();
        userToDelete.setUsername("DELETE_ME");
        userToDelete.setPassword("jfid8s9fu(*#U*(JF*9-2jisdjfio1NJAKDHKJASDSJ");

        // Add user to DB
        User savedUser = userService.save(userToDelete);

        // Delete user from DB
        userService.deleteAccount(-1L);

        boolean result = userService.existsByUsername("DELETE_ME");

        assertTrue(result);
    }
}

