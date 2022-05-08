package com.training.config.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Collections;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${mail.host}")
    private String mailServerHost;

    @Value("${mail.port}")
    private Integer mailServerPort;

    @Value("${mail.transport.protocol}")
    private String protocol;

    @Value("${mail.username}")
    private String mailServerUsername;

    @Value("${mail.password}")
    private String mailServerPassword;

    @Value("${mail.properties.mail.smtp.auth}")
    private String mailServerAuth;

    @Value("${mail.properties.mail.smtp.starttls.enable}")
    private String mailServerStartTls;

    @Value("${mail.debug}")
    private String debug;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailServerHost);
        mailSender.setPort(mailServerPort);

        mailSender.setUsername(mailServerUsername);
        mailSender.setPassword(mailServerPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", mailServerAuth);
        props.put("mail.smtp.starttls.enable", mailServerStartTls);
        props.put("mail.debug", debug);

        return mailSender;
    }

    @Bean
    public ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("mail/MailMessages");
        return messageSource;
    }

    @Bean
    public TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(2);
        templateResolver.setResolvablePatterns(Collections.singleton("templates/*"));
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

}
