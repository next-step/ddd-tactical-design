package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class EatInOrders {
    @OneToMany(mappedBy = "orderTable")
    private List<EatInOrder> eatInOrders = new ArrayList<>();

    protected EatInOrders() {
    }

    public void add(EatInOrder eatInOrder) {
        eatInOrders.add(eatInOrder);
    }

    public List<EatInOrder> getEatInOrders() {
        return eatInOrders;
    }

    public boolean checkAllCompleted() {
        return eatInOrders.stream().allMatch(EatInOrder::hasCompleted);
    }
}
