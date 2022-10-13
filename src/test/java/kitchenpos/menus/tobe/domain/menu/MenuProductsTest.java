package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("메뉴 상품 리스트")
class MenuProductsTest {

    private UUID menuIdFirst;
    private UUID productIdFirst;
    private UUID menuIdSecond;
    private UUID productIdSecond;

    private MenuProduct menuProductFirst;
    private MenuProduct menuProductSecond;

    @BeforeEach
    void setUp() {
        menuIdFirst = UUID.randomUUID();
        productIdFirst = UUID.randomUUID();
        menuIdSecond = UUID.randomUUID();
        productIdSecond = UUID.randomUUID();

        menuProductFirst = new MenuProduct(productIdFirst, new Quantity(2), new Price(20_000));
        menuProductSecond = new MenuProduct(productIdSecond, new Quantity(1), new Price(10_000));
    }

    @DisplayName("메뉴 상품 리스트 생성")
    @Test
    void createMenuProducts() {
        MenuProducts menuProducts = new MenuProducts(List.of(menuProductFirst, menuProductSecond));

        assertThat(menuProducts.getMenuProductsCount()).isEqualTo(2);
        assertThat(menuProducts.getMenuProductsTotalPrice()).isEqualTo(BigDecimal.valueOf(50_000));
    }

    @DisplayName("메뉴 상품 리스트 중 상품 가격 변경")
    @Test
    void menuProductsInChangeProductPrice(){
        MenuProducts menuProducts = new MenuProducts(List.of(menuProductFirst, menuProductSecond));
        menuProducts.changePrice(productIdFirst, new Price(25_000));

        assertThat(menuProducts.getMenuProductsTotalPrice()).isEqualTo(BigDecimal.valueOf(60_000));
    }
}
