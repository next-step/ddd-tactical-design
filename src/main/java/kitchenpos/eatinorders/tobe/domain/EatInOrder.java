package kitchenpos.eatinorders.tobe.domain;

import jakarta.persistence.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@DiscriminatorValue("EAT_IN")
public class EatInOrder extends Order{

    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private UUID orderTableId;

    public EatInOrder(LocalDateTime orderDateTime, OrderLineItems orderLineItems, UUID orderTableId) {
        super(OrderType.EAT_IN, OrderStatus.WAITING, orderDateTime, orderLineItems);
        registerEvent(new OrderTableCheckEvent(this, orderTableId));
        this.orderTableId = orderTableId;
    }

    protected EatInOrder() {

    }

    public void accepted(){
        if(this.getStatus() != OrderStatus.WAITING){
            throw new IllegalArgumentException();
        }
        setStatus(OrderStatus.ACCEPTED);
    }

    public void served(){
        if(this.getStatus() != OrderStatus.ACCEPTED){
            throw new IllegalArgumentException();
        }
        setStatus(OrderStatus.SERVED);
    }

    public void complete(){
        if(this.getStatus() != OrderStatus.COMPLETED){
            throw new IllegalArgumentException();
        }
        setStatus(OrderStatus.COMPLETED);
        registerEvent(new EatInOrderCompletedEvent(this, this.orderTableId));

    }

}
