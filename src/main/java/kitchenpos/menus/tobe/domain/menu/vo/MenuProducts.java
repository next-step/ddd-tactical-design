package kitchenpos.menus.tobe.domain.menu.vo;

import kitchenpos.common.Price;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuProducts {
    private List<MenuProductVO> menuProducts;

    public MenuProducts(){
        this(Collections.emptyList());
    }

    public MenuProducts(List<MenuProductVO> menuProductVOS) {
        this.menuProducts = new ArrayList<>(menuProductVOS);
    }

    public void add (MenuProductVO menuProductVO){
        this.menuProducts.add(menuProductVO);
    }

    public List<MenuProductVO> list(){
        return new ArrayList<>(this.menuProducts);
    }

    public BigDecimal totalAcount (){
        Price totalAcount = Price.zero();

        menuProducts.stream()
            .forEach(menuProduct ->{
                totalAcount.add(menuProduct.acount());
            });

        return totalAcount.valueOf();
    }

}
