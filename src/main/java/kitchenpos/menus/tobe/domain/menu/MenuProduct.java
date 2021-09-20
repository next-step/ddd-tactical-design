package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import kitchenpos.products.tobe.domain.Product;

@Table(name = "menu_product")
@Entity(name = "TobeMenuProduct")
public class MenuProduct {
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long seq;

	@ManyToOne(optional = false)
	@JoinColumn(
		name = "product_id",
		columnDefinition = "varbinary(16)",
		foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
	)
	private Product product;

	@Embedded
	private Quantity quantity;

	protected MenuProduct() {

	}

	public MenuProduct(Product product, Quantity quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public Quantity getQuantity() {
		return quantity;
	}
}
