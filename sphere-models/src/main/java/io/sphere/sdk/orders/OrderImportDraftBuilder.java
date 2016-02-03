package io.sphere.sdk.orders;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.TaxedPrice;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.*;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class OrderImportDraftBuilder extends Base implements Builder<OrderImportDraft> {
    @Nullable
    private String orderNumber;
    @Nullable
    private String customerId;
    @Nullable
    private String customerEmail;
    @Nullable
    private List<LineItemImportDraft> lineItems = Collections.emptyList();
    private List<CustomLineItemImportDraft> customLineItems = Collections.emptyList();
    private MonetaryAmount totalPrice;
    @Nullable
    private TaxedPrice taxedPrice;
    @Nullable
    private Address shippingAddress;
    @Nullable
    private Address billingAddress;
    @Nullable
    private Reference<CustomerGroup> customerGroup;
    @Nullable
    private CountryCode country;
    private OrderState orderState;
    @Nullable
    private ShipmentState shipmentState;
    @Nullable
    private PaymentState paymentState;
    @Nullable
    private OrderShippingInfo shippingInfo;
    @Nullable
    private ZonedDateTime completedAt;

    private OrderImportDraftBuilder(final MonetaryAmount totalPrice, final OrderState orderState) {
        this.totalPrice = totalPrice;
        this.orderState = orderState;
    }

    /**
     * String that unique identifies an order. It can be used to create more human-readable (in contrast to ID) identifier for the order. It should be unique within a project.
     * @param orderNumber ID to set
     * @return this builder
     */
    public OrderImportDraftBuilder orderNumber(@Nullable final String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public OrderImportDraftBuilder customerId(@Nullable final String customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderImportDraftBuilder customerEmail(@Nullable final String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public OrderImportDraftBuilder lineItems(final List<LineItemImportDraft> lineItems) {
        this.lineItems = lineItems;
        return this;
    }

    public OrderImportDraftBuilder customLineItems(final List<CustomLineItemImportDraft> customLineItems) {
        this.customLineItems = customLineItems;
        return this;
    }

    public OrderImportDraftBuilder totalPrice(final MonetaryAmount totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public OrderImportDraftBuilder taxedPrice(@Nullable  final TaxedPrice taxedPrice) {
        this.taxedPrice = taxedPrice;
        return this;
    }

    public OrderImportDraftBuilder shippingAddress(@Nullable final Address shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public OrderImportDraftBuilder billingAddress(@Nullable final Address billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public OrderImportDraftBuilder customerGroup(@Nullable final Referenceable<CustomerGroup> customerGroup) {
        this.customerGroup =  Optional.ofNullable(customerGroup).map(Referenceable::toReference).orElse(null);
        return this;
    }

    public OrderImportDraftBuilder country(@Nullable final CountryCode country) {
        this.country = country;
        return this;
    }

    public OrderImportDraftBuilder orderState(final OrderState orderState) {
        this.orderState = orderState;
        return this;
    }

    public OrderImportDraftBuilder shipmentState(@Nullable final ShipmentState shipmentState) {
        this.shipmentState = shipmentState;
        return this;
    }

    public OrderImportDraftBuilder paymentState(@Nullable final PaymentState paymentState) {
        this.paymentState = paymentState;
        return this;
    }

    public OrderImportDraftBuilder shippingInfo(@Nullable final OrderShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
        return this;
    }

    public OrderImportDraftBuilder completedAt(@Nullable final ZonedDateTime completedAt) {
        this.completedAt = completedAt;
        return this;
    }

    /**
     * Creates a builder for {@link OrderImportDraft} with at least one line item.
     * You can add {@link io.sphere.sdk.carts.CustomLineItem}s with {@link #customLineItems(java.util.List)}.
     *
     * @param totalPrice the total price of the order
     * @param orderState the state of the order
     * @param lineItems a list of line items with at least one element
     * @return a new builder
     */
    public static OrderImportDraftBuilder ofLineItems(final MonetaryAmount totalPrice, final OrderState orderState, final List<LineItemImportDraft> lineItems) {
        return new OrderImportDraftBuilder(totalPrice, orderState).lineItems(lineItems);
    }

    /**
     * Creates a builder for {@link OrderImportDraft} with at least one custom line item.
     * You can add {@link io.sphere.sdk.carts.LineItem}s with {@link #lineItems(java.util.List)}.
     *
     * @param totalPrice the total price of the order
     * @param orderState the state of the order
     * @param customLineItems a list of custom line items with at least one element
     * @return a new builder
     */
    public static OrderImportDraftBuilder ofCustomLineItems(final MonetaryAmount totalPrice, final OrderState orderState, final List<CustomLineItemImportDraft> customLineItems) {
        return new OrderImportDraftBuilder(totalPrice, orderState).customLineItems(customLineItems);
    }

    @Override
    public OrderImportDraft build() {
        return new OrderImportDraftImpl(billingAddress, orderNumber, customerId, customerEmail, lineItems, customLineItems, totalPrice, taxedPrice, shippingAddress, customerGroup, country, orderState, shipmentState, paymentState, shippingInfo, completedAt);
    }
}
