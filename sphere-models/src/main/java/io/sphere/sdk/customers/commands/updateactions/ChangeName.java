package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerName;

import javax.annotation.Nullable;

/**
 * Changes customer's firstName, lastName, middleName and title fields.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#changeName()}
 * @see Customer
 */
public class ChangeName extends UpdateActionImpl<Customer> {
    private final String firstName;
    private final String lastName;
    @Nullable
    private final String middleName;
    @Nullable
    private final String title;

    private ChangeName(final String firstName, final String lastName, final String middleName, final String title) {
        super("changeName");
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.title = title;
    }

    public static ChangeName of(final CustomerName name) {
        return new ChangeName(name.getFirstName(), name.getLastName(), name.getMiddleName(), name.getTitle());
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Nullable
    public String getMiddleName() {
        return middleName;
    }

    @Nullable
    public String getTitle() {
        return title;
    }
}
