package kitchenpos.menu.application.service;

import kitchenpos.menu.application.port.in.UpdateMenuProductPriceUseCase;
import kitchenpos.menu.application.port.out.LoadMenuPort;
import kitchenpos.menu.application.port.out.SaveMenuPort;
import kitchenpos.menu.domain.model.Menu;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class UpdateMenuProductPriceService implements UpdateMenuProductPriceUseCase {
    private final LoadMenuPort loadMenuPort;
    private final SaveMenuPort saveMenuPort;

    public UpdateMenuProductPriceService(
            final LoadMenuPort loadMenuPort,
            final SaveMenuPort saveMenuPort
    ) {
        this.loadMenuPort = loadMenuPort;
        this.saveMenuPort = saveMenuPort;
    }

    @Transactional
    @Override
    public void execute(UUID productId, BigDecimal price) {
        List<Menu> menus = loadMenuPort.findByProductId(productId);
        for (Menu menu : menus) {
            menu.changeMenuProductPrice(productId, price);
        }
        saveMenuPort.saveAll(menus);
    }
}
