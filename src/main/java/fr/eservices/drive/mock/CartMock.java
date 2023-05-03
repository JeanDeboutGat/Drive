package fr.eservices.drive.mock;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import fr.eservices.drive.model.Cart;

@Component
@Qualifier("mock")
public class CartMock {

    private HashMap<Integer, Cart> carts = new HashMap<>();

    public void setCart(int i, Cart cart) {
        carts.put(i, cart);
    }

}