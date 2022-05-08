package com.training.service;


import com.training.model.Ticket;
import com.training.model.User;
import com.training.model.enums.Template;

import java.util.List;

public interface EmailService {

    void sendEmail(List<User> to, Ticket ticket, Template template);
}
