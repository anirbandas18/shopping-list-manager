package com.teenthofabud.codingchallenge.ecommerce.model.event;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddedToListEvent implements InteractionEvent {

    @NotBlank(message = "Username cannot be blank")
    private String username;
    @Min(value = 1, message = "Item ID must be greater than 0")
    private Long itemId;
    @Min(value = 1, message = "Item ID must be greater than 0")
    private Long cartId;
}
