package com.teenthofabud.codingchallenge.ecommerce.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    private List<String> valueList;
    private boolean isEmptyAllowed;

    public EnumValidatorImpl() {
        this.isEmptyAllowed = false;
        this.valueList = new ArrayList<>();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isEmptyAllowed || valueList.contains(value.toUpperCase());
    }

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        isEmptyAllowed = constraintAnnotation.isEmptyAllowed();
        valueList = Arrays.stream(constraintAnnotation.enumClazz().getEnumConstants()).map(Enum::name).collect(Collectors.toList());
    }

}
