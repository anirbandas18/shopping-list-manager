package com.teenthofabud.codingchallenge.ecommerce.health;

import com.teenthofabud.codingchallenge.ecommerce.exception.ShoppingListManagerSystemException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.availability.LivenessStateHealthIndicator;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.util.concurrent.atomic.AtomicBoolean;

public class LivenessHealthIndicator extends LivenessStateHealthIndicator implements ApplicationListener<ApplicationStartedEvent> {

    private final AtomicBoolean ready = new AtomicBoolean();

    private String applicationName;

    @Value("${spring.application.name}")
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public LivenessHealthIndicator(ApplicationAvailability availability) {
        super(availability);
    }

    @Override
    protected AvailabilityState getState(ApplicationAvailability applicationAvailability) {
        LivenessState livenessState = applicationAvailability.getLivenessState();
        return super.getState(applicationAvailability);
    }

    public void markAsReady() {
        if (ready.get()) {
            throw new ShoppingListManagerSystemException("Liveness check " + applicationName + " already started");
        }
        ready.set(true);
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        this.markAsReady();
    }
}