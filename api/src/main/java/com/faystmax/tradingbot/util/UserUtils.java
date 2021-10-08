package com.faystmax.tradingbot.util;

import com.faystmax.tradingbot.db.entity.User;
import lombok.experimental.UtilityClass;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * @author Amosov Maxim
 * @since 03.10.2021 : 19:24
 */
@UtilityClass
public class UserUtils {
    /**
     * @param user user
     * @return list of symbols
     */
    public List<String> parseSymbols(@NotNull final User user) {
        return List.of(StringUtils.split(StringUtils.defaultString(user.getActiveSymbols()), ","));
    }

    /**
     * @param symbols symbols
     * @return symbols joined with separator
     */
    public String joinSymbols(final Collection<String> symbols) {
        return String.join(",", symbols);
    }
}
