package io.sphere.sdk.projects.commands;

import io.sphere.sdk.projects.CartValueDraftBuilder;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.commands.updateactions.SetShippingRateInputType;
import io.sphere.sdk.projects.queries.ProjectGet;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectCartValueIntegrationTest extends ProjectIntegrationTest {

    @Test
    public void execution() {

        final Project project = client().executeBlocking(ProjectGet.of());
        final Project updatedProjectCartValue = client().executeBlocking(ProjectUpdateCommand.of(project, SetShippingRateInputType.of(CartValueDraftBuilder.of().build())));
        assertThat(updatedProjectCartValue.getShippingRateInputType()).isNotNull();
        assertThat(updatedProjectCartValue.getShippingRateInputType().getType()).isEqualTo("CartValue");

        //TODO remove the logging test
        logger.error("the project was created at {} ", updatedProjectCartValue.getCreatedAt());

        if (updatedProjectCartValue.getCreatedAt().isAfter(DateTime.parse("2019-02-01").toGregorianCalendar().toZonedDateTime())) {
            assertThat(updatedProjectCartValue.getLastModifiedBy()).isNotNull();
            assertThat(updatedProjectCartValue.getCreatedBy()).isNotNull();
        }
    }
}
