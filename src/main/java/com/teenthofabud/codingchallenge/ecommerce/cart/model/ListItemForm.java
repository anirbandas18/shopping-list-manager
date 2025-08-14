package com.teenthofabud.codingchallenge.ecommerce.cart.model;

import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListItemForm {

    @Min(value = 1, message = "Item ID must be greater than 0")
    private Long itemId;
    @Min(value = 1, message = "Quantity cannot be negative or zero")
    private Integer quantity;
}
