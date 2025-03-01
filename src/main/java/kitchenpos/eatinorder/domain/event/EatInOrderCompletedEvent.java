package kitchenpos.eatinorder.domain.event;

import kitchenpos.shared.event.DomainEvent;

import java.util.UUID;

public record EatInOrderCompletedEvent(UUID orderTableId) implements DomainEvent {
}
