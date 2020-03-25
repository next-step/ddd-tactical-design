package kitchenpos.menus.tobe.domain.menu.vo;

import kitchenpos.common.Price;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewMenuProducts {
    private List<NewMenuProduct> newMenuProducts;

    public NewMenuProducts (){
        this(Collections.emptyList());
    }

    public NewMenuProducts(List<NewMenuProduct> newMenuProducts) {
        this.newMenuProducts = new ArrayList<>(newMenuProducts);
    }

    public void add(NewMenuProduct newMenuProduct){
        newMenuProducts.add(new NewMenuProduct(newMenuProduct));
    }

//    public Price mul

}
