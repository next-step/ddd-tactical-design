package kitchenpos.menus.tobe.domain.menu;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import kitchenpos.common.domain.Price;

@Embeddable
public class MenuProducts {
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(
		name = "menu_id",
		nullable = false,
		columnDefinition = "varbinary(16)",
		foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
	)
	private List<MenuProduct> values;

	protected MenuProducts() {

	}

	public MenuProducts(List<MenuProduct> values) {
		this.values = values;
	}

	public List<MenuProduct> getValues() {
		return values;
	}

	public Price getTotalPrice() {
		return values.stream()
			.map(menuProduct ->
				Price.multiply(
					menuProduct.getProduct().getPrice(),
					menuProduct.getQuantity().getValue()))
			.reduce(Price::add)
			.get();
	}
}
