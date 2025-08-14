package com.teenthofabud.codingchallenge.ecommerce.model.event;

public interface InteractionEvent {

    public void setUsername(String username);
    public String getUsername();
    public void setItemId(Long itemId);
    public Long getItemId();
    public void setCartId(Long cartId);
    public Long getCartId();

}
