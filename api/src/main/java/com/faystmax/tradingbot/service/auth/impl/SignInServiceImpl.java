package com.faystmax.tradingbot.service.auth.impl;

import com.faystmax.tradingbot.config.security.jwt.JwtUtils;
import com.faystmax.tradingbot.dto.auth.UserAuthResponse;
import com.faystmax.tradingbot.service.auth.SignInService;
import com.faystmax.tradingbot.service.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class SignInServiceImpl implements SignInService {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserAuthResponse signIn(final String email, final String password) {
        final var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(auth);

        final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return new UserAuthResponse(jwtUtils.generateJwtToken(auth),
            userDetails.getId(),
            userDetails.getEmail(),
            userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList()));
    }
}
