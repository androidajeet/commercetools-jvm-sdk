package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;

import javax.annotation.Nullable;

public class ProductDiscountDraft extends Base {
    private final ProductDiscountPredicate predicate;
    private final LocalizedStrings name;
    private final String sortOrder;
    private final ProductDiscountValue value;
    private final LocalizedStrings description;
    private final Boolean active;

    private ProductDiscountDraft(final LocalizedStrings name, final LocalizedStrings description, final ProductDiscountPredicate predicate, final ProductDiscountValue value, final String sortOrder, final boolean active) {
        this.active = active;
        this.predicate = predicate;
        this.name = name;
        this.sortOrder = sortOrder;
        this.value = value;
        this.description = description;
    }

    @JsonProperty("isActive")
    public Boolean isActive() {
        return active;
    }

    @Nullable
    public LocalizedStrings getDescription() {
        return description;
    }

    public LocalizedStrings getName() {
        return name;
    }

    public String getPredicate() {
        return predicate.toSpherePredicate();
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public ProductDiscountValue getValue() {
        return value;
    }

    public static ProductDiscountDraft of(final LocalizedStrings name, final LocalizedStrings description, final ProductDiscountPredicate predicate, final ProductDiscountValue value, final String sortOrder, final boolean active) {
        return new ProductDiscountDraft(name, description, predicate, value, sortOrder, active);
    }
}
