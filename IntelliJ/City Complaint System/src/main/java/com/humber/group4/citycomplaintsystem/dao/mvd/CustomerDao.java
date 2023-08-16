package com.humber.group4.citycomplaintsystem.dao.mvd;

import com.humber.group4.citycomplaintsystem.models.mvd.Customer;

public interface CustomerDao {
    Customer readById(Long id);

    void create(Customer customer);
}
