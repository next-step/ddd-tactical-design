package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.Menu.DisplayStatus;
import kitchenpos.products.tobe.domain.event.ProductPriceChanged;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductServiceTest {

    private MenuProductService menuProductService;
    private MenuProductRepository menuProductRepository;
    UUID product1Uuid;
    UUID product2Uuid;
    UUID product3Uuid;
    Menu menu1;
    Menu menu2;

    @BeforeEach
    public void init() {
        menuProductRepository = new InMemoryMenuProductRepository();
        menuProductService = new MenuProductService(menuProductRepository);
        product1Uuid = UUID.randomUUID();
        product2Uuid = UUID.randomUUID();
        product3Uuid = UUID.randomUUID();
        MenuProduct p1Request = new MenuProduct(product1Uuid, BigDecimal.valueOf(10_000), 1);
        MenuProduct p2Request = new MenuProduct(product2Uuid, BigDecimal.valueOf(20_000), 1);
        MenuProduct p3Request = new MenuProduct(product3Uuid, BigDecimal.valueOf(30_000), 1);

        menu1 = MenuFixture.menu(List.of(p1Request, p2Request), BigDecimal.valueOf(20_000));
        menu2 = MenuFixture.menu(List.of(p2Request, p3Request), BigDecimal.valueOf(50_000));

        for (MenuProduct menuProduct : menu1.getMenuProducts()) {
            menuProductRepository.save(menuProduct);
        }
        for (MenuProduct menuProduct : menu2.getMenuProducts()) {
            menuProductRepository.save(menuProduct);
        }
    }

    /**
     * | 제품       | 가격     | |------------|----------| | Product1   | 10,000원 | | Product2   | 20,000원 | | Product3   |
     * 30,000원 |
     * <p>
     * | 메뉴   | 가격     | 가지고 있는 제품        | |--------|----------|---------------------------| | Menu1  | 20,000원 |
     * Product1, Product2       | | Menu2  | 50,000원 | Product2, Product3       |
     */

    @DisplayName("20000원짜리 상품을 10000원으로 할인할때 연관된 메뉴중, 메뉴 가격이 구성품가격보다 큰 메뉴는 숨김처리된다")
    @Test
    void test1() {
        //given
        ProductPriceChanged productPriceChanged = new ProductPriceChanged(product2Uuid, BigDecimal.valueOf(10_000));

        //when
        menuProductService.checkMenuPrice(productPriceChanged);

        //then
        assertAll(
            () -> assertThat(menu1.getStatus()).isEqualTo(DisplayStatus.DISPLAY),
            () -> assertThat(menu2.getStatus()).isEqualTo(DisplayStatus.HIDE)
        );
    }
}