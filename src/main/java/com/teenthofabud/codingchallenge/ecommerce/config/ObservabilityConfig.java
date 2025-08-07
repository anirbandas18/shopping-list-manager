package com.teenthofabud.codingchallenge.ecommerce.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.annotation.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.actuate.autoconfigure.tracing.TracingProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ObservabilityConfig {

    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }

    @Bean
    public SpanAspect spanAspect(MethodInvocationProcessor methodInvocationProcessor) {
        return new SpanAspect(methodInvocationProcessor);
    }

    @Bean
    public MethodInvocationProcessor methodInvocationProcessor(NewSpanParser newSpanParser, Tracer tracer, BeanFactory beanFactory) {
        return new ImperativeMethodInvocationProcessor(newSpanParser, tracer, beanFactory::getBean, beanFactory::getBean);
    }

    @Bean
    public NewSpanParser newSpanParser() {
        return new DefaultNewSpanParser();
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

}
