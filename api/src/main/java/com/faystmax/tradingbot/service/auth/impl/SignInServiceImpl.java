package com.faystmax.tradingbot.service.auth.impl;

import com.faystmax.tradingbot.config.security.jwt.JwtUtils;
import com.faystmax.tradingbot.dto.JwtDto;
import com.faystmax.tradingbot.service.auth.SignInService;
import com.faystmax.tradingbot.service.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SignInServiceImpl implements SignInService {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtDto signIn(String email, String password) {
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(auth);

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return new JwtDto(jwtUtils.generateJwtToken(auth),
            userDetails.getId(),
            userDetails.getEmail(),
            userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList()));
    }
}
