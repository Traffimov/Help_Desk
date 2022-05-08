package com.training;

import com.training.dao.CategoryDao;
import com.training.dao.UserDao;
import com.training.model.Category;
import com.training.model.User;
import com.training.model.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    private final CategoryDao categoryDao;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        initUsers();
        initCategories();
    }

    private void initCategories() {
        Category category = new Category();
        category.setName("Application & Services");

        Category category1 = new Category();
        category1.setName("Benefits & Paper Work");

        Category category2 = new Category();
        category2.setName("Hardware & Software");

        Category category3 = new Category();
        category3.setName("People Management");

        Category category4 = new Category();
        category4.setName("Security & Access");

        Category category5 = new Category();
        category5.setName("Workplaces & Facilities");

        categoryDao.save(category);
        categoryDao.save(category1);
        categoryDao.save(category2);
        categoryDao.save(category3);
        categoryDao.save(category4);
        categoryDao.save(category5);
    }

    private void initUsers() {
        User employee1 = new User();
        employee1.setEmail("user1_mogilev@yopmail.com");
        employee1.setPassword(passwordEncoder.encode("P@ssword1"));
        employee1.setUserRole(UserRole.ROLE_EMPLOYEE);
        employee1.setFirstName("employee");
        employee1.setLastName("1");

        User employee2 = new User();
        employee2.setEmail("user2_mogilev@yopmail.com");
        employee2.setPassword(passwordEncoder.encode("P@ssword1"));
        employee2.setUserRole(UserRole.ROLE_EMPLOYEE);
        employee2.setFirstName("employee");
        employee2.setLastName("2");

        User manager1 = new User();
        manager1.setEmail("manager1_mogilev@yopmail.com");
        manager1.setPassword(passwordEncoder.encode("P@ssword1"));
        manager1.setUserRole(UserRole.ROLE_MANAGER);
        manager1.setFirstName("manager");
        manager1.setLastName("1");


        User manager2 = new User();
        manager2.setEmail("manager2_mogilev@yopmail.com");
        manager2.setPassword(passwordEncoder.encode("P@ssword1"));
        manager2.setUserRole(UserRole.ROLE_MANAGER);
        manager2.setFirstName("manager");
        manager2.setLastName("2");

        User engineer1 = new User();
        engineer1.setEmail("engineer1_mogilev@yopmail.com");
        engineer1.setPassword(passwordEncoder.encode("P@ssword1"));
        engineer1.setUserRole(UserRole.ROLE_ENGINEER);
        engineer1.setFirstName("engineer");
        engineer1.setLastName("2");

        User engineer2 = new User();
        engineer2.setEmail("engineer2_mogilev@yopmail.com");
        engineer2.setPassword(passwordEncoder.encode("P@ssword1"));
        engineer2.setUserRole(UserRole.ROLE_ENGINEER);
        engineer2.setFirstName("engineer");
        engineer2.setLastName("2");

        userDao.save(employee1);
        userDao.save(employee2);
        userDao.save(manager1);
        userDao.save(manager2);
        userDao.save(engineer1);
        userDao.save(engineer2);
    }
}
