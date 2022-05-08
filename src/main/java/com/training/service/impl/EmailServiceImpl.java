package com.training.service.impl;

import com.training.model.Ticket;
import com.training.model.User;
import com.training.model.enums.Template;
import com.training.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final SpringTemplateEngine thymeleafTemplateEngine;

    @Override
    public void sendEmail(List<User> to, Ticket ticket, Template template) {

        to.forEach(el -> {
                    MimeMessage message = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper;
                    try {
                        Context thymeleafContext = new Context();
                        thymeleafContext.setVariables(getTicketInformation(ticket, el));

                        String htmlBody = thymeleafTemplateEngine.process(template.getTemplateName(), thymeleafContext);

                        helper = new MimeMessageHelper(message, true, "UTF-8");
                        helper.setTo(el.getEmail());
                        helper.setSubject(template.getSubject());
                        helper.setText(htmlBody, true);
                        javaMailSender.send(message);
                    } catch (MessagingException e) {
                        log.error(e.getMessage());
                    }
                }
        );
    }

    private Map<String, Object> getTicketInformation(Ticket ticket, User user) {
        Map<String, Object> ticketInformation = new HashMap<>();
        ticketInformation.put("id", ticket.getId());
        ticketInformation.put("firstName", user.getFirstName());
        ticketInformation.put("lastName", user.getLastName());
        return ticketInformation;
    }
}
