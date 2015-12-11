package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static org.assertj.core.api.Assertions.*;

public class TaxCategoryByIdGetTest extends IntegrationTest {
    @Test
    public void execution() {
        withTaxCategory(client(), taxCategory -> {
            final TaxCategory loadedTaxCategory = execute(TaxCategoryByIdGet.of(taxCategory.getId()));
            assertThat(loadedTaxCategory).isEqualTo(taxCategory);
        });
    }
}