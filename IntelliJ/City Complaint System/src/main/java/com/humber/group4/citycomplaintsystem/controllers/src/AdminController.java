package com.humber.group4.citycomplaintsystem.controllers.src;

import com.humber.group4.citycomplaintsystem.controllers.natl.UserController;
import com.humber.group4.citycomplaintsystem.dao.mvd.ComplaintDao;
import com.humber.group4.citycomplaintsystem.dao.natl.ReplyDao;
import com.humber.group4.citycomplaintsystem.dao.natl.UserDao;
import com.humber.group4.citycomplaintsystem.dao.src.AdminDao;
import com.humber.group4.citycomplaintsystem.dao.src.EmployeeDao;
import com.humber.group4.citycomplaintsystem.models.src.Admin;
import com.humber.group4.citycomplaintsystem.models.natl.Reply;
import com.humber.group4.citycomplaintsystem.models.natl.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class AdminController {

    final UserDao userDao;
    final AdminDao adminDao;
    final ComplaintDao complaintDao;
    final ReplyDao replyDao;
    final EmployeeDao employeeDao;

    public AdminController(UserDao userDao, AdminDao adminDao, ComplaintDao complaintDao, ReplyDao replyDao, EmployeeDao employeeDao) {
        this.userDao = userDao;
        this.adminDao = adminDao;
        this.complaintDao = complaintDao;
        this.replyDao = replyDao;
        this.employeeDao = employeeDao;
    }

    @GetMapping("admin/sign-up")
    public String signUp(Model model, HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            return "redirect:/user/sign-up";
        }
        model.addAttribute("admin", new Admin());
        return "admin/sign-up";
    }

    @PostMapping("admin/sign-up")
    public String signUp(@ModelAttribute("admin") Admin admin, HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            return "redirect:/user/sign-up";
        }
        httpSession.invalidate();
        userDao.create(user);
        admin.setUser(user);
        adminDao.create(admin);
        UserController.setCookies(response, user);
        return "redirect:/admin/index";
    }

    @GetMapping("admin/index")
    public String index(Model model, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "type", defaultValue = "") String type) {
        if (email.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        }
        model.addAttribute("complaints", complaintDao.list());
        return "admin/index";
    }

    @GetMapping("admin/complaint/{id}")
    public String viewComplaint(@PathVariable long id, Model model, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "type", defaultValue = "") String type) {
        if (email.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        }
        model.addAttribute("complaint", complaintDao.read(id));
        model.addAttribute("employees", userDao.listByType("employee"));
        model.addAttribute("reply", new Reply());
        model.addAttribute("replies", replyDao.listByComplaint(complaintDao.read(id)));
        return "admin/view-complaint";
    }

    @PostMapping("admin/complaint/{id}/reply")
    public String reply(@PathVariable long id, @ModelAttribute("reply") Reply reply, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "type", defaultValue = "") String type) {
        if (email.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        }
        reply.setId(null);
        reply.setComplaint(complaintDao.read(id));
        reply.setUser(userDao.readByEmail(email));
        reply.setDate(LocalDateTime.now());
        replyDao.create(reply);
        return "redirect:/admin/complaint/" + id;
    }

    @PostMapping("admin/complaint/{id}/assign")
    public String assign(@PathVariable long id, @RequestParam("employee") Long employeeId, @CookieValue(value = "email", defaultValue = "") String adminEmail, @CookieValue(value = "type", defaultValue = "") String type) {
        if (adminEmail.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        }
        var complaint = complaintDao.read(id);
        complaint.setEmployee(employeeDao.readById(employeeId));
        complaintDao.update(complaint);
        return "redirect:/admin/complaint/" + id;
    }

    @GetMapping("admin/complaint/reply/{id}/delete")
    public String deleteReply(@PathVariable long id, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "type", defaultValue = "") String type) {
        if (email.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        }
        long complaintId = replyDao.readById(id).getComplaint().getId();
        replyDao.delete(id);
        return "redirect:/admin/complaint/" + complaintId;
    }
}
