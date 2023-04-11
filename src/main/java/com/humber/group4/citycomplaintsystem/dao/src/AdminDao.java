package com.humber.group4.citycomplaintsystem.dao.src;

import com.humber.group4.citycomplaintsystem.models.src.Admin;

public interface AdminDao {
    void create(Admin admin);
    Admin readById(Long id);
}
