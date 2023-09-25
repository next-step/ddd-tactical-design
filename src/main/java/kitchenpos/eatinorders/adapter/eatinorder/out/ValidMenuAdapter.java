package kitchenpos.eatinorders.adapter.eatinorder.out;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.application.eatinorder.port.out.ValidMenuPort;
import kitchenpos.menu.application.menu.port.in.MenuFindUseCase;

public class ValidMenuAdapter implements ValidMenuPort {

    private final MenuFindUseCase menuFindUseCase;

    public ValidMenuAdapter(final MenuFindUseCase menuFindUseCase) {
        this.menuFindUseCase = menuFindUseCase;
    }

    @Override
    public void checkValidMenu(final List<UUID> menuIds) {
        if (menuIds.size() != menuFindUseCase.findAll()
            .stream()
            .filter(menu -> menuIds.contains(menu.getId()))
            .count()) {

            throw new IllegalStateException(String.format("menu is not exist. ids: %s", menuIds));
        }
    }
}
