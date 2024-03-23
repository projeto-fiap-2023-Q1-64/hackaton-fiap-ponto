package br.fiap.projeto.ponto.utils;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {


    public void enviarEmail(String destinatario, String assunto, String htmlBody) throws MessagingException {
        // Configurações do servidor de e-mail (neste exemplo, para o Gmail)
        String host = "smtp.gmail.com";
        int porta = 587;
        String usuario = "pos.fiap.grupo.64@gmail.com";
        String senha = "vhml dvbc dzsh usce";
        // Configurações adicionais (para usar TLS)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", porta);

        // Cria uma sessão com autenticação
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, senha);
            }
        });

        try {
            // Cria uma mensagem de e-mail
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuario));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(assunto);

            // Define o conteúdo do e-mail como o JSON fornecido
            message.setContent(htmlBody, "text/html; charset=utf-8");


            // Envia o e-mail
            Transport.send(message);

            System.out.println("E-mail enviado com sucesso para: " + destinatario);
        } catch (MessagingException e) {
            System.out.println("Erro ao enviar e-mail: " + e.getMessage());
            throw e;
        }
    }
}
