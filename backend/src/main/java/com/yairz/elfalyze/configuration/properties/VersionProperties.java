package com.yairz.elfalyze.configuration.properties;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;


@Validated
@ConfigurationProperties(prefix = "app.version")
public record VersionProperties(@NonNull Integer major, @NonNull Integer minor, @NonNull Integer patch) {
    public VersionProperties {
        Objects.requireNonNull(major, "Missing major in version");
        Objects.requireNonNull(minor, "Missing minor in version");
        Objects.requireNonNull(patch, "Missing patch in version");
    }

    @Override
    public @NonNull String toString() {
        return String.format("%d.%d.%d", major, minor, patch);
    }
}
