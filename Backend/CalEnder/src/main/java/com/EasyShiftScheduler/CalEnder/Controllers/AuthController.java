package com.EasyShiftScheduler.CalEnder.Controllers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Helpers.JwtResponse;
import com.EasyShiftScheduler.CalEnder.Services.AuthService;
import com.EasyShiftScheduler.CalEnder.Services.UserService;
@RestController

@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.authService = authService;
    }


    @PostMapping("/signin")
    public JwtResponse authenticateUser(@RequestBody User user) {
        return authService.authenticateUser(user);
    }

    @PostMapping("/employee_signup")
    public String registerEmployee(@RequestBody User user) {
        user.setRoles("EMPLOYEE");
        return userService.save(user).toString();
    }

    @PostMapping("/employer_signup")
    public String registerEmployer(@RequestBody User user) {
        user.setRoles("EMPLOYEE,EMPLOYER");
        return userService.save(user).toString();
    }

}
