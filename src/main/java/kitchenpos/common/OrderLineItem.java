package kitchenpos.eatinorders.tobe.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name = "TobeOrderLineItem")
@Table(name = "order_line_item")
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;
    @Column(name = "menu_id", columnDefinition = "binary(16)", nullable = false)
    private UUID menuId;
    @Column(name = "quantity", columnDefinition = "binary(16)", nullable = false)
    private long quantity;
}
