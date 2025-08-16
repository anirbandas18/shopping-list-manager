package com.teenthofabud.codingchallenge.ecommerce.config;

import io.micrometer.observation.ObservationPredicate;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.observation.ServerRequestObservationContext;

@Configuration
public class ObservationConfig {

    @Bean
    public ObservationPredicate ignoreObservations() {
        return (name, context) -> {
            if(context instanceof ServerRequestObservationContext) {
                return !((ServerRequestObservationContext) context).getCarrier().getRequestURI().startsWith("/actuator") ||
                        !((ServerRequestObservationContext) context).getCarrier().getRequestURI().startsWith("/swagger") ||
                        !((ServerRequestObservationContext) context).getCarrier().getRequestURI().startsWith("/v3/api-docs");
            } else return !name.startsWith("spring.security");
        };
    }

    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }

}
