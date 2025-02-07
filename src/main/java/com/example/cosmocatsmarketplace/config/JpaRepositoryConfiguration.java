package com.example.cosmocatsmarketplace.config;

import com.example.cosmocatsmarketplace.repository.impl.NaturalIdRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.cosmocatsmarketplace.repository",
    repositoryBaseClass = NaturalIdRepositoryImpl.class)
public class JpaRepositoryConfiguration {
}
