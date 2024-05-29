package kitchenpos.menugroups.tobe.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.menugroups.tobe.domain.MenuGroup;
import kitchenpos.menugroups.tobe.domain.MenuGroupRepository;

@Service
public class DefaultMenuGroupService implements MenuGroupService {
	private final MenuGroupRepository menuGroupRepository;

	public DefaultMenuGroupService(final MenuGroupRepository menuGroupRepository) {
		this.menuGroupRepository = menuGroupRepository;
	}

	@Transactional
	public MenuGroup create(final String name) {
		return menuGroupRepository.save(new MenuGroup(name));
	}

	@Transactional(readOnly = true)
	public List<MenuGroup> findAll() {
		return menuGroupRepository.findAll();
	}
}
