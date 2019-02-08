package io.sphere.sdk.models;

import io.sphere.sdk.annotations.IgnoreInQueryModel;

public interface TraceResource {

    @IgnoreInQueryModel
    CreatedBy getCreatedBy();

    @IgnoreInQueryModel
    LastModifiedBy getLastModifiedBy();
}