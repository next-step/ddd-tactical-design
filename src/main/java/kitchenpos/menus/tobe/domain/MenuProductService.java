package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.function.Consumer;

import kitchenpos.core.annotation.CommandDomainService;
import kitchenpos.core.constant.ExceptionMessages;
import kitchenpos.core.domain.Price;
import kitchenpos.core.domain.Quantity;

@CommandDomainService
public class MenuProductService {

	public void changePrice(Menu menu, MenuProduct menuProduct, Price price) {
		change(menu, menuProduct, originalMenuProduct -> originalMenuProduct.changePrice(price));
	}

	public void changeQuantity(Menu menu, MenuProduct menuProduct, Quantity quantity) {
		change(menu, menuProduct, originalMenuProduct -> originalMenuProduct.changeQuantity(quantity));
	}

	private void change(
		Menu menu,
		MenuProduct menuProduct,
		Consumer<MenuProduct> changeTask
	) {
		MenuProducts menuProducts = menu.getMenuProducts();
		MenuProduct originalMenuProduct = menuProducts.getBySeq(menuProduct.getSeq());
		changeTask.accept(originalMenuProduct);

		validatePrice(menu, menuProducts);
	}

	private void validatePrice(Menu menu, MenuProducts menuProducts) {
		BigDecimal menuPrice = menu.getPrice();
		BigDecimal totalPrice = menuProducts.getTotalPrice();

		if (menuPrice.compareTo(totalPrice) > 0) {
			throw new IllegalArgumentException(ExceptionMessages.Menu.INVALID_PRICE);
		}
	}
}
