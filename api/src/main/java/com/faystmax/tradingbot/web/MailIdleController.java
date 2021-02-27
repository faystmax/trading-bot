package com.faystmax.tradingbot.web;

import com.faystmax.tradingbot.dto.MessageResponse;
import com.faystmax.tradingbot.service.mail.MailIdleService;
import com.faystmax.tradingbot.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class MailIdleController {
    private final UserService userService;
    private final MailIdleService mailIdleService;

    @PostMapping("/mailIdle/start")
    public ResponseEntity<MessageResponse> startIdle(Principal principal) {
        mailIdleService.startIdle(userService.findUserByEmail(principal.getName()));
        return ok(new MessageResponse("Mail Idle successfully started!"));
    }

    @PostMapping("/mailIdle/stop")
    public ResponseEntity<MessageResponse> stopIdle(Principal principal) {
        mailIdleService.stopIdle(userService.findUserByEmail(principal.getName()));
        return ok(new MessageResponse("Mail Idle successfully stopped!"));
    }
}
