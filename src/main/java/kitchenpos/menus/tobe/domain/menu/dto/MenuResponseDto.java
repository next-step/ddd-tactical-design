package kitchenpos.menus.tobe.domain.menu.dto;

import kitchenpos.menus.tobe.domain.menu.infra.MenuEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class MenuResponseDto {

    private Long id;
    private BigDecimal price;
    private String name;
    private Long menuGroupId;
    private List<MenuProductDto> menuProductDtos;

    public MenuResponseDto(MenuEntity menu){
        this.id = menu.getId();
        this.price = menu.getPrice();
        this.name = menu.getName();
        this.menuGroupId = menu.getMenuGroupId();
        this.menuProductDtos = menu.getMenuProducts().stream()
            .map(menuProductEntity -> new MenuProductDto(menuProductEntity))
            .collect(Collectors.toList());
    }

}
