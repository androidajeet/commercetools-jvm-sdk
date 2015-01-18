package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.commands.updateactions.ChangeIsDefault;
import io.sphere.sdk.shippingmethods.commands.updateactions.ChangeName;
import io.sphere.sdk.shippingmethods.commands.updateactions.ChangeTaxCategory;
import io.sphere.sdk.shippingmethods.commands.updateactions.SetDescription;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodQuery;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withUpdateableShippingMethod;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;

public class ShippingMethodUpdateCommandTest extends IntegrationTest {
    @Test
    public void setDescription() throws Exception {
        withUpdateableShippingMethod(client(), shippingMethod -> {
            final String newDescription = randomString();
            assertThat(shippingMethod.getDescription()).isNotEqualTo(Optional.of(newDescription));
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, SetDescription.of(newDescription));
            final ShippingMethod updatedShippingMethod = execute(cmd);
            assertThat(updatedShippingMethod.getDescription().get()).isEqualTo(newDescription);
            return updatedShippingMethod;
        });
    }

    @Test
    public void changeName() throws Exception {
        withUpdateableShippingMethod(client(), shippingMethod -> {
            final String newName = randomString();
            assertThat(shippingMethod.getName()).isNotEqualTo(newName);
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, ChangeName.of(newName));
            final ShippingMethod updatedShippingMethod = execute(cmd);
            assertThat(updatedShippingMethod.getName()).isEqualTo(newName);
            return updatedShippingMethod;
        });
    }

    @Test
    public void changeTaxCategory() throws Exception {
        final TaxCategory newTaxCategory = TaxCategoryFixtures.defaultTaxCategory(client());
        withUpdateableShippingMethod(client(), shippingMethod -> {
            assertThat(shippingMethod.getTaxCategory().getId()).isNotEqualTo(newTaxCategory.getId());
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, ChangeTaxCategory.of(newTaxCategory));
            final ShippingMethod updatedShippingMethod = execute(cmd);
            assertThat(updatedShippingMethod.getTaxCategory().getId()).isEqualTo(newTaxCategory.getId());
            return updatedShippingMethod;
        });
    }

    @Test
    public void changeIsDefault() throws Exception {
        //only one can be default one, so clean up if there is any
        final Query<ShippingMethod> query = ShippingMethodQuery.of().withPredicate(Predicate.of("isDefault = true"));
        final Optional<ShippingMethod> defaultShippingMethodOption = execute(query).head();
        defaultShippingMethodOption.ifPresent(sm -> execute(ShippingMethodUpdateCommand.of(sm, ChangeIsDefault.toFalse())));

        withUpdateableShippingMethod(client(), shippingMethod -> {
            assertThat(shippingMethod.isDefault()).isFalse();
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, ChangeIsDefault.toTrue());
            final ShippingMethod updatedShippingMethod = execute(cmd);
            assertThat(updatedShippingMethod.isDefault()).isTrue();
            return execute(ShippingMethodUpdateCommand.of(updatedShippingMethod, ChangeIsDefault.toFalse()));
        });
    }
}