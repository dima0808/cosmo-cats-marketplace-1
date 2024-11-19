package com.example.cosmocatsmarketplace.featureToggle.aspect;

import com.example.cosmocatsmarketplace.featureToggle.FeatureToggles;
import com.example.cosmocatsmarketplace.featureToggle.annotation.FeatureToggle;
import com.example.cosmocatsmarketplace.featureToggle.exception.FeatureToggleNotEnabledException;
import com.example.cosmocatsmarketplace.featureToggle.service.FeatureToggleService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Before("@annotation(featureToggle)")
    public void checkFeatureToggleBefore(FeatureToggle featureToggle) {
        FeatureToggles toggle = featureToggle.value();
        if (!featureToggleService.check(toggle.getFeatureName())) {
            log.warn("Feature toggle {} is not enabled!", toggle.getFeatureName());
            throw new FeatureToggleNotEnabledException(toggle.getFeatureName());
        }

    }

}