package kitchenpos.common.domain;

import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

public interface AggregateRoot {

    @Transient
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
