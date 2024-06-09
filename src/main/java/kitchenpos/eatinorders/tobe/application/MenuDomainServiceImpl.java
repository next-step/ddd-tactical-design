package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.EatInOrderLineItem;
import kitchenpos.menus.tobe.domain.Displayed;
import kitchenpos.menus.tobe.domain.TobeMenu;
import kitchenpos.menus.tobe.domain.TobeMenuRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MenuDomainServiceImpl implements MenuDomainService {
    private final TobeMenuRepository tobeMenuRepository;

    public MenuDomainServiceImpl(TobeMenuRepository tobeMenuRepository) {
        this.tobeMenuRepository = tobeMenuRepository;
    }


    @Override
    public EatInOrderLineItem fetchOrderLineItem(UUID menuId, long quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("주문 수량은 0보다 커야 합니다.");
        }
        TobeMenu menu = tobeMenuRepository.findById(menuId).orElseThrow(() -> new IllegalArgumentException("메뉴가 존재하지 않습니다."));

        if (menu.displayed() == Displayed.HIDDEN) {
            throw new IllegalStateException("주문할 수 없는 메뉴입니다.");
        }

        return EatInOrderLineItem.create(menu.id(), menu.menuPrice(), quantity);
    }
}
