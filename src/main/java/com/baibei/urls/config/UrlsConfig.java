package com.baibei.urls.config;

import com.baibei.urls.components.Base62;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlsConfig {

    @Bean
    public Base62 base62() {
        return new Base62();
    }
}
