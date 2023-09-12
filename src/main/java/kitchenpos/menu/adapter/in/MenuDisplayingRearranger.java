package kitchenpos.menu.adapter.in;

import java.util.UUID;
import kitchenpos.menu.application.port.in.MenuUseCase;

public class MenuDisplayingRearranger {

    private final MenuUseCase menuUseCase;

    public MenuDisplayingRearranger(final MenuUseCase menuUseCase) {
        this.menuUseCase = menuUseCase;
    }

    public void rearrange(final UUID productId) {
        menuUseCase.rearrangeDisplaying(productId);
    }
}
