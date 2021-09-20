package kitchenpos.menus.tobe.ui.menu;

import java.util.UUID;

public class MenuProductDto {
	private UUID productId;
	private long quantity;

	public MenuProductDto() {

	}

	public MenuProductDto(UUID productId, long quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}

	public UUID getProductId() {
		return productId;
	}

	public long getQuantity() {
		return quantity;
	}
}
