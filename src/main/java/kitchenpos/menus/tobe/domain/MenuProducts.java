package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.util.CollectionUtils;

import kitchenpos.core.constant.ExceptionMessages;
import kitchenpos.core.constant.UbiquitousLanguages;

@Embeddable
public class MenuProducts {

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(
		name = "menu_id",
		nullable = false,
		columnDefinition = "binary(16)",
		foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
	)
	private List<MenuProduct> values = new ArrayList<>();

	protected MenuProducts() {
	}

	public MenuProducts(List<MenuProduct> values) {
		validate(values);
		this.values = values;
	}

	private void validate(List<MenuProduct> values) {
		if (CollectionUtils.isEmpty(values)) {
			throw new IllegalArgumentException(String.format(ExceptionMessages.EMPTY_INVENTORY_TEMPLATE, UbiquitousLanguages.MENU_PRODUCTS));
		}
	}

	public BigDecimal getTotalPrice() {
		return values.stream()
			.map(MenuProduct::getTotalPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
