package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.dto.MenuResponseDto;
import kitchenpos.menus.tobe.domain.menu.infra.MenuEntity;
import kitchenpos.menus.tobe.domain.menu.infra.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.vo.MenuProducts;
import kitchenpos.menus.tobe.domain.menu.vo.MenuVO;
import kitchenpos.menus.tobe.domain.menu.vo.WrongMenuPriceException;
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
        //메뉴그룹이 등록되어있는지 확인.
        menuGroupService.isExist(menuVO.getMenuGroupiId());

        //MenuProduct 정보를 완성.
        MenuProducts findMenuProducts = productService.findAllPrice(menuProducts);

        //메뉴가격이 상품가격 총합보다 크면, exception을 발생한다.
        if(menuVO.getPrice().compareTo(findMenuProducts.totalAcount()) > 0){
            throw new WrongMenuPriceException("메뉴가격을 잘못 설정했습니다.");
        }

        //가격이 적당하면 Menu를 저장한다.
        MenuEntity menuEntity = new MenuEntity(menuVO);
        menuEntity.addMenuProducts(findMenuProducts);

        return new MenuResponseDto( menuRepository.save(menuEntity) );
    }

}
