package kitchenpos.eatinorders.domain.eatinorder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class OrderLineItems {

  @OneToMany(cascade = {CascadeType.PERSIST,
      CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "order")
  private List<OrderLineItem> orderLineItems = new ArrayList<>();
}
