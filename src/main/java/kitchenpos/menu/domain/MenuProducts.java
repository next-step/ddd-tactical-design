package kitchenpos.menu.domain;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.util.CollectionUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Embeddable
public class MenuProducts {
	public static final String EMPTY_MENU_PRODUCTS_ERROR = "메뉴 상품 목록은 비워 둘 수 없습니다.";
	public static final String INVALID_MENU_PRODUCTS_PRICE_ERROR = "메뉴의 가격은 메뉴 상품 가격의 합보다 크면 안됩니다.";

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(
		name = "menu_id",
		nullable = false,
		columnDefinition = "binary(16)",
		foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
	)
	private List<MenuProduct> value;

	protected MenuProducts() {
	}

	public MenuProducts(List<MenuProduct> value, MenuPrice menuPrice) {
		if (CollectionUtils.isEmpty(value)) {
			throw new IllegalArgumentException(EMPTY_MENU_PRODUCTS_ERROR);
		}

		this.value = value;

		validatePrice(menuPrice);
	}

	public List<MenuProduct> getValue() {
		return Collections.unmodifiableList(value);
	}

	public void validatePrice(MenuPrice menuPrice) {
		if (menuPrice.getValue().compareTo(getPrice()) > 0) {
			throw new IllegalArgumentException(INVALID_MENU_PRODUCTS_PRICE_ERROR);
		}
	}

	public BigDecimal getPrice() {
		if (CollectionUtils.isEmpty(value)) {
			throw new IllegalArgumentException(INVALID_MENU_PRODUCTS_PRICE_ERROR);
		}

		return value.stream()
			.map(MenuProduct::getPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}

