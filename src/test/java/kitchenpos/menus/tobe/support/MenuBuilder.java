package kitchenpos.menus.tobe.support;

import kitchenpos.common.Name;
import kitchenpos.common.PositiveNumber;
import kitchenpos.common.Price;
import kitchenpos.menus.tobe.domain.menu.infra.MenuEntity;
import kitchenpos.menus.tobe.domain.menu.infra.MenuProductEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MenuBuilder {
    private Long id;
    private Price price;
    private Name name;
    private PositiveNumber menuGroupId;
    private List<MenuProductEntity> menuProductEntityList;
    private Price amount;

    public MenuBuilder id (Long id){
        this.id = id;
        return this;
    }

    public MenuBuilder price (BigDecimal price){
        this.price = new Price(price);
        return this;
    }

    public MenuBuilder name (String name){
        this.name = new Name(name);
        return this;
    }

    public MenuBuilder menuGroupid (Long menuGroupId){
        this.menuGroupId = new PositiveNumber(menuGroupId);
        return this;
    }

    public MenuBuilder menuProductList (List<MenuProductEntity> menuProductEntityList){
        this.menuProductEntityList = new ArrayList<>(menuProductEntityList);
        return this;
    }

    public MenuBuilder amount (BigDecimal amount){
        this.amount = new Price(amount);
        return this;
    }

    public MenuEntity build(){
        return new MenuEntity(
            id,
            price.valueOf(),
            name.valueOf(),
            menuGroupId.valueOf(),
            menuProductEntityList,
            amount.valueOf());
    }
}
