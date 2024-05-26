package kitchenpos.menus.tobe.application;

import java.util.List;

import kitchenpos.menus.tobe.domain.MenuGroup;


public interface MenuGroupService {
	MenuGroup create(final String name);

	List<MenuGroup> findAll();
}
