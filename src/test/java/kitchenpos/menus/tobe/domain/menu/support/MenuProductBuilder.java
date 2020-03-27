package kitchenpos.menus.tobe.domain.menu.support;

import kitchenpos.common.PositiveNumber;
import kitchenpos.menus.tobe.domain.menu.infra.MenuProductEntity;
import kitchenpos.menus.tobe.domain.menu.vo.MenuProductVO;

public class MenuProductBuilder {
    private Long id;
    private PositiveNumber menuId;
    private PositiveNumber productId;
    private PositiveNumber quantity;

    public MenuProductBuilder id (Long id){
        this.id = id;
        return this;
    }

    public MenuProductBuilder menuId (Long menuId){
        this.menuId = new PositiveNumber(menuId);
        return this;
    }

    public MenuProductBuilder productId (Long productId){
        this.productId = new PositiveNumber(productId);
        return this;
    }

    public MenuProductBuilder quantity (Long quantity){
        this.quantity = new PositiveNumber(quantity);
        return this;
    }

    public MenuProductEntity build (){
        return new MenuProductEntity(
            new MenuProductVO(productId.valueOf(), quantity.valueOf())
        );
    }
}
