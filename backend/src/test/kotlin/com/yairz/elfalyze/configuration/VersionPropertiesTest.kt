package com.yairz.elfalyze.configuration

import com.yairz.elfalyze.configuration.properties.AppProperties
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class VersionPropertiesTest {
    @Autowired
    private lateinit var appProperties: AppProperties

    @Test
    fun `version parts are non-negative`() {
        assert(appProperties.version.major() >= 0)
        assert(appProperties.version.minor() >= 0)
        assert(appProperties.version.patch() >= 0)
    }

    @Test
    fun `version major and version minor cannot both be zero`() {
        assert(!(appProperties.version.major == 0 && appProperties.version.minor == 0))
    }
}