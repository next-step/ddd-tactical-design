package kitchenpos.eatinorders.application.orders.dto;

import kitchenpos.eatinorders.domain.orders.EatInOrderLineItems;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class EatInOrderLineItemResponse {
    private Long seq;
    private String menuId;
    private BigDecimal price;
    private long quantity;

    public EatInOrderLineItemResponse(Long seq, String menuId, BigDecimal price, long quantity) {
        this.seq = seq;
        this.menuId = menuId;
        this.price = price;
        this.quantity = quantity;
    }

    public static List<EatInOrderLineItemResponse> from(EatInOrderLineItems orderLineItems) {
        return orderLineItems.getOrderLineItems().stream()
                .map(it -> new EatInOrderLineItemResponse(
                        it.getSeq(),
                        it.getMenuId().toString(),
                        it.getPrice(),
                        it.getQuantity()
                ))
                .collect(Collectors.toList());
    }

    public Long getSeq() {
        return seq;
    }

    public String getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }
}
