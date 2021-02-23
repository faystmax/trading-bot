package com.faystmax.tradingbot.web;

import com.faystmax.tradingbot.dto.MessageResponse;
import com.faystmax.tradingbot.dto.NewPasswordDto;
import com.faystmax.tradingbot.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class PasswordController {
    private final UserService userService;

    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody NewPasswordDto dto, Principal principal) {
        userService.changePassword(principal.getName(), dto.getOldPassword(), dto.getNewPassword());
        return ok(new MessageResponse("Password successfully updated!"));
    }
}
