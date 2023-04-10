package com.humber.group4.citycomplaintsystem.controllers.mvd;

import com.humber.group4.citycomplaintsystem.dao.mvd.ComplaintDao;
import com.humber.group4.citycomplaintsystem.dao.mvd.CustomerDao;
import com.humber.group4.citycomplaintsystem.dao.natl.UserDao;
import com.humber.group4.citycomplaintsystem.models.mvd.Complaint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class ComplaintController {
    final
    ComplaintDao complaintDao;
    final
    UserDao userDao;
    final
    CustomerDao customerDao;

    public ComplaintController(ComplaintDao complaintDao, UserDao userDao, CustomerDao customerDao) {
        this.complaintDao = complaintDao;
        this.userDao = userDao;
        this.customerDao = customerDao;
    }

    @GetMapping("complaint/create")
    public String create(Model model, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "type", defaultValue = "") String type) {
        if (email.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        } else if (!type.equals("customer")) {
            return "redirect:/user/sign-in";
        }
        model.addAttribute("complaint", new Complaint());
        return "customer/create-complaint";
    }

    @PostMapping("complaint/create")
    public String create(@ModelAttribute("complaint") Complaint complaint, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "type", defaultValue = "") String type) {
        if (email.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        } else if (!type.equals("customer")) {
            return "redirect:/user/sign-in";
        }
        complaint.setCustomer(customerDao.readById(userDao.readByEmail(email).getId()));
        complaint.setDate(LocalDateTime.now());
        complaintDao.create(complaint);
        return "redirect:/customer/index";
    }

    @GetMapping("complaint/delete/{id}")
    public String delete(@PathVariable("id") Long id, @CookieValue(value = "email", defaultValue = "") String email, @CookieValue(value = "type", defaultValue = "") String type) {
        if (email.equals("") || type.equals("")) {
            return "redirect:/user/sign-in";
        }
        complaintDao.delete(id);
        switch (type) {
            case "customer":
                return "redirect:/customer/index";
            case "employee":
                return "redirect:/employee/index";
            case "admin":
                return "redirect:/admin/index";
            default:
                return "redirect:/user/sign-in";
        }
    }
}
