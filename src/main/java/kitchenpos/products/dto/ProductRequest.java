package kitchenpos.products.dto;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.lang.Nullable;

public class ProductRequest {
	private UUID id;

	@Nullable
	private String name;

	private BigDecimal price;

	public ProductRequest() {
	}

	public ProductRequest(BigDecimal price) {
		this.price = price;
	}

	public ProductRequest(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public UUID getId() {
		return id;
	}
}
