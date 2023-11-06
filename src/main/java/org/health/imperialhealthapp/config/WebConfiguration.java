package org.health.imperialhealthapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableSpringDataWebSupport
@EnableMethodSecurity
public class WebConfiguration {
}
