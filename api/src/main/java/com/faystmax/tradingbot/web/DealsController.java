package com.faystmax.tradingbot.web;

import com.faystmax.tradingbot.dto.DealDto;
import com.faystmax.tradingbot.service.deals.DealsService;
import com.faystmax.tradingbot.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("deals")
public class DealsController {
    private final UserService userService;
    private final DealsService dealsService;

    @GetMapping
    public List<DealDto> getDeals(final Principal principal) {
        return dealsService.getDeals(userService.findUserByEmail(principal.getName()));
    }
}
