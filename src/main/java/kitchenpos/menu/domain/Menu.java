package kitchenpos.menu.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import kitchenpos.common.domain.PurgomalumClient;

@Table(name = "menu")
@Entity
public class Menu {
	@Column(name = "id", columnDefinition = "binary(16)")
	@Id
	private UUID id;

	@Embedded
	private MenuName name;

	@Embedded
	private MenuPrice price;

	@ManyToOne(optional = false)
	@JoinColumn(
		name = "menu_group_id",
		columnDefinition = "binary(16)",
		foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
	)
	private MenuGroup menuGroup;

	@Column(name = "displayed", nullable = false)
	private boolean displayed;

	@Embedded
	private MenuProducts menuProducts;

	@Transient
	private UUID menuGroupId;

	public Menu() {
	}

	public Menu(
		String name,
		BigDecimal price,
		MenuGroup menuGroup,
		List<MenuProduct> menuProducts,
		boolean displayed,
		PurgomalumClient purgomalumClient) {
		this(UUID.randomUUID(), name, price, menuGroup, menuProducts, displayed, purgomalumClient);
	}

	public Menu(
		UUID id,
		String name,
		BigDecimal price,
		MenuGroup menuGroup,
		List<MenuProduct> menuProducts,
		boolean displayed,
		PurgomalumClient purgomalumClient
	) {
		this.id = id;
		this.name = new MenuName(name, purgomalumClient);
		this.price = MenuPrice.of(price);
		this.menuGroup = menuGroup;
		this.menuGroupId = menuGroup.getId();
		this.menuProducts = new MenuProducts(menuProducts, this.price);
		this.displayed = displayed;
	}

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public String getName() {
		return name.getValue();
	}

	public BigDecimal getPrice() {
		return price.getValue();
	}

	public MenuGroup getMenuGroup() {
		return menuGroup;
	}

	public boolean isDisplayed() {
		return displayed;
	}

	public List<MenuProduct> getMenuProducts() {
		return menuProducts.getValue();
	}

	public UUID getMenuGroupId() {
		return menuGroupId;
	}

	public void displayBasedOnProductsPrice() {
		if (price.getValue().compareTo(menuProducts.getPrice()) > 0) {
			display(false);
		}
	}

	public void display(boolean display) {
		if (display) {
			menuProducts.validatePrice(price);
		}

		this.displayed = display;
	}

	public void changePrice(BigDecimal newPrice) {
		this.menuProducts.validatePrice(MenuPrice.of(newPrice));
		this.price = MenuPrice.of(newPrice);
	}
}
