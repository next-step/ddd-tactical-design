package kitchenpos.menus.tobe.domain.menu.dto;

import kitchenpos.menus.tobe.domain.menu.infra.MenuEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuResponseDto that = (MenuResponseDto) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(price, that.price) &&
            Objects.equals(name, that.name) &&
            Objects.equals(menuGroupId, that.menuGroupId) &&
            Objects.equals(menuProductDtos, that.menuProductDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, name, menuGroupId, menuProductDtos);
    }
}
