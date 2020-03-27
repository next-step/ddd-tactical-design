package kitchenpos.menus.tobe.domain.menu.support;

import kitchenpos.menus.tobe.domain.menu.infra.MenuEntity;
import kitchenpos.menus.tobe.domain.menu.vo.MenuProducts;
import kitchenpos.menus.tobe.domain.menu.vo.MenuVO;

import java.math.BigDecimal;

public class MenuBuilder {
    private Long id;
    private BigDecimal price;
    private String name;
    private Long menuGroupId;
    private MenuProducts menuProducts;

    public MenuBuilder id (Long id){
        this.id = id;
        return this;
    }

    public MenuBuilder price (BigDecimal price){
        this.price = price;
        return this;
    }

    public MenuBuilder name (String name){
        this.name = name;
        return this;
    }

    public MenuBuilder menuGroupid (Long menuGroupId){
        this.menuGroupId = menuGroupId;
        return this;
    }

    public MenuBuilder menuProducts (MenuProducts menuProducts){
        this.menuProducts = menuProducts;
        return this;
    }

    public MenuEntity build() {
        MenuEntity menuEntity = new MenuEntity(new MenuVO(id, price, name, menuGroupId));
        menuEntity.addMenuProducts(menuProducts);

        return menuEntity;
    }
}
