package kitchenpos.menus.domain.tobe;

import org.springframework.context.ApplicationEvent;

public class MenuCreatedEvent extends ApplicationEvent {
    private Menu menu;

    public MenuCreatedEvent(Menu menu) {
        super(menu);
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }
}
