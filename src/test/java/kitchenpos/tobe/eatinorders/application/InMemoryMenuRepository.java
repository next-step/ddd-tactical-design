package kitchenpos.tobe.eatinorders.application;

import static kitchenpos.tobe.eatinorders.application.Fixtures.HIDE_MENU_ID;
import static kitchenpos.tobe.eatinorders.application.Fixtures.INVALID_ID;
import static kitchenpos.tobe.eatinorders.application.Fixtures.menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.eatinorders.tobe.domain.model.Menu;
import kitchenpos.eatinorders.tobe.domain.repository.MenuRepository;

public class InMemoryMenuRepository implements MenuRepository {

    @Override
    public Optional<Menu> findById(UUID id) {
        if (id.equals(HIDE_MENU_ID)) {
            return Optional.of(new Menu(id, menu().getDisplayedName(), menu().getPrice(), false));
        }

        return Optional.of(new Menu(id, menu().getDisplayedName(), menu().getPrice(), menu().isDisplayed()));
    }

    @Override
    public List<Menu> findAllByIdIn(List<UUID> ids) {
        return ids.stream()
                .filter(uuid -> !uuid.equals(INVALID_ID))
                .map(uuid -> {
                    if(uuid.equals(HIDE_MENU_ID)) {
                        return new Menu(uuid, menu().getDisplayedName(), menu().getPrice(), false);
                    }

                    return new Menu(uuid, menu().getDisplayedName(), menu().getPrice(), true);
                })
                .collect(Collectors.toList());
    }
}
