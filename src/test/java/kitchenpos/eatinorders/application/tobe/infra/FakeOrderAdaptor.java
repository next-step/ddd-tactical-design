package kitchenpos.eatinorders.application.tobe.infra;

import kitchenpos.eatinorders.tobe.domain.TobeOrderTable;
import kitchenpos.eatinorders.tobe.infra.OrderAdaptor;

public class FakeOrderAdaptor implements OrderAdaptor {

    @Override
    public boolean isOrderComplete(TobeOrderTable orderTable) {
        return orderTable.isEmpty();
    }
}
