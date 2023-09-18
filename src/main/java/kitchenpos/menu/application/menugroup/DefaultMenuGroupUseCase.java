package kitchenpos.menu.application.menugroup;

import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import kitchenpos.menu.application.menugroup.port.in.MenuGroupDTO;
import kitchenpos.menu.application.menugroup.port.in.MenuGroupUseCase;
import kitchenpos.menu.application.menugroup.port.out.MenuGroupRepository;
import kitchenpos.menu.domain.menugroup.MenuGroupNew;
import kitchenpos.support.vo.Name;

public class DefaultMenuGroupUseCase implements MenuGroupUseCase {
    
    private final MenuGroupRepository repository;

    public DefaultMenuGroupUseCase(final MenuGroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public MenuGroupDTO register(final Name name) {
        checkNotNull(name, "name");

        final MenuGroupNew menuGroup = repository.save(MenuGroupNew.create(name));

        return new MenuGroupDTO(menuGroup);
    }
}
