package kitchenpos.eatinorders.domain.order;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.List;


@Embeddable
public class EatInOrderLineItems {

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<EatInOrderLineItem> values;

    public EatInOrderLineItems(List<EatInOrderLineItem> values) {
        this.values = values;
    }

    protected EatInOrderLineItems() {

    }
}
