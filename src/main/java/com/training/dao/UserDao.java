package com.training.dao;

import com.training.model.User;
import com.training.model.enums.UserRole;

import java.util.List;

public interface UserDao {

    User save(User user);

    List<User> getAllByRole(UserRole userRole);

    User findById(Long id);

    User getByEmail(String login);

}
