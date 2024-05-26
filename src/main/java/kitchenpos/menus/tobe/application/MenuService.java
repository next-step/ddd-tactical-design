package kitchenpos.menus.tobe.application;

import java.util.UUID;

public interface MenuService {
	void hideMenusBasedOnProductPrice(UUID productId);
}