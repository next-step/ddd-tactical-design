package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import kitchenpos.common.tobe.domain.Quantity;
import kitchenpos.products.tobe.domain.Product;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq")

	private Long seq;

	@ManyToOne(optional = false)
	@JoinColumn(
		name = "product_id",
		columnDefinition = "binary(16)",
		foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
	)
	private Product product;

	@Embedded
	private Quantity quantity;

	@Transient
	private UUID productId;

	public MenuProduct() {
	}

	public MenuProduct(Long seq, Product product, long quantity) {
		this.seq = seq;
		this.productId = product.getId();
		this.product = product;
		this.quantity = new Quantity(quantity);
	}

	public MenuProduct(Product product, long quantity) {
		this.productId = product.getId();
		this.product = product;
		this.quantity = new Quantity(quantity);
	}

	public Long getSeq() {
		return seq;
	}

	public Product getProduct() {
		return product;
	}

	public long getQuantity() {
		return quantity.getValue();
	}

	public UUID getProductId() {
		return productId;
	}

	public BigDecimal getPrice() {
		return product.getPrice().multiply(quantity.getBigDecimalValue());
	}

}
