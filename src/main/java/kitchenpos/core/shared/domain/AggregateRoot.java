package kitchenpos.core.shared.domain;

import jakarta.persistence.Transient;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot<T extends DomainEntity<T, TID>, TID> extends DomainEntity<T, TID> {
    @Transient
    private final transient List<Object> domainEvents = new ArrayList();

    public AggregateRoot() {
    }

    protected <T> void registerEvent(T event) {
        Assert.notNull(event, "Domain event must not be null");
        this.domainEvents.add(event);
    }

    @AfterDomainEventPublication
    protected void clearDomainEvents() {
        this.domainEvents.clear();
    }

    @DomainEvents
    protected Collection<Object> domainEvents() {
        return Collections.unmodifiableList(this.domainEvents);
    }
}
