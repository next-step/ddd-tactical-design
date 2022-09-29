package kitchenpos.menus.tobe.domain;

import static kitchenpos.menus.tobe.MenuFixtures.menu;
import static kitchenpos.menus.tobe.MenuFixtures.menuProduct;
import static kitchenpos.menus.tobe.MenuFixtures.productDTO;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
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
        chickenOfFried = productDTO(UUID.randomUUID(), "후라이드 치킨", 15_000);
        chickenOfSpicy = productDTO(UUID.randomUUID(), "양념 치킨", 15_500);
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
        Menu requestMenu = menu(
                1_000,
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
