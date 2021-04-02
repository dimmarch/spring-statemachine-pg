package dev.marchuk.statemachine.domain;

public enum State {
    CREATED,
    DELETED,
    WAIT_FOR_PROVIDER_APPROVE,
    PROVIDER_APPROVED,
    PROVIDER_DECLINED,
    EDITED,
    ARCHIVED,
    PUBLISHED,
}
