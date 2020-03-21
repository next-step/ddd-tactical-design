package kitchenpos.menus.tobe.menugroup.application;

import kitchenpos.menus.tobe.menugroup.dto.MenuGroupDto;

import java.util.List;
import java.util.Optional;

public interface MenuGroupService {

    MenuGroupDto register (MenuGroupDto dto);

    List<MenuGroupDto> list();

    Optional<MenuGroupDto> findMenuGroup (Long id);

}
