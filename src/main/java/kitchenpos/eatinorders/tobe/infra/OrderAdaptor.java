package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.domain.TobeOrderTable;

public interface OrderAdaptor {
    boolean isOrderComplete(TobeOrderTable orderTable);
}
