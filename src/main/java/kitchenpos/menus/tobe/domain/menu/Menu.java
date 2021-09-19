package kitchenpos.menus.tobe.domain.menu;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;

@Table(name = "menu")
@Entity(name = "TobeMenu")
public class Menu {
	@Column(name = "id", columnDefinition = "varbinary(16)")
	@Id
	private UUID id;

	@Embedded
	private DisplayedName name;

	@Embedded
	private Price price;

	@Column(name = "displayed", nullable = false)
	private boolean displayed;

	@Embedded
	private MenuProducts menuProducts;

	@ManyToOne(optional = false)
	@JoinColumn(
		name = "menu_group_id",
		columnDefinition = "varbinary(16)",
		foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
	)
	private MenuGroup menuGroup;

	protected Menu() {

	}

	public Menu(DisplayedName name, Price price, boolean displayed, MenuProducts menuProducts, MenuGroup menuGroup) {
		if (price.compareTo(menuProducts.getTotalPrice()) > 0) {
			throw new IllegalArgumentException("메뉴의 가격은 메뉴상품들의 전체 가격보다 적거나 같아야 합니다.");
		}

		this.id = UUID.randomUUID();
		this.name = name;
		this.price = price;
		this.displayed = displayed;
		this.menuProducts = menuProducts;
		this.menuGroup = menuGroup;
	}

	public UUID getId() {
		return id;
	}

	public DisplayedName getName() {
		return name;
	}

	public Price getPrice() {
		return price;
	}

	public boolean isDisplayed() {
		return displayed;
	}

	public MenuProducts getMenuProducts() {
		return menuProducts;
	}

	public MenuGroup getMenuGroup() {
		return menuGroup;
	}
}
