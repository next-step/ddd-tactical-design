package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import kitchenpos.core.constant.ExceptionMessages;
import kitchenpos.core.domain.Name;
import kitchenpos.core.domain.Price;

@Entity
@Table(name = "dt_menu")
public class Menu {

	@Id
	@Column(name = "id", columnDefinition = "binary(16)", nullable = false)
	private UUID id;

	@Column(name = "displayed", nullable = false)
	private boolean displayed;

	@Embedded
	private Name name;

	@Embedded
	private Price price;

	@Embedded
	private MenuProducts menuProducts;

	@ManyToOne(optional = false)
	@JoinColumn(
		name = "menu_group_id",
		columnDefinition = "binary(16)",
		foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
	)
	private MenuGroup menuGroup;

	protected Menu() {
	}

	public Menu(
		boolean displayed,
		Name name,
		Price price,
		MenuGroup menuGroup,
		MenuProducts menuProducts
	) {
		validatePrice(price, menuProducts);

		this.id = UUID.randomUUID();
		this.displayed = displayed;
		this.name = name;
		this.price = price;
		this.menuGroup = menuGroup;
		this.menuProducts = menuProducts;
	}

	private void validatePrice(Price price, MenuProducts menuProducts) {
		if (price.isBiggerThan(menuProducts.getTotalPrice())) {
			throw new IllegalArgumentException(ExceptionMessages.Menu.INVALID_PRICE);
		}
	}

	public void changePrice(Price price) {
		validatePrice(price, menuProducts);
		this.price = price;
	}

	public void display() {
		validatePrice(price, menuProducts);
		this.displayed = true;
	}

	public void hide() {
		this.displayed = false;
	}

	public UUID getId() {
		return id;
	}

	public boolean isDisplayed() {
		return displayed;
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Menu menu = (Menu)o;
		return Objects.equals(getId(), menu.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
