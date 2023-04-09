package com.humber.group4.citycomplaintsystem.dao.src;

import com.humber.group4.citycomplaintsystem.models.src.Employee;

public interface EmployeeDao {
    void create(Employee employee);

    Employee readById(Long id);

}
