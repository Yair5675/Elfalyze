package com.yairz.elfalyze.configuration;

import com.yairz.elfalyze.configuration.properties.FileStorageProperties;
import com.yairz.elfalyze.storage.FileStorage;
import com.yairz.elfalyze.storage.FileStorageFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FileStorageConfiguration {
    private final FileStorageProperties fileStorageProperties;
    private final FileStorageFactory fileStorageFactory;

    FileStorageConfiguration(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
        fileStorageFactory = new FileStorageFactory();
    }

    @Bean
    public FileStorage fileStorage() {
        return fileStorageFactory.create(fileStorageProperties.type(), fileStorageProperties.params());
    }
}
