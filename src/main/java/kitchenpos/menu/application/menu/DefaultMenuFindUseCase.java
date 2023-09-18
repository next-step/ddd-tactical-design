package kitchenpos.menu.application.menu;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.menu.application.menu.port.in.MenuDTO;
import kitchenpos.menu.application.menu.port.in.MenuFindUseCase;
import kitchenpos.menu.application.menu.port.out.MenuRepository;

public class DefaultMenuFindUseCase implements MenuFindUseCase {

    private final MenuRepository repository;

    public DefaultMenuFindUseCase(final MenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MenuDTO> findAll() {
        return repository.findAll()
            .stream()
            .map(MenuDTO::new)
            .collect(Collectors.toUnmodifiableList());
    }
}
