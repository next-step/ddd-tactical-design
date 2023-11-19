package kitchenpos.order.tobe.eatinorder.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import kitchenpos.common.Price;

@Embeddable
public class EatItOrderMenu {
    @Column(name = "eat_in_order_menu_id", columnDefinition = "binary(16)", nullable = false)
    private UUID eatInOrderMenuId;

    @Embedded
    private Price price;

    protected EatItOrderMenu() {
    }

    public EatItOrderMenu(UUID eatInOrderMenuId, Price price) {
        this.eatInOrderMenuId = eatInOrderMenuId;
        this.price = price;
    }

    public static EatItOrderMenu from(UUID eatInOrderMenuId, Price price) {
        return new EatItOrderMenu(eatInOrderMenuId, price);
    }

    public UUID getEatInOrderMenuId() {
        return eatInOrderMenuId;
    }

    public Price getPrice() {
        return price;
    }
}
