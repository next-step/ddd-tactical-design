package kitchenpos.menu.application.service;

import kitchenpos.menu.adapter.out.persistance.JpaMenuEntityEntityRepository;
import kitchenpos.menu.adapter.out.persistance.entity.MenuEntity;
import kitchenpos.menu.adapter.out.persistance.entity.MenuProductEntity;
import kitchenpos.menu.application.port.in.UpdateMenuDisplayStatusUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class UpdateMenuDisplayStatusService implements UpdateMenuDisplayStatusUseCase {
    private final JpaMenuEntityEntityRepository menuEntityRepository;

    public UpdateMenuDisplayStatusService(JpaMenuEntityEntityRepository menuEntityRepository) {
        this.menuEntityRepository = menuEntityRepository;
    }

    @Transactional
    @Override
    public void execute(UUID productId) {
        final List<MenuEntity> menus = menuEntityRepository.findAllByProductId(productId);
        for (final MenuEntity menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProductEntity menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                        menuProduct.getProductPrice()
                                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
    }
}
