package kitchenpos.menus.infra;

import kitchenpos.common.vo.Price;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.MenuProductsValidator;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuProductsException;
import kitchenpos.products.infra.tobe.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductId;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductsValidatorServiceTest {

    private ProductRepository productRepository;
    private MenuProductsValidator menuProductsValidator;

    @BeforeEach
    void setUp() {
        this.productRepository = new InMemoryProductRepository();
        this.menuProductsValidator = new MenuProductsValidatorService(this.productRepository);
    }

    @DisplayName("메뉴상품들 중 존재하지 않는 상품이 있는 경우, 예외가 발생한다")
    @Test
    void validate() {
        Product chicken = productRepository.save(createProduct(ProductId.generate(), "후라이드치킨", 25_000));
        ProductId nonExistId = ProductId.generate();
        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(chicken.getId(), 1, 25_000),
                new MenuProduct(nonExistId, 1, 2_000)
        );

        assertThatThrownBy(() -> menuProductsValidator.validate(menuProducts))
                .isInstanceOf(InvalidMenuProductsException.class);
    }

    private Product createProduct(ProductId id, String name, int price) {
        return new Product(id, new ProductName(name, (productName) -> false), new Price(price));
    }
}
