package com.teenthofabud.codingchallenge.ecommerce.recommender.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommenderDto {

    @NotBlank(message = "Username is required")
    private String username;
    @Min(value = 1, message = "Cart ID must be greater than 0")
    private Long cartId;

}