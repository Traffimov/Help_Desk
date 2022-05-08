package com.training.service;

import com.training.dto.AuthRequest;
import com.training.dto.AuthResponse;
import com.training.model.User;
import com.training.model.enums.UserRole;

import java.util.List;

public interface UserService {

    List<User> getAllByRole(UserRole userRole);

    User findById(Long id);

    User save(User user);

    User getCurrent();

    User findByLogin(String login);

    AuthResponse authentication(AuthRequest authRequest);

}
