package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.common.Name;
import kitchenpos.menus.tobe.domain.menu.dto.MenuRegisterDto;
import kitchenpos.menus.tobe.domain.menu.dto.MenuResponseDto;
import kitchenpos.menus.tobe.domain.menu.infra.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.vo.MenuDuplicationException;
import kitchenpos.menus.tobe.domain.menu.vo.MenuProductVO;
import kitchenpos.menus.tobe.domain.menu.vo.MenuProducts;
import kitchenpos.menus.tobe.domain.menu.vo.MenuVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultMenuService implements MenuService{

    private final MenuRegisterService menuRegisterService;
    private final MenuRepository menuRepository;

    public DefaultMenuService(MenuRegisterService menuRegisterService, MenuRepository menuRepository){
        this.menuRegisterService = menuRegisterService;
        this.menuRepository = menuRepository;
    }

    @Transactional(readOnly = false)
    @Override
    public MenuResponseDto register(MenuRegisterDto dto) {
        validateRegisteredMenu(new Name(dto.getName()));

        MenuVO menuVO = new MenuVO(
            dto.getPrice(),
            dto.getName(),
            dto.getMenuGroupId());

        MenuProducts menuProducts = new MenuProducts();
        dto.getMenuProducts().stream()
        .forEach(menuProduct -> {
            menuProducts.add(
                new MenuProductVO(menuProduct.getProductId(), menuProduct.getQuantity()));
        });

        return menuRegisterService.register(menuVO, menuProducts);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MenuResponseDto> list() {
        return menuRepository.findAll().stream()
            .map(menuEntity -> new MenuResponseDto(menuEntity))
            .collect(Collectors.toList());
    }

    private void validateRegisteredMenu(final Name name){
        if(menuRepository.findByName(name.valueOf())){
            throw new MenuDuplicationException("동일한 메뉴가 이미 등록 되었습니다.");
        }
    }
}
