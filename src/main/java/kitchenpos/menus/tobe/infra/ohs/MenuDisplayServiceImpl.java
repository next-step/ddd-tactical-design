package kitchenpos.menus.tobe.infra.ohs;

import java.util.List;
import java.util.UUID;
import kitchenpos.annotations.OpenHostService;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.MenuDisplayService;
import org.springframework.transaction.annotation.Transactional;

@OpenHostService(
    description = "상품 가격 변경 시 메뉴 표시 상태를 업데이트하는 서비스",
    downstreamContexts = {"ProductsContext"}
)
public class MenuDisplayServiceImpl implements MenuDisplayService {

    private final MenuRepository menuRepository;

    public MenuDisplayServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    @Transactional
    public void updateMenusDisplayStatusByProductId(UUID productId) {
        List<Menu> menus = menuRepository.findAllByProductId(productId);
        menus.forEach(Menu::updateDisplayStatus);
    }
} 
