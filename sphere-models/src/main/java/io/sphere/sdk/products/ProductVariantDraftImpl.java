package io.sphere.sdk.products;

import java.util.Optional;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.attributes.AttributeDraft;

import java.util.List;

class ProductVariantDraftImpl extends Base implements ProductVariantDraft {
    private final Optional<String> sku;
    private final List<Price> prices;
    private final List<AttributeDraft> attributes;

    public ProductVariantDraftImpl(final Optional<String> sku, final List<Price> prices, final List<AttributeDraft> attributes) {
        this.sku = sku;
        this.prices = prices;
        this.attributes = attributes;
    }

    @Override
    public Optional<String> getSku() {
        return sku;
    }

    @Override
    public List<Price> getPrices() {
        return prices;
    }

    @Override
    public List<AttributeDraft> getAttributes() {
        return attributes;
    }
}
