package kitchenpos.eatinorders.tobe.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.util.CollectionUtils;

import kitchenpos.core.constant.ExceptionMessages;
import kitchenpos.core.constant.UbiquitousLanguages;
import kitchenpos.core.util.ArrayUtils;

@Embeddable
public class OrderLineItems implements Serializable {

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(
		name = "order_id",
		nullable = false,
		columnDefinition = "binary(16)",
		foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
	)
	private List<OrderLineItem> values = new ArrayList<>();

	protected OrderLineItems() {
	}

	public OrderLineItems(OrderLineItem orderLineItem, OrderLineItem... orderLineItems) {
		this(ArrayUtils.asList(orderLineItem, orderLineItems));
	}

	private OrderLineItems(List<OrderLineItem> values) {
		validate(values);
		this.values = Collections.unmodifiableList(values);
	}

	private void validate(List<OrderLineItem> values) {
		if (CollectionUtils.isEmpty(values)) {
			throw new IllegalArgumentException(String.format(ExceptionMessages.EMPTY_INVENTORY_TEMPLATE, UbiquitousLanguages.ORDER_LINE_ITEM));
		}
	}

	public List<OrderLineItem> getValues() {
		return values;
	}
}
