package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;

/**

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#setCustomerEmail()}
 */
public class SetCustomerEmail extends UpdateActionImpl<Cart> {
    private final String email;

    private SetCustomerEmail(final String email) {
        super("setCustomerEmail");
        this.email = email;
    }

    public static SetCustomerEmail of(final String email) {
        return new SetCustomerEmail(email);
    }

    public String getEmail() {
        return email;
    }
}
