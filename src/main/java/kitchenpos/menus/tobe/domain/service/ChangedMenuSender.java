package kitchenpos.menus.tobe.domain.service;

import kitchenpos.menus.tobe.domain.entity.ChangedMenuEvent;
import kitchenpos.menus.tobe.domain.entity.Menu;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ChangedMenuSender {
    private final ApplicationEventPublisher publisher;

    public ChangedMenuSender(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void sendAtChangedMenu(Menu menu) {
        publisher.publishEvent(new ChangedMenuEvent(
                menu.getId(), menu.getPrice(), menu.isDisplayed()
        ));
    }
}
