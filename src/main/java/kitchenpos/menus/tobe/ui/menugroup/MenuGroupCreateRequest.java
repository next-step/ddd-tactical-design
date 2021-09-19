package kitchenpos.menus.tobe.ui.menugroup;

import javax.validation.constraints.NotBlank;

public class MenuGroupCreateRequest {
	@NotBlank
	private String name;

	public MenuGroupCreateRequest() {

	}

	public MenuGroupCreateRequest(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
