package com.humber.group4.citycomplaintsystem.controllers.src;

import com.humber.group4.citycomplaintsystem.controllers.natl.UserController;
import com.humber.group4.citycomplaintsystem.dao.mvd.ComplaintDao;
import com.humber.group4.citycomplaintsystem.dao.natl.ReplyDao;
import com.humber.group4.citycomplaintsystem.dao.natl.UserDao;
import com.humber.group4.citycomplaintsystem.dao.src.EmployeeDao;
import com.humber.group4.citycomplaintsystem.models.src.Employee;
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
public class EmployeeController {

    private final EmployeeDao employeeDao;
    private final UserDao userDao;
    private final ComplaintDao complaintDao;
    private final ReplyDao replyDao;

    public EmployeeController(EmployeeDao employeeDao, UserDao userDao, ComplaintDao complaintDao, ReplyDao replyDao) {
        this.employeeDao = employeeDao;
        this.userDao = userDao;
        this.complaintDao = complaintDao;
        this.replyDao = replyDao;
    }

    @GetMapping("employee/sign-up")
    public String signUp(Model model, HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            return "redirect:/user/sign-up";
        }
        model.addAttribute("employee", new Employee());
        return "employee/sign-up";
    }

    @PostMapping("employee/sign-up")
    public String signUp(@ModelAttribute("employee") Employee employee, HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            return "redirect:/user/sign-up";
        }
        httpSession.invalidate();
        userDao.create(user);
        employee.setUser(user);
        employeeDao.create(employee);
        UserController.setCookies(response, user);
        return "redirect:/employee/index";
    }

    @GetMapping("employee/index")
    public String index(Model model, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "type", defaultValue = "") String type) {
        if (email.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        }
        model.addAttribute("complaints", complaintDao.listByEmployee(employeeDao.readById(userDao.readByEmail(email).getId())));
        return "employee/index";
    }

    @GetMapping("employee/complaint/{id}")
    public String viewComplaint(@PathVariable long id, Model model, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "type", defaultValue = "") String type) {
        if (email.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        }
        model.addAttribute("complaint", complaintDao.read(id));
        model.addAttribute("reply", new Reply());
        model.addAttribute("replies", replyDao.listByComplaint(complaintDao.read(id)));
        return "employee/view-complaint";
    }

    @PostMapping("employee/complaint/{id}/reply")
    public String reply(@PathVariable long id, @ModelAttribute("reply") Reply reply, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "type", defaultValue = "") String type) {
        if (email.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        }
        reply.setId(null);
        reply.setComplaint(complaintDao.read(id));
        reply.setUser(userDao.readByEmail(email));
        reply.setDate(LocalDateTime.now());
        replyDao.create(reply);
        return "redirect:/employee/complaint/" + id;
    }

    @GetMapping("employee/complaint/reply/{id}/delete")
    public String deleteReply(@PathVariable long id, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "type", defaultValue = "") String type) {
        if (email.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        }
        long complaintId = replyDao.readById(id).getComplaint().getId();
        replyDao.delete(id);
        return "redirect:/employee/complaint/" + complaintId;
    }
}
