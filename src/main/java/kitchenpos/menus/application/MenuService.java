package kitchenpos.menus.application;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import kitchenpos.menus.domain.Menu;

public interface MenuService {
	@Transactional
	Menu create(Menu request);

	@Transactional
	Menu changePrice(UUID menuId, Menu request);

	@Transactional
	Menu display(UUID menuId);

	@Transactional
	Menu hide(UUID menuId);

	@Transactional(readOnly = true)
	List<Menu> findAll();
}
