package com.faystmax.tradingbot.service.deals;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.service.binance.BinanceService;
import com.faystmax.tradingbot.service.user.UserService;
import com.faystmax.tradingbot.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Amosov Maxim
 * @since 09.10.2021 : 22:10
 */
@Service
@RequiredArgsConstructor
public class SymbolsServiceImpl implements SymbolsService {
    private final UserService userService;
    private final BinanceService binanceService;

    @Override
    public List<String> updateActiveSymbols(final User user) {
        final List<String> currentActiveSymbols = UserUtils.parseSymbols(user);
        final List<String> newActiveSymbols = binanceService.getActiveSymbols(user);
        final List<String> resultActiveSymbols = Stream.of(currentActiveSymbols, newActiveSymbols)
            .flatMap(Collection::stream).distinct()
            .collect(Collectors.toList());

        userService.updateUserActiveSymbols(user.getId(), resultActiveSymbols);
        return resultActiveSymbols;
    }
}
