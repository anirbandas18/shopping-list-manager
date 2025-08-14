package com.teenthofabud.codingchallenge.ecommerce.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class AIConfig {

    private final Resource recommenderSystemPrompt;

    //public AIConfig( @Value("classpath:/rag-system-strict-prompt-template.st") Resource recipeSystemPrompt) {
    public AIConfig( @Value("classpath:/prompts/rag-system-flexible-prompt-template.st") Resource recipeSystemPrompt) {
        this.recommenderSystemPrompt = recipeSystemPrompt;
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem(recommenderSystemPrompt).build();
    }

}
