package com.training.config.security;

import com.training.dao.UserDao;
import com.training.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInformation implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userDao.getByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }
        return new UserPrincipal(user);
    }
}
