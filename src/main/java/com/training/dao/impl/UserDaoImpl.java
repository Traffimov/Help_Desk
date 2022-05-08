package com.training.dao.impl;

import com.training.dao.GenericJPADAO;
import com.training.dao.UserDao;
import com.training.exceptions.ValidationException;
import com.training.model.User;
import com.training.model.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class UserDaoImpl extends GenericJPADAO<User, Long> implements UserDao {

    private static final String SELECT_ALL = "FROM User u WHERE u.userRole = :role";

    private static final String SELECT_BY_EMAIL = "FROM User u WHERE u.email = :email";

    protected UserDaoImpl() {
        super(User.class);
    }

    @Override
    public List<User> getAllByRole(UserRole userRole) {
        return getEntityManager()
                .createQuery(SELECT_ALL, User.class)
                .setParameter("role", userRole)
                .getResultList();
    }

    @Override
    public User getByEmail(String login) {
        if (login == null) {
            throw new ValidationException("Invalid login");
        }
        return getEntityManager()
                .createQuery(SELECT_BY_EMAIL, User.class)
                .setParameter("email", login)
                .getSingleResult();
    }

}
