package com.training.service.impl;

import com.training.config.jwt.JwtUtil;
import com.training.dto.AuthRequest;
import com.training.dto.AuthResponse;
import com.training.dao.UserDao;
import com.training.model.User;
import com.training.model.enums.UserRole;
import com.training.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtTokenUtil;

    @Override
    @Transactional
    public AuthResponse authentication(AuthRequest authRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect login or password", e);
        }
        String jwt = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
        return new AuthResponse(jwt);
    }

    @Override
    @Transactional(readOnly = true)
    public User getCurrent() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDao.getByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllByRole(UserRole userRole) {
        return userDao.getAllByRole(userRole);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        return userDao.getByEmail(login);
    }


}
