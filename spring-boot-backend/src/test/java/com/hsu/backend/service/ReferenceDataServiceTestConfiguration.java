package com.hsu.backend.service;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class ReferenceDataServiceTestConfiguration {

    @Bean
    @Primary
    public ReferenceDataService referenceDataService() {
        return Mockito.mock(ReferenceDataService.class);
    }
}
