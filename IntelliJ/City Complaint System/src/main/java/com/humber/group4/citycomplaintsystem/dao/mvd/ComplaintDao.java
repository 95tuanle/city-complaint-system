package com.humber.group4.citycomplaintsystem.dao.mvd;

import com.humber.group4.citycomplaintsystem.models.mvd.Complaint;
import com.humber.group4.citycomplaintsystem.models.mvd.Customer;
import com.humber.group4.citycomplaintsystem.models.src.Employee;

import java.util.List;

public interface ComplaintDao {
    void create(Complaint complaint);
    Complaint read(long id);
    void update(Complaint complaint);
    void delete(Long id);

    List<Complaint> list();
    List<Complaint> listByCustomer(Customer customer);
    List<Complaint> listByEmployee(Employee employee);
}
