package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.attributes.AttributeDefinition;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.producttypes.ProductType;

import java.util.List;

/**
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandTest#changeAttributeOrder()}
 */
public class ChangeAttributeOrder extends UpdateActionImpl<ProductType> {
    private final List<AttributeDefinition> attributes;

    private ChangeAttributeOrder(final List<AttributeDefinition> attributes) {
        super("changeAttributeOrder");
        this.attributes = attributes;
    }

    public static ChangeAttributeOrder of(final List<AttributeDefinition> attributes) {
        return new ChangeAttributeOrder(attributes);
    }

    public List<AttributeDefinition> getAttributes() {
        return attributes;
    }
}
