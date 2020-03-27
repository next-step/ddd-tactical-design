package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.dto.MenuResponseDto;
import kitchenpos.menus.tobe.domain.menu.infra.MenuEntity;
import kitchenpos.menus.tobe.domain.menu.infra.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.vo.MenuProducts;
import kitchenpos.menus.tobe.domain.menu.vo.MenuVO;
import kitchenpos.menus.tobe.domain.menugroup.application.MenuGroupService;
import org.springframework.stereotype.Component;

@Component
public class MenuRegisterService {

    private final MenuRepository menuRepository;
    private final MenuGroupService menuGroupService;
    private final ProductService productService;

    public MenuRegisterService(
            final MenuRepository menuRepository,
            final MenuGroupService menuGroupService,
            final ProductService productService
            ){
        this.menuRepository = menuRepository;
        this.menuGroupService = menuGroupService;
        this.productService = productService;
    }

    public MenuResponseDto register(MenuVO menuVO, MenuProducts menuProducts){

        menuGroupService.isExist(menuVO.getMenuGroupId());

        MenuProducts findMenuProducts = productService.findAllPrice(menuProducts);

        findMenuProducts.compare(menuVO.getPrice());

        MenuEntity menuEntity = new MenuEntity(menuVO);
        menuEntity.addMenuProducts(findMenuProducts);

        return new MenuResponseDto( menuRepository.save(menuEntity) );
    }

}
