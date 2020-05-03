package com.i2kiselev.springCourseProject.security;

import com.i2kiselev.springCourseProject.model.User;
import com.i2kiselev.springCourseProject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(
            UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean userExists(String username){
        User user= userRepo.findByUsername(username);
        return user != null;
    }

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("form", new RegistrationForm());
        return "registration";
    }

    @PostMapping
    public String processRegistration(@Valid @ModelAttribute("form") RegistrationForm form, BindingResult result, Model model) {
        String username = form.getUsername();
        if(userExists(username)){
            model.addAttribute("error", "User " +username + " already exists");
            return "registration";
        }
        if(result.hasErrors()){
            return "registration";
        }
        else {
            userRepo.save(form.toUser(passwordEncoder));
            return "redirect:/login";
        }
    }
}
