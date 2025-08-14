package com.teenthofabud.codingchallenge.ecommerce.item.model;

import com.teenthofabud.codingchallenge.ecommerce.category.Category;
import com.teenthofabud.codingchallenge.ecommerce.validator.EnumValidator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemForm {

    @NotBlank(message = "Item name cannot be blank")
    private String name;
    @EnumValidator(enumClazz = Category.class, message = "Item category is invalid", isEmptyAllowed = false)
    private String category;
    @NotBlank(message = "Item description cannot be blank")
    private String description;
    @Min(value = 0, message = "Item price cannot be less than 0")
    private Double price;

}
