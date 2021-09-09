package kitchenpos.products.tobe.ui;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductCreateRequest {
	@NotBlank
	private String name;
	@NotNull
	private Long price;

	public ProductCreateRequest(String name, Long price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public Long getPrice() {
		return price;
	}
}
