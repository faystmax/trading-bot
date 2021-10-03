package com.faystmax.tradingbot.web;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

/**
 * @author Amosov Maxim
 * @since 03.10.2021 : 11:45
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("binance")
public class BinanceController {
    private final UserService userService;
    private final BinanceService binanceService;

    @GetMapping("total")
    public BigDecimal getTotalUsdtAmount(final Principal principal) {
        return binanceService.getTotalUsdtAmount(userService.findUserByEmail(principal.getName()));
    }

    @PostMapping("symbols/reload")
    public List<String> reloadActiveSymbols(final Principal principal) {
        final User user = userService.findUserByEmail(principal.getName());
        final List<String> activeSymbols = binanceService.getActiveSymbols(user);
        userService.updateUserActiveSymbols(user.getId(), activeSymbols);
        return activeSymbols;
    }
}
