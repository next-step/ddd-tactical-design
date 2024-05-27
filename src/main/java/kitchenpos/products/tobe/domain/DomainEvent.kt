package kitchenpos.products.tobe.domain

import java.time.LocalDateTime

interface DomainEvent {
    val occurredOn: LocalDateTime
}
