package org.health.imperialhealthapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableSpringDataWebSupport
@EnableTransactionManagement
@EnableMethodSecurity
public class WebConfiguration {
}
