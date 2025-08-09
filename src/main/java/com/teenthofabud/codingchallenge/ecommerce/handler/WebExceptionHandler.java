package com.teenthofabud.codingchallenge.ecommerce.handler;

import com.teenthofabud.codingchallenge.ecommerce.cart.exception.*;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemAlreadyExistsException;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemException;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemNotFoundException;
import com.teenthofabud.codingchallenge.ecommerce.model.ErrorVo;
import com.teenthofabud.codingchallenge.ecommerce.model.ShoppingListManagerSystemException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class WebExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorVo> handle(ConstraintViolationException e) {
        ErrorVo vo = ErrorVo.builder().code("INVALID_VALUE").message(e.getConstraintViolations().iterator().next().getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(vo);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorVo> handle(MethodArgumentNotValidException e) {
        ErrorVo vo = ErrorVo.builder().code("INVALID_VALUE").message(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(vo);
    }

    @ExceptionHandler(value = ShoppingListManagerSystemException.class)
    public ResponseEntity<ErrorVo> handle(ShoppingListManagerSystemException e) {
        ErrorVo vo = ErrorVo.builder().domain("System").code(e.getHttpStatus().name()).message(e.getMessage()).build();
        return ResponseEntity.status(e.getHttpStatus()).body(vo);
    }

    @ExceptionHandler(value = { ItemAlreadyExistsException.class, ItemNotFoundException.class, ItemInvalidException.class })
    public ResponseEntity<ErrorVo> handle(ItemException e) {
        ErrorVo vo = ErrorVo.builder().domain("Item").code(e.getHttpStatus().name()).message(e.getMessage()).build();
        return ResponseEntity.status(e.getHttpStatus()).body(vo);
    }

    @ExceptionHandler(value = { CartAlreadyExistsException.class, CartNotFoundException.class, CartInvalidException.class, CartAbusedException.class, CartMismatchException.class, CartInconsistentException.class })
    public ResponseEntity<ErrorVo> handle(CartException e) {
        ErrorVo vo = ErrorVo.builder().domain("Cart").code(e.getHttpStatus().name()).message(e.getMessage()).build();
        return ResponseEntity.status(e.getHttpStatus()).body(vo);
    }

}
