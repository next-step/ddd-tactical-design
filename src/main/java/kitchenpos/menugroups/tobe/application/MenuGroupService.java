package kitchenpos.menugroups.tobe.application;

import java.util.List;

import kitchenpos.menugroups.tobe.domain.MenuGroup;


public interface MenuGroupService {
	MenuGroup create(final String name);

	List<MenuGroup> findAll();
}
