package kitchenpos.common.domainevent;

@FunctionalInterface
public interface DomainEventPublisher {
    void publishEvent(Object event);
}
