package com.humber.group4.citycomplaintsystem.dao.natl;

import com.humber.group4.citycomplaintsystem.models.natl.User;

import java.util.List;

public interface UserDao {
    void create(User user);

    User readByEmail(String email);

    List<User> listByType(String type);
}
