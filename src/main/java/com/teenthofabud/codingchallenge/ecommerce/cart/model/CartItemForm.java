package com.teenthofabud.codingchallenge.ecommerce.cart.model;

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
public class CartItemForm {

    @NotBlank(message = "Username is required")
    private String username;
    @Min(value = 1, message = "Cart ID must be greater than 0")
    private Long cartId;
    @Min(value = 1, message = "Item ID must be greater than 0")
    private Long itemId;
    @Min(value = 1, message = "Quantity cannot be negative or zero")
    private Integer quantity;
}
