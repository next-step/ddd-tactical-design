package kitchenpos.menus.tobe.application.menugroup;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.menugroup.Name;
import kitchenpos.menus.tobe.ui.menugroup.MenuGroupCreateRequest;

@Service(value = "TobeMenuGroupService")
public class MenuGroupService {
	private final MenuGroupRepository menuGroupRepository;

	public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
		this.menuGroupRepository = menuGroupRepository;
	}

	@Transactional
	public MenuGroup create(final MenuGroupCreateRequest request) {
		Name name = new Name(request.getName());
		MenuGroup menuGroup = new MenuGroup(name);
		return menuGroupRepository.save(menuGroup);
	}

	@Transactional(readOnly = true)
	public List<MenuGroup> findAll() {
		return menuGroupRepository.findAll();
	}
}
