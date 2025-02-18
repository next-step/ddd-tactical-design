package kitchenpos.menu.domain.service;

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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();
        Product 배추 = new Product(productNameCreationService.createName("배추"), new ProductPrice(BigDecimal.ONE), id1);
        Product 고춧가루 = new Product(productNameCreationService.createName("고춧가루"), new ProductPrice(BigDecimal.ONE), id2);
        Product non = new Product(productNameCreationService.createName("non"), new ProductPrice(BigDecimal.ONE), id3);

        productRepository.save(배추);
        productRepository.save(고춧가루);
        productRepository.save(non);

        MenuProduct 배추들 = new MenuProduct(배추, new MenuProductQuantity(1), id1);
        MenuProduct 고춧가루들 = new MenuProduct(고춧가루, new MenuProductQuantity(3), id2);

        // when // then
        assertThatThrownBy(() -> menuProductValidator.validateMenuProduct(List.of(배추들, 고춧가루들)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 상품에 들어갈 상품 수와 실제 상품 수가 다릅니다!");
    }
}