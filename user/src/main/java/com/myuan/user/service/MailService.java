//package com.myuan.user.service;
//
//import com.myuan.user.entity.MyResult;
//import javax.mail.internet.MimeMessage;
//import lombok.extern.log4j.Log4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//
///*
// * @author liuwei
// * @date 2018/3/1 16:29
// * 发送邮件
// */
//@Component
//@Log4j
//public class MailService {
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Value("${mail.fromMail.addr}")
//    private String from;
//
//    public MyResult sendSimpleMail(String to, String username, String code) {
//        MimeMessage message = mailSender.createMimeMessage();
//
//        try {
//            //true表示需要创建一个multipart message
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom(from);
//            helper.setTo(to);
//            helper.setSubject("密码重置");
//            helper.setText("<!DOCTYPE html><html><head> <meta charset=\"utf-8\"><link rel=\"stylesheet\" th:href=\"@{/res/layui/css/layui.css}\"/><link rel=\"stylesheet\" th:href=\"@{/res/css/global.css}\"/></head><body><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 600px; border: 1px solid #ddd; border-radius: 3px; color: #555; font-family: &#39;Helvetica Neue Regular&#39;,Helvetica,Arial,Tahoma,&#39;Microsoft YaHei&#39;,&#39;San Francisco&#39;,&#39;微软雅黑&#39;,&#39;Hiragino Sans GB&#39;,STHeitiSC-Light; font-size: 12px; height: auto; margin: auto; overflow: hidden; text-align: left; word-break: break-all; word-wrap: break-word;\"> <tbody style=\"margin: 0; padding: 0;\"> <tr style=\"background-color: #393D49; height: 60px; margin: 0; padding: 0;\"> <td style=\"margin: 0; padding: 0;\"> <div style=\"color: #5EB576; margin: 0; margin-left: 30px; padding: 0;\"><a style=\"font-size: 14px; margin: 0; padding: 0; color: #5EB576; text-decoration: none;\" href=\"/\" target=\"_blank\">码猿社区</a></div> </td> </tr> <tr style=\"margin: 0; padding: 0;\"> <td style=\"margin: 0; padding: 30px;\"> <p style=\"line-height: 20px; margin: 0; margin-bottom: 10px; padding: 0;\"> 你好，<em style=\"font-weight: 700;\">" + username + "</em>，请在30分钟内重置您的密码： </p> <div style=\"\"> <a href=\"/user/reset?code=" + code + "  \" style=\"background-color: #009E94; color: #fff; display: inline-block; height: 32px; line-height: 32px; margin: 0 15px 0 0; padding: 0 15px; text-decoration: none;\" target=\"_blank\">立即重置密码</a> </div> <p style=\"line-height: 20px; margin-top: 20px; padding: 10px; background-color: #f2f2f2; font-size: 12px;\"> 如果该邮件不是由你本人操作，请勿进行激活！否则你的邮箱将会被他人绑定。 </p> </td> </tr> <tr style=\"background-color: #fafafa; color: #999; height: 35px; margin: 0; padding: 0; text-align: center;\"> <td style=\"margin: 0; padding: 0;\">系统邮件，请勿直接回复。</td> </tr> </tbody> </table></body></html>", "text/html;charset=UTF-8");
//            mailSender.send(message);
//            log.info("html邮件发送成功");
//            return MyResult.ok("邮件已发送，请查收");
//        } catch (Exception e) {
//            log.error("发送html邮件时发生异常！", e);
//        }
//        return MyResult.error("发送邮件时发生异常,请重试");
//    }
//}
