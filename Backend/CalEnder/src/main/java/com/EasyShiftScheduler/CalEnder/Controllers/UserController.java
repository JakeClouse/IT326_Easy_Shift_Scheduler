package com.EasyShiftScheduler.CalEnder.Controllers;

import com.EasyShiftScheduler.CalEnder.Entities.UserTimecard;
import com.EasyShiftScheduler.CalEnder.Services.UserService;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;


@RestController
@RequestMapping("/api/user/")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
}
