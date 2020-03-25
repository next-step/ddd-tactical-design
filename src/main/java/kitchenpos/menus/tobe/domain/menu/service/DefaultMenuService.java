package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.vo.NewMenu;
import kitchenpos.menus.tobe.domain.menu.vo.NewMenuProduct;
import kitchenpos.menus.tobe.domain.menu.dto.MenuRegisterDto;
import kitchenpos.menus.tobe.domain.menu.dto.MenuResponseDto;
import kitchenpos.menus.tobe.domain.menu.vo.NewMenuProducts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultMenuService implements MenuService{

    private final MenuRegisterService menuRegisterService;

    public DefaultMenuService(MenuRegisterService menuRegisterService){
        this.menuRegisterService = menuRegisterService;
    }

    @Transactional(readOnly = false)
    @Override
    public MenuRegisterDto register(MenuRegisterDto dto) {
        //VO 변환
        NewMenu newMenu = new NewMenu(dto.getPrice(),
            dto.getName(),
            dto.getPrice(),
            dto.getMenuGroupId());

        //MenuProduct의 Product가 제대로 설정됐는지 확인.
//        NewMenuProducts newMenuProducts = new NewMenuProducts();
        List<NewMenuProduct> newMenuProducts = new ArrayList<>();
        dto.getMenuProducts().stream()
        .forEach(menuProduct -> {
            newMenuProducts.add(
                new NewMenuProduct(menuProduct.getProductId(), menuProduct.getQuantity()));
        });

        menuRegisterService.register(newMenu, newMenuProducts);
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<MenuResponseDto> list() {
        return null;
    }
}
