package com.humber.group4.citycomplaintsystem.controllers.md;


import com.humber.group4.citycomplaintsystem.models.md.Customer;

import com.humber.group4.citycomplaintsystem.dao.md.CustomerDao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {
	
    @Autowired
    private CustomerDao customerDao;
    
    
    @GetMapping("/customers")
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerDao.findAll());
        return "customer/list";
    }

    // SAVE , EDIT, DELETE not working...yet
    @PostMapping("/customers/save")
    public String saveCustomer(Customer customer) {
        customerDao.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String editCustomer(@PathVariable("id") int id, Model model) {
        Customer customer = customerDao.findById(id);
        model.addAttribute("customer", customer);
        return "customer/edit";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable("id") int id) {
        customerDao.deleteById(id);
        return "redirect:/customers";
    }
}
