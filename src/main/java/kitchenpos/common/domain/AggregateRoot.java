package kitchenpos.common.domain;

import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface AggregateRoot {

    ThreadLocal<List<Object>> threadLocal = ThreadLocal.withInitial(ArrayList::new);

    default Object register(Object event) {
        if (event == null) {
            return event;
        }
        threadLocal.get().add(event);
        return event;
    }

    @AfterDomainEventPublication
    default void clear() {
        threadLocal.get().clear();
    }

    @DomainEvents
    default List<Object> domainEvents() {
        return new ArrayList<>(threadLocal.get());
    }

}
