package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.infra.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.vo.NewMenu;
import kitchenpos.menus.tobe.domain.menu.vo.NewMenuProduct;
import kitchenpos.menus.tobe.domain.menugroup.application.MenuGroupService;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public void register(NewMenu newMenu, List<NewMenuProduct> newMenuProducts){
        //이미 등록된 메뉴 그룹인지 확인
        menuGroupService.isExist(newMenu.getMenuGroupiId());

        //Product의 가격들을 구한다.
        List<NewMenuProduct> findMenuProduct = productService.findAllProductPrice(newMenuProducts);

        //이제 가격을 비교한다.

        //가격이 적당하면 Menu를 저장한다.

        //반환한다.
    }

//    public void register(MenuVO menuVO, )

//    public MenuDto register (Menu menu, MenuGroup menuGroup, MenuProducts menuProducts){
//        //menuGroupId 가 설정되었는지 확인.
//        menuGroupService.isExist(menuGroup.getId());
//
//        //menuProduct 가 제대로 설정되었는지 확인.
//        productService.
//
//        //menuProduct의 가격이 제대로 설정되었는지 확인.
//        //
//
//        return null;
//    }

//    public void register (MenuRegisterDto menuRegisterDto){
//
//    }

}
