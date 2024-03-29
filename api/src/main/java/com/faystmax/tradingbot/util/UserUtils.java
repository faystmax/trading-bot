package com.faystmax.tradingbot.util;

import com.faystmax.tradingbot.db.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;

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
