package com.mov.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movo.constants.EventConstant;

import java.time.Instant;
import java.util.UUID;

public sealed interface DomainEvent permits TransactionCreatedEvent {

    @JsonProperty(EventConstant.EVENT_ID)
    UUID eventId();

    @JsonProperty(EventConstant.EVENT_TYPE)
    String eventType();

    @JsonProperty(EventConstant.AGGREGATE_TYPE)
    String aggregateType();

    @JsonProperty(EventConstant.USER_ID)
    UUID userId();

    @JsonProperty(EventConstant.OCCURRED_AT)
    Instant occurredAt();

    @JsonProperty(EventConstant.SCHEMA_VERSION)
    int schemaVersion();



}

