package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.vo.EatInOrderLineItemSeq;
import kitchenpos.eatinorders.tobe.domain.vo.Quantity;

public class EatInOrderLineItem {

    private final EatInOrderLineItemSeq seq;
    private final EatInOrderLineItemMenu eatInOrderLineItemMenu;
    private final Quantity quantity;

    public EatInOrderLineItem(final long seq, final EatInOrderLineItemMenu eatInOrderLineItemMenu, final int quantity) {
        this(new EatInOrderLineItemSeq(seq), eatInOrderLineItemMenu, new Quantity(quantity));
    }

    public EatInOrderLineItem(
            final EatInOrderLineItemSeq seq,
            final EatInOrderLineItemMenu eatInOrderLineItemMenu,
            final Quantity quantity
    ) {
        this.seq = seq;
        this.eatInOrderLineItemMenu = eatInOrderLineItemMenu;
        this.quantity = quantity;
    }
}
