package kitchenpos.menus.tobe.infra.ohs;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.annotations.OpenHostService;
import kitchenpos.eatinorders.tobe.domain.eatinorder.MenuValidationService;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.TobeMenuRepository;
import org.springframework.transaction.annotation.Transactional;


@OpenHostService(
    description = "주문 컨텍스트에 메뉴 유효성 검증 서비스 제공",
    downstreamContexts = {"EatInOrdersContext", "TakeoutOrdersContext", "DeliveryOrdersContext"}
)
public class MenuValidationServiceImpl implements MenuValidationService {

    private final TobeMenuRepository menuRepository;

    public MenuValidationServiceImpl(TobeMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional(readOnly = true)
    public Menu getById(UUID menuId) {
        return menuRepository.findById(menuId)
            .orElseThrow(() -> new NoSuchElementException("Menu not found with id: " + menuId));
    }

    @Transactional(readOnly = true)
    public void validateCount(List<UUID> menuIds, int count) {
        int menuCount = menuRepository.countAllByIdIn(menuIds);
        if (menuCount != count) {
            throw new IllegalArgumentException();
        }
    }

    @Transactional(readOnly = true)
    public void validateDisplayedAndPrice(UUID menuId, BigDecimal price) {
        Menu menu = getById(menuId);
        if (!menu.isDisplayed()) {
            throw new IllegalStateException();
        }
        if (menu.getPrice().compareTo(price) != 0) {
            throw new IllegalArgumentException();
        }
    }
}
