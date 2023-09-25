package kitchenpos.eatinorders.domain.eatinorder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import kitchenpos.eatinorders.application.eatinorder.port.out.ValidMenuPort;
import kitchenpos.eatinorders.application.exception.InvalidAcceptStatusException;
import kitchenpos.eatinorders.application.exception.InvalidCompleteStatusException;
import kitchenpos.eatinorders.application.exception.InvalidServeStatusException;
import kitchenpos.eatinorders.application.exception.NotExistOrderTableException;
import kitchenpos.eatinorders.application.ordertable.port.out.OrderTableNewRepository;
import kitchenpos.eatinorders.domain.ordertable.OrderTableNew;
import org.apache.commons.lang3.tuple.Pair;

@Table(name = "eatin_order")
@Entity
public class EatinOrder {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItemNew> orderLineItems;

    @ManyToOne
    @JoinColumn(
        name = "order_table_new_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTableNew orderTable;

    protected EatinOrder() {
    }

    private EatinOrder(final UUID id, final Status status, final LocalDateTime orderDateTime,
        final List<OrderLineItemNew> orderLineItems, final OrderTableNew orderTable) {

        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTable = orderTable;
    }

    /**
     * @throws NotExistOrderTableException orderTableId에 해당하는 table이 없을 때
     * @throws IllegalStateException       orderLineItem에 있는 menu가 없는 menu일 때
     */
    public static EatinOrder create(final UUID orderTableId,
        final List<UUID> orderLindItemMenuIds,
        final List<Pair<UUID, Integer>> orderLineItemMenuAndQuantities,
        final OrderTableNewRepository orderTableRepository,
        final ValidMenuPort validMenuPort) {

        final OrderTableNew orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(() -> new NotExistOrderTableException(orderTableId));

        validMenuPort.checkValidMenu(orderLindItemMenuIds);

        final List<OrderLineItemNew> orderLineItems =
            orderLineItemMenuAndQuantities.stream()
                .map(item -> OrderLineItemNew.create(item.getLeft(), item.getRight()))
                .collect(Collectors.toUnmodifiableList());

        return new EatinOrder(UUID.randomUUID(),
            Status.WAITING, LocalDateTime.now(), orderLineItems, orderTable);
    }

    /**
     * @throws InvalidAcceptStatusException accept를 할 수 없는 상태일 때
     */
    public void acceptOrder() {
        if (status != Status.WAITING) {
            throw new InvalidAcceptStatusException(id, status);
        }

        status = Status.ACCEPTED;
    }

    /**
     * @throws InvalidServeStatusException serve를 할 수 없는 상태일 때
     */
    public void serveOrder() {
        if (status != Status.ACCEPTED) {
            throw new InvalidServeStatusException(id, status);
        }

        status = Status.SERVED;
    }

    /**
     * @throws InvalidCompleteStatusException complete를 할 수 없는 상태일 때
     */
    public void completeOrder(final OrderTableNewRepository orderTableRepository) {
        if (status != Status.SERVED) {
            throw new InvalidCompleteStatusException(id, status);
        }

        status = Status.COMPLETED;

        orderTable.clear();
        orderTableRepository.save(orderTable);
    }

    public UUID getId() {
        return id;
    }

    public List<OrderLineItemNew> getOrderLineItems() {
        return orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTable.getId();
    }
}
