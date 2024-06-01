package com.example.hieustore.util;

import com.example.hieustore.domain.dto.common.DataMailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SendMailUtil {

    @Value("${mail.from_to}")
    public String fromName;

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    /**
     * Gửi mail với file html
     *
     * @param mail     Thông tin của mail cần gửi
     * @param template Tên file html trong folder resources/template
     *                 Example: Index.html
     */
    public void sendEmailWithHTML(DataMailDto mail, String template) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        helper.setFrom(Objects.requireNonNull(((JavaMailSenderImpl) mailSender).getUsername()), fromName);
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        Context context = new Context();
        context.setVariables(mail.getProperties());
        String htmlMsg = templateEngine.process(template, context);
        helper.setText(htmlMsg, true);
        mailSender.send(message);
    }

    /**
     * Gửi mail với tệp đính kèm
     *
     * @param mail  Thông tin của mail cần gửi
     * @param files File cần gửi
     */
    public void sendMailWithAttachment(DataMailDto mail, MultipartFile[] files) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getContent());
        if (files != null) {
            for (MultipartFile file : files) {
                helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
            }
        }
        mailSender.send(message);
    }

}
