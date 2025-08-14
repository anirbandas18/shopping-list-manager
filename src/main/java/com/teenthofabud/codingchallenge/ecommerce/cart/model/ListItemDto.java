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
public class ListItemDto {

    private ListItemForm form;
    @NotBlank(message = "Username is required")
    private String username;
    @Min(value = 1, message = "List ID must be greater than 0")
    private Long listId;

}
