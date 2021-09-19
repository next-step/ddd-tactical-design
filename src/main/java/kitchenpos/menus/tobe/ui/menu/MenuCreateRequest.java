package kitchenpos.menus.tobe.ui.menu;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MenuCreateRequest {
	@NotBlank
	private String name;
	@NotNull
	private Long price;
	@NotNull
	private UUID menuGroupId;
	@NotNull
	private Boolean displayed;
	@NotEmpty
	private List<MenuProductDto> menuProducts;

	public MenuCreateRequest() {

	}

	public MenuCreateRequest(
		String name,
		Long price,
		UUID menuGroupId,
		Boolean displayed,
		List<MenuProductDto> menuProducts
	) {
		this.name = name;
		this.price = price;
		this.menuGroupId = menuGroupId;
		this.displayed = displayed;
		this.menuProducts = menuProducts;
	}

	public String getName() {
		return name;
	}

	public Long getPrice() {
		return price;
	}

	public UUID getMenuGroupId() {
		return menuGroupId;
	}

	public Boolean getDisplayed() {
		return displayed;
	}

	public List<MenuProductDto> getMenuProducts() {
		return menuProducts;
	}
}
