package kitchenpos.common;

import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity(name = "TobeOrders")
@Table(name = "orders")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Order {

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    protected UUID id;

    @Column(name = "order_date_time", nullable = false)
    protected LocalDateTime orderDateTime;

    @OneToMany(cascade = ALL, orphanRemoval = true, fetch = EAGER)
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    protected List<OrderLineItem> orderLineItems = new ArrayList<>();
}
