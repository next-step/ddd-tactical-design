package kitchenpos.products.tobe.ui;

import javax.validation.constraints.NotNull;

public class ProductPriceChangeRequest {
	@NotNull
	private Long price;

	public ProductPriceChangeRequest(Long price) {
		this.price = price;
	}

	public Long getPrice() {
		return price;
	}
}
