package kitchenpos.order.tobe.common

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.DiscriminatorType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*
import org.springframework.data.domain.AbstractAggregateRoot

@Table(name = "orders")
@Entity(name = "TobeOrder")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
abstract class Order(
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "order_date_time", nullable = false)
    val orderDateTime: LocalDateTime = LocalDateTime.now(),
) : AbstractAggregateRoot<Order>() {
    abstract val type: OrderType
}
