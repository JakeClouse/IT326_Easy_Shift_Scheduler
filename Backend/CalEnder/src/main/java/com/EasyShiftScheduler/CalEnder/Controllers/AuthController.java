package com.EasyShiftScheduler.CalEnder.Controllers;

import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Repositories.UserRepository;
import com.EasyShiftScheduler.CalEnder.Security.JwtUtil;
import com.EasyShiftScheduler.CalEnder.Services.AuthService;
import com.EasyShiftScheduler.CalEnder.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
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
    public String authenticateUser(@RequestBody User user) {
        return authService.authenticateUser(user);
    }

    @PostMapping("/employee_signup")
    public String registerEmployee(@RequestBody User user) {
        user.setRoles("EMPLOYEE");
        return userService.save(user);
    }

    @PostMapping("/employer_signup")
    public String registerEmployer(@RequestBody User user) {
        user.setRoles("EMPLOYEE,EMPLOYER");
        return userService.save(user);
    }

    @GetMapping("/test")
    public String registerUser() {
        return "TEST!!!";
    }

}
