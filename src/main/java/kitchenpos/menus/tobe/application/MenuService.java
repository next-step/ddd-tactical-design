package kitchenpos.menus.tobe.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import kitchenpos.menus.tobe.application.dto.MenuCreationRequest;
import kitchenpos.menus.tobe.domain.Menu;

public interface MenuService {
	Menu create(final MenuCreationRequest request);

	Menu changePrice(final UUID menuId, final BigDecimal requestPrice);

	Menu display(final UUID menuId);

	Menu hide(final UUID menuId);

	List<Menu> findAll();

	void hideMenusBasedOnProductPrice(UUID productId);
}