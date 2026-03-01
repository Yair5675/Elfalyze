package com.yairz.elfalyze.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Objects;


@ConfigurationProperties(prefix = "app.file-storage")
public record FileStorageProperties(String type, Map<String, String> params) {
    public FileStorageProperties {
        Objects.requireNonNull(type, "Missing storage type");
        Objects.requireNonNull(params, "Missing storage parameters");
    }
}
