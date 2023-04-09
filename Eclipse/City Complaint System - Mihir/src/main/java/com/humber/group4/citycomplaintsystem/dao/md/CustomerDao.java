package com.humber.group4.citycomplaintsystem.dao.md;

import com.humber.group4.citycomplaintsystem.models.md.Customer;
import java.util.List;

public interface CustomerDao {
	List<Customer> findAll();
    Customer findById(int id);
    void save(Customer customer);
    void deleteById(int id);
}
