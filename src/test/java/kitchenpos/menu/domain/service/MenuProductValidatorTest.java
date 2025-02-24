package kitchenpos.menu.domain.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import kitchenpos.common.infra.external.FakePurgomalumClient;
import kitchenpos.menu.domain.model.MenuProduct;
import kitchenpos.menu.domain.model.MenuProductQuantity;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.model.ProductNameCreationService;
import kitchenpos.product.domain.model.ProductPrice;
import kitchenpos.product.domain.repository.ProductRepository;
import kitchenpos.product.infra.persistence.FakeProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductValidatorTest {

    private MenuProductValidator menuProductValidator;
    private ProductRepository productRepository;
    private ProductNameCreationService productNameCreationService;

    @BeforeEach
    void setUp() {
        productRepository = new FakeProductRepository(new HashMap<>());
        menuProductValidator = new MenuProductValidator(productRepository);
        productNameCreationService = new ProductNameCreationService(new FakePurgomalumClient());
    }

    @Test
    @DisplayName("메뉴 상품과 이에 포함된 상품이 존재하는지 확인하고, 없으면 예외를 던진다.")
    void validate_menu_product_exception() {
        // given
        Product lettuce = new Product(productNameCreationService.createName("lettuce"),
                new ProductPrice(BigDecimal.ONE));
        Product pepper = new Product(productNameCreationService.createName("pepper"), new ProductPrice(BigDecimal.ONE));

        productRepository.save(lettuce);

        MenuProduct lettuces = new MenuProduct(lettuce, new MenuProductQuantity(1), lettuce.getId());
        MenuProduct peppers = new MenuProduct(pepper, new MenuProductQuantity(3), pepper.getId());

        // when // then
        assertThatThrownBy(() -> menuProductValidator.validateMenuProduct(List.of(lettuces, peppers)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 상품에 들어갈 상품 수와 실제 상품 수가 다릅니다!");
    }
}
