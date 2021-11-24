package ru.kirillov.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kirillov.spring.models.User;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserDetailsService userDetailsService;  // Попробовать слить userDetailsService c UserServiceImpl

    @Autowired
    public UsersController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Залогинившийся user с помощью @AuthenticationPrincipal и UserDetails попадет в currentUser,
    //   затем в модель передастся user из БД,
    //    далее модель с user уйдет в userInfo
    @GetMapping()
    public String showUserInfo(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("currentUser",
                userDetailsService.loadUserByUsername(currentUser.getUsername()));
        return "/user/userInfo";
    }
}
