package kitchenpos.menus.domain.tobe;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MenuProductsTest {

    @Test
    void totalAmount(){
        List<MenuProduct> menuProductList = new ArrayList<>();

        menuProductList.add(MenuProduct.createMenuProduct(UUID.randomUUID(), BigDecimal.valueOf(30000), 3));
        menuProductList.add(MenuProduct.createMenuProduct(UUID.randomUUID(), BigDecimal.valueOf(20000), 2));
        menuProductList.add(MenuProduct.createMenuProduct(UUID.randomUUID(), BigDecimal.valueOf(10000), 1));

        MenuProducts menuProducts = new MenuProducts(menuProductList);
        assertThat(menuProducts.totalAmount()).isEqualTo(BigDecimal.valueOf(140000));
    }
}
