package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.util.CollectionUtils;

import kitchenpos.core.constant.ExceptionMessages;
import kitchenpos.core.constant.UbiquitousLanguages;

@Embeddable
class MenuProducts {

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

	MenuProducts(List<MenuProduct> values) {
		validate(values);
		this.values = values;
	}

	private void validate(List<MenuProduct> values) {
		if (CollectionUtils.isEmpty(values)) {
			throw new IllegalArgumentException(String.format(ExceptionMessages.EMPTY_INVENTORY_TEMPLATE, UbiquitousLanguages.MENU_PRODUCTS));
		}
	}

	BigDecimal getTotalPrice() {
		return values.stream()
			.map(MenuProduct::getTotalPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	MenuProduct getBySeq(Long seq) {
		return values.stream()
			.filter(menuProduct -> Objects.equals(menuProduct.getSeq(), seq))
			.findAny()
			.orElseThrow(() -> new IllegalArgumentException(String.format(ExceptionMessages.NOT_FOUND_TEMPLATE, UbiquitousLanguages.MENU_PRODUCT)));
	}
}
