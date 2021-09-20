package kitchenpos.menus.tobe.ui.menu;

import javax.validation.constraints.NotNull;

public class MenuPriceChangeRequest {
	@NotNull
	private Long price;

	public MenuPriceChangeRequest() {

	}

	public MenuPriceChangeRequest(Long price) {
		this.price = price;
	}

	public Long getPrice() {
		return price;
	}
}
