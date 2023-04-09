package com.humber.group4.citycomplaintsystem.controllers.mvd;

import com.humber.group4.citycomplaintsystem.controllers.natl.UserController;
import com.humber.group4.citycomplaintsystem.dao.mvd.ComplaintDao;
import com.humber.group4.citycomplaintsystem.dao.mvd.CustomerDao;
import com.humber.group4.citycomplaintsystem.dao.natl.ReplyDao;
import com.humber.group4.citycomplaintsystem.dao.natl.UserDao;
import com.humber.group4.citycomplaintsystem.models.mvd.Customer;
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
public class CustomerController {
    final
    CustomerDao customerDao;
    final
    UserDao userDao;
    final
    ComplaintDao complaintDao;
    final
    ReplyDao replyDao;

    public CustomerController(CustomerDao customerDao, UserDao userDao, ComplaintDao complaintDao, ReplyDao replyDao) {
        this.customerDao = customerDao;
        this.userDao = userDao;
        this.complaintDao = complaintDao;
        this.replyDao = replyDao;
    }

    @GetMapping("customer/sign-up")
    public String signUp(Model model, HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            return "redirect:/user/sign-up";
        }
        model.addAttribute("customer", new Customer());
        return "customer/sign-up";
    }

    @PostMapping("customer/sign-up")
    public String signUp(@ModelAttribute("customer") Customer customer, HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            return "redirect:/user/sign-up";
        }
        httpSession.invalidate();
        userDao.create(user);
        customer.setUser(user);
        customerDao.create(customer);
        UserController.setCookies(response, user);
        return "redirect:/customer/index";
    }

    @GetMapping("customer/index")
    public String index(Model model, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "type", defaultValue = "") String type) {
        if (email.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        }
        model.addAttribute("complaints", complaintDao.listByCustomer(customerDao.readById(userDao.readByEmail(email).getId())));
        return "customer/index";
    }

    @GetMapping("customer/complaint/{id}")
    public String viewComplaint(@PathVariable long id, Model model, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "type", defaultValue = "") String type) {
        if (email.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        }
        model.addAttribute("complaint", complaintDao.read(id));
        model.addAttribute("reply", new Reply());
        model.addAttribute("replies", replyDao.listByComplaint(complaintDao.read(id)));
        return "customer/view-complaint";
    }

    @PostMapping("customer/complaint/{id}/reply")
    public String reply(@PathVariable long id, @ModelAttribute("reply") Reply reply, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "type", defaultValue = "") String type) {
        if (email.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        }
        reply.setId(null);
        reply.setComplaint(complaintDao.read(id));
        reply.setUser(userDao.readByEmail(email));
        reply.setDate(LocalDateTime.now());
        replyDao.create(reply);
        return "redirect:/customer/complaint/" + id;
    }

    @GetMapping("customer/complaint/reply/{id}/delete")
    public String deleteReply(@PathVariable long id, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "type", defaultValue = "") String type) {
        if (email.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        }
        long complaintId = replyDao.readById(id).getComplaint().getId();
        replyDao.delete(id);
        return "redirect:/customer/complaint/" + complaintId;
    }
}
