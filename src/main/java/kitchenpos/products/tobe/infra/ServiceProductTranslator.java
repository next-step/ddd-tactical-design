package kitchenpos.products.tobe.infra;

import kitchenpos.menus.application.MenuService;
import kitchenpos.products.tobe.domain.ProductTranslator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ServiceProductTranslator implements ProductTranslator {
    // FIXME: Repository 를 RestTemplate 으로 분리하기
    private final MenuService menuService;

    public ServiceProductTranslator(final MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void changeMenuStatus(final UUID productId) {
        menuService.changeStatus(productId);
    }
}
