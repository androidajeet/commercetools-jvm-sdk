package io.sphere.sdk.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

@ResourceValue
@JsonDeserialize(as = CreatedByImpl.class)
public interface CreatedBy {

    String getClientId();

    //TODO add user
    

    @Nullable
    String getExternalUserId();

    Reference<Customer> getCustomer();

    @Nullable
    String getAnonymousId();

}
