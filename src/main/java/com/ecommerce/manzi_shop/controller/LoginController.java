package com.ecommerce.manzi_shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ecommerce.manzi_shop.error.ExceptionDetails;
import com.ecommerce.manzi_shop.error.RequestException;
import com.ecommerce.manzi_shop.global.GlobalData;
import com.ecommerce.manzi_shop.model.Role;
import com.ecommerce.manzi_shop.model.User;
import com.ecommerce.manzi_shop.repository.RoleRepository;
import com.ecommerce.manzi_shop.repository.UserRepository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/login")
    public String login() {
        GlobalData.cart.clear();
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request) throws Exception {
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));

        Optional<Role> userRole;
        if (user.getEmail().equalsIgnoreCase("admin@gmail.com")) {
            userRole = roleRepository.findById(1); // 1 = ADMIN role
        } else {
            userRole = roleRepository.findById(2); // 2 = USER role
        }

        List<Role> roles = new ArrayList<>();
        roles.add(userRole.get());
        user.setRoles(roles);

        userRepository.save(user);

        try {
            request.login(user.getEmail(), password);
        } catch (Exception e) {
            throw new RequestException("Login failed for user: " + user.getEmail(), e);
        }

        return "redirect:/";
    }

    @ExceptionHandler(RequestException.class)
    public String handleRequestException(RequestException e, Model model) {
        model.addAttribute("exceptionDetails", new ExceptionDetails(
                e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now()));
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception e, Model model) {
        model.addAttribute("errorTitle", "Unexpected Error");
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
        model.addAttribute("timestamp", ZonedDateTime.now());
        return "error";
    }
}
