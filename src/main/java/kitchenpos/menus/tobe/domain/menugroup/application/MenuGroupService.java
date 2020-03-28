package kitchenpos.menus.tobe.domain.menugroup.application;

import kitchenpos.menus.tobe.domain.menugroup.dto.MenuGroupDto;

import java.util.List;

public interface MenuGroupService {

    MenuGroupDto register (MenuGroupDto dto);

    List<MenuGroupDto> list();

    MenuGroupDto findMenuGroup (Long id);

    boolean isExist (Long id);

}
