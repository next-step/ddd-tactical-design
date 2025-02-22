package kitchenpos.menu.application.service;

import kitchenpos.menu.application.port.in.UpdateMenuDisplayStatusUseCase;
import kitchenpos.menu.application.port.out.LoadMenuPort;
import kitchenpos.menu.application.port.out.SaveMenuPort;
import kitchenpos.menu.domain.model.Menu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UpdateMenuDisplayStatusService implements UpdateMenuDisplayStatusUseCase {
    private final LoadMenuPort loadMenuPort;
    private final SaveMenuPort saveMenuPort;

    public UpdateMenuDisplayStatusService(
            final LoadMenuPort loadMenuPort,
            final SaveMenuPort saveMenuPort
    ) {
        this.loadMenuPort = loadMenuPort;
        this.saveMenuPort = saveMenuPort;
    }

    @Transactional
    @Override
    public void execute(UUID productId) {
        List<Menu> menus = loadMenuPort.findByProductId(productId);
        for (final Menu menu : menus) {
            menu.hideMenuWhenMenuProductTotalPriceLowerThanMenuPrice();
        }
        saveMenuPort.saveAll(menus);
    }
}
