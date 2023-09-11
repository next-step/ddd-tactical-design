package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuProductQuantity {
    public long quantity;

    public MenuProductQuantity(long quantity){
        checkQuantity(quantity);
    }


    public void checkQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
        this.quantity = quantity;
    }

    public long getQuantity(){
        return this.quantity;
    }

}
