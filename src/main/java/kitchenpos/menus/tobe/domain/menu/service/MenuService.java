package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.dto.MenuResponseDto;
import kitchenpos.menus.tobe.domain.menu.dto.MenuRegisterDto;

import java.util.List;

public interface MenuService {
    MenuResponseDto register(MenuRegisterDto menuDto);
    List<MenuResponseDto> list();
}
