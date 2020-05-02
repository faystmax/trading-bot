package com.faystmax.tradingbot.web;

import com.faystmax.tradingbot.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
public class BuildInfoController {
    private final Map<String, String> buildInfo;

    @Autowired
    public BuildInfoController(BuildProperties buildProperties) {
        buildInfo = Map.of(
            "name", buildProperties.getName(),
            "group", buildProperties.getGroup(),
            "version", buildProperties.getVersion(),
            "time", DateUtils.format(Date.from(buildProperties.getTime()))
        );
    }

    @GetMapping(value = {"/", "version"})
    public Map<String, String> version() {
        return buildInfo;
    }
}