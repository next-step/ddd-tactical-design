package kitchenpos.menus.tobe.domain;

import static kitchenpos.global.TobeFixtures.menu;
import static kitchenpos.global.TobeFixtures.menuProduct;
import static kitchenpos.global.TobeFixtures.productDTO;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menus.tobe.application.FakeProductServerClient;
import kitchenpos.menus.tobe.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductsServiceTest {

    private MenuProductsService service;
    private ProductDTO chickenOfFried;
    private ProductDTO chickenOfSpicy;

    @BeforeEach
    void setUp() {
        chickenOfFried = productDTO("후라이드 치킨", 15_000);
        chickenOfSpicy = productDTO("양념 치킨", 15_500);
        List<ProductDTO> productDTOs = List.of(
                chickenOfFried,
                chickenOfSpicy
        );

        ProductServerClient productServerClient = new FakeProductServerClient(productDTOs);
        service = new MenuProductsService(productServerClient);
    }

    @DisplayName("메뉴 상품에 필요한 상품 정보를 합친다.")
    @Test
    void syncProduct() {
        BigDecimal menuPrice = BigDecimal.TEN;
        Menu requestMenu = menu(
                menuPrice,
                menuProduct(chickenOfFried.getId(), 1)
                        .withProduct(chickenOfFried.toProduct()),
                menuProduct(chickenOfSpicy.getId(), 1)
                        .withProduct(chickenOfSpicy.toProduct())
        );

        service.syncProduct(requestMenu);

        MenuProducts menuProducts = requestMenu.getMenuProducts();
        assertThat(menuProducts.getValues())
                .map(MenuProduct::getProduct)
                .contains(chickenOfFried.toProduct(), chickenOfSpicy.toProduct());
    }
}
