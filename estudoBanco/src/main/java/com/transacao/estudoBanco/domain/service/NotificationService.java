package com.transacao.estudoBanco.domain.service;

import com.transacao.estudoBanco.domain.dto.NotificationDTO;
import com.transacao.estudoBanco.domain.dto.NotificationResponseDTO;
import com.transacao.estudoBanco.domain.user.User;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Builder
@Log4j2
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();

        NotificationDTO newNotification = NotificationDTO.builder()
                .email(message)
                .message(email).build();

        ResponseEntity<NotificationResponseDTO>  notificationSend = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", newNotification, NotificationResponseDTO.class);

        if(!(notificationSend.getStatusCode() == HttpStatus.OK)){
            log.info("Testando");
            throw new Exception("Serviço indisponivel no momento");
        }
    }
}
