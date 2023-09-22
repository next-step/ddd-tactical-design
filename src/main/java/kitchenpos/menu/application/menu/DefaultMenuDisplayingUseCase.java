package kitchenpos.menu.application.menu;

import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import java.util.UUID;
import kitchenpos.menu.application.exception.NotExistMenuException;
import kitchenpos.menu.application.menu.port.in.MenuDisplayingUseCase;
import kitchenpos.menu.application.menu.port.out.MenuRepository;

public class DefaultMenuDisplayingUseCase implements MenuDisplayingUseCase {

    private final MenuRepository repository;

    public DefaultMenuDisplayingUseCase(final MenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public void display(final UUID id) {
        checkNotNull(id, "id");

        repository.findById(id)
            .orElseThrow(() -> new NotExistMenuException(id))
            .display();
    }

    @Override
    public void hide(final UUID id) {
        checkNotNull(id, "id");

        repository.findById(id)
            .orElseThrow(() -> new NotExistMenuException(id))
            .hide();
    }
}
