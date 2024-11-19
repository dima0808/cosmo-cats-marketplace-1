package com.example.cosmocatsmarketplace;

import com.example.cosmocatsmarketplace.featureToggle.config.FeatureToggleProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CosmoCatsMarketplaceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CosmoCatsMarketplaceApplication.class, args);
  }

}