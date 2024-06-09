package kitchenpos.menus.tobe.domain.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.repository.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.dto.MenuCreateDto;
import kitchenpos.menus.tobe.dto.MenuGroupCreateDto;
import kitchenpos.menus.tobe.dto.MenuProductCreateDto;
import kitchenpos.products.tobe.domain.application.ChangePrice;
import kitchenpos.products.tobe.domain.application.ChangePriceTestFixture;
import kitchenpos.products.tobe.domain.application.CreateProduct;
import kitchenpos.products.tobe.domain.application.CreateProductTestFixture;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.dto.ProductCreateDto;
import kitchenpos.products.tobe.dto.ProductPriceChangeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HideMenuWithInvalidPriceByProductIdTest {
    private HideMenuWithInvalidPriceByProductId hideMenuWithInvalidPriceByProductId;
    private MenuRepository menuRepository = new InMemoryMenuRepository();
    private ProductRepository productRepository = new InMemoryProductRepository();
    private CreateMenu createMenu;
    private CreateMenuGroup createMenuGroup;
    private CreateProduct createProduct;
    private ChangePrice changePrice;
    private CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity;

    @BeforeEach
    void setUp() {
        this.createMenu = new CreateMenuTestFixture(menuRepository, productRepository,
            (text -> false));
        this.createMenuGroup = new CreateMenuGroupTestFixture(menuRepository);
        this.createProduct = new CreateProductTestFixture((text -> false), productRepository);
        this.changePrice = new ChangePriceTestFixture(productRepository, (event) -> {});
        this.calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity = new CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantityTestFixture(productRepository);
        this.hideMenuWithInvalidPriceByProductId = new HideMenuWithInvalidPriceByProductIdTestFixture(menuRepository, calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity);
    }

    @DisplayName("메뉴에 속한 제품의 가격 * 수량의 합이 메뉴 가격보다 크다면 메뉴는 숨겨진다")
    @Test
    void test_hide() {
        // given
        BigDecimal initialProductPrice = BigDecimal.valueOf(30);
        MenuGroup menuGroup = createMenuGroup.execute(new MenuGroupCreateDto("test_menu_group"));
        Product product = createProduct.execute(new ProductCreateDto("test_product", initialProductPrice));
        MenuCreateDto menuCreateDto = new MenuCreateDto(
            "test_menu", BigDecimal.valueOf(50), true, menuGroup.getId(), List.of(
            new MenuProductCreateDto(product.getId(), 2L)
        ));
        Menu menu = createMenu.execute(menuCreateDto);
        changePrice.execute(product.getId(), new ProductPriceChangeDto(BigDecimal.TWO));
        assertThat(menu.isDisplayed()).isEqualTo(true);

        // when
        hideMenuWithInvalidPriceByProductId.execute(product.getId());

        // then
        assertThat(menu.isDisplayed()).isEqualTo(false);
    }
}
