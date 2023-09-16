package kitchenpos.eatinorders.tobe.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Embeddable
public class Orders {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderTable")
    private List<Order> orders = new ArrayList<>();

    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    protected Orders() {
    }

    public boolean allOrderComplete() {
        boolean isComplete = true;
        for (Order order : orders) {
            isComplete &= order.isComplete();
        }

        return isComplete;
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(this.orders);
    }
}
