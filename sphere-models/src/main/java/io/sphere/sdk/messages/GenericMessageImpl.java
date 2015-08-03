package io.sphere.sdk.messages;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class GenericMessageImpl<R> extends DefaultModelImpl<Message> implements GenericMessage<R> {
    protected final Long sequenceNumber;
    protected final JsonNode resource;
    protected final Long resourceVersion;
    protected final String type;
    @JsonIgnore
    private final Map<String, JsonNode> furtherFields = new HashMap<>();

    public GenericMessageImpl(final String id, final Long version, final ZonedDateTime createdAt,
                              final ZonedDateTime lastModifiedAt, final JsonNode resource,
                              final Long sequenceNumber, final Long resourceVersion,
                              final String type) {
        super(id, version, createdAt, lastModifiedAt);
        this.resource = resource;
        this.sequenceNumber = sequenceNumber;
        this.resourceVersion = resourceVersion;
        this.type = type;
    }

    @Override
    public Long getResourceVersion() {
        return resourceVersion;
    }

    @Override
    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Reference<Message> toReference() {
        return Reference.of(Message.typeId(), getId(), this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Reference<R> getResource() {
        return (Reference<R>) SphereJsonUtils.readObject(resource, new TypeReference<Reference<Order>>() {
        });
    }

    @Override
    public JsonNode getPayload() {
        final ObjectMapper objectMapper = SphereJsonUtils.newObjectMapper();
        final ObjectNode jsonNode = objectMapper.createObjectNode();
        furtherFields.entrySet().forEach(entry -> jsonNode.replace(entry.getKey(), entry.getValue()));
        return jsonNode;
    }

    @Override
    public <T extends Message> T as(final Class<T> messageClass) {
        final ObjectMapper objectMapper = SphereJsonUtils.newObjectMapper();
        final ObjectNode jsonNode = objectMapper.createObjectNode()
                .put("id", getId())
                .put("version", getVersion())
                .put("createdAt", getCreatedAt().toString())
                .put("lastModifiedAt", getLastModifiedAt().toString())
                .put("sequenceNumber", sequenceNumber)
                .put("resourceVersion", resourceVersion)
                .put("type", type);
        furtherFields.entrySet().forEach(entry -> jsonNode.replace(entry.getKey(), entry.getValue()));
        jsonNode.replace("resource", resource);
        return SphereJsonUtils.readObject(jsonNode, messageClass);
    }

    @JsonAnySetter
    private void set(final String key, final JsonNode value) {
        furtherFields.put(key, value);
    }
}
