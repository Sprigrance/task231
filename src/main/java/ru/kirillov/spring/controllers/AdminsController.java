package ru.kirillov.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kirillov.spring.models.Role;
import ru.kirillov.spring.models.User;
import ru.kirillov.spring.services.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admins")
public class AdminsController {

    private final UserService userService;

    @Autowired
    public AdminsController(UserService userService) {
        this.userService = userService;
    }

    // index-страница после редиректа
    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/getAllUsers";
    }

    // введенный в адресной строке id попадет с помощью @PathVariable в int id
    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "admin/getUser";
    }

    // GET-запрос создаст модель "newUser" и поместит его как объект для создания в new.html
    @GetMapping("/new")
    public String createUser(@ModelAttribute("newUser") User user) {
        return "admin/new";
    }

    // POST-метод возьмет пользователя со страницы new.html, передаст его с помощью @ModelAttribute("newUser") в User user
    //    и сделает .saveUser()
    // если никакого User модель содержать не будет, то в User user поместится user с полями по умолчанию (0, null, null)
    @PostMapping()
    public String addUser(@ModelAttribute("newUser") User user) {
        userService.saveUser(user);
        return "redirect:/admins";
    }

    // GET-запрос со страницы getUser передаст id и перейдет в этот метод по адресу /{id}/edit
    // модель примет пользователя, найденного по id и откроет страницу editUser.html
    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("existingUser", userService.getUser(id));
        return "admin/editUser";
    }

    // PATCH-запрос из editUser.html возьмет пользователя из модели existingUser, поместит его в user,
    // id так же перейдёт из editUser.html. Далее произойдет изменение с помощью updateUser()
    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("existingUser") User user, @PathVariable("id") int id) {
        userService.updateUser(id, user);
        return "redirect:/admins";
    }

    // DELETE-запрос с id найдет пользователя, удалит его, и перейдет в admins/
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admins";
    }
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostConstruct
//    public void myinit() {
//        Role role1 = new Role("ROLE_ADMIN");
//        Role role2 = new Role("ROLE_USER");
//        Set<Role> roles = new HashSet<>();
//        roles.add(role1);
//        roles.add(role2);
//
//        roleService.saveRole(role1);
//        roleService.saveRole(role2);
//
//        User user = new User();
//        user.setUsername("admin");
//        user.setPassword(passwordEncoder.encode("admin"));
//        user.setRoles(roles);
//
//        userService.saveUser(user);
//    }
}
