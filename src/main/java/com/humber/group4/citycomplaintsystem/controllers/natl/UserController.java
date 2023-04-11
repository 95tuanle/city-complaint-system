package com.humber.group4.citycomplaintsystem.controllers.natl;

import com.humber.group4.citycomplaintsystem.dao.natl.UserDao;
import com.humber.group4.citycomplaintsystem.models.natl.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping(value="user/sign-up")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "user-sign-up";
    }

    @PostMapping("user/sign-up")
    public String signUp(@ModelAttribute("user") User user, HttpServletRequest request) {
        request.getSession().setAttribute("user", user);
        switch (user.getType().name()) {
            case "customer":
                return "redirect:/customer/sign-up";
            case "admin":
                return "redirect:/admin/sign-up";
            case "employee":
                return "redirect:/employee/sign-up";
        }
        return null;
    }

    @GetMapping("user/sign-in")
    public String signIn(Model model) {
        model.addAttribute("user", new User());
        return "user-sign-in";
    }

    @PostMapping("user/sign-in")
    public String signIn(@ModelAttribute("user") User user, HttpServletResponse response) {
        User user1 = userDao.readByEmail(user.getEmail());
        if (user1 != null) {
            if (user1.getPassword().equals(user.getPassword())) {
                setCookies(response, user1);
                switch (user1.getType().name()) {
                    case "customer":
                        return "redirect:/customer/index";
                    case "admin":
                        return "redirect:/admin/index";
                    case "employee":
                        return "redirect:/employee/index";
                }
            } else {
                return "redirect:/user/sign-in";
            }
        } else {
            return "redirect:/user/sign-up";
        }
        return null;
    }

    public static void setCookies(HttpServletResponse response, User user) {
        Cookie email = new Cookie("email", user.getEmail());
        email.setPath("/");
        response.addCookie(email);
        Cookie type = new Cookie("type", user.getType().name());
        type.setPath("/");
        response.addCookie(type);
    }

    @GetMapping("user/sign-out")
    public String signOut(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return "redirect:/";
    }
}
