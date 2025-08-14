package com.teenthofabud.codingchallenge.ecommerce.health;

import com.teenthofabud.codingchallenge.ecommerce.exception.ShoppingListManagerSystemException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.availability.ReadinessStateHealthIndicator;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.concurrent.atomic.AtomicBoolean;


public class ReadynessHealthIndicator extends ReadinessStateHealthIndicator implements ApplicationListener<ApplicationReadyEvent> {
    private final AtomicBoolean ready = new AtomicBoolean();

    private String applicationName;

    @Value("${spring.application.name}")
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public ReadynessHealthIndicator(ApplicationAvailability availability) {
        super(availability);
    }

    @Override
    protected AvailabilityState getState(ApplicationAvailability applicationAvailability) {
        return ready.get()
                ? ReadinessState.ACCEPTING_TRAFFIC
                : ReadinessState.REFUSING_TRAFFIC;
    }

    public void markAsReady() {
        if (ready.get()) {
            throw new ShoppingListManagerSystemException("Readiness check " + applicationName + " already started");
        }
        ready.set(true);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.markAsReady();
    }
}
