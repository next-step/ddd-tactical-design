package kitchenpos.products.dto;

import java.math.BigDecimal;
import java.util.UUID;

import kitchenpos.products.tobe.domain.Product;

public class ProductResponse {
	private UUID id;
	private String name;
	private BigDecimal price;

	public ProductResponse() {
	}

	public ProductResponse(UUID id, String name, BigDecimal price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public static ProductResponse from(Product product) {
		return new ProductResponse(product.getId(), product.getName(), product.getPrice());
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}
}
