package kitchenpos.menus.domain.tobe.menugroup;

import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.menus.domain.tobe.MenuCreatedEvent;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

public class MenuEventHandler {
    private final MenuGroupRegisterService menuGroupRegisterService;

    public MenuEventHandler(MenuGroupRegisterService menuGroupRegisterService) {
        this.menuGroupRegisterService = menuGroupRegisterService;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void createMenuEvent(MenuCreatedEvent event) {
        final Menu menu = event.getMenu();
        menuGroupRegisterService.findMenuGroup(menu.getMenuGroupId());
    }
}
