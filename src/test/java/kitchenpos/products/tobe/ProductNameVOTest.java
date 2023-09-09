package kitchenpos.products.tobe;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.ToBeMenuRepository;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.application.ToBeProductService;
import kitchenpos.products.tobe.domain.ToBeProduct;
import kitchenpos.products.tobe.domain.ToBeProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductNameVOTest {
    private ToBeProductRepository productRepository;
    private ToBeMenuRepository menuRepository;
    private PurgomalumClient purgomalumClient;
    private ToBeProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        purgomalumClient = new FakePurgomalumClient();
        productService = new ToBeProductService(productRepository, menuRepository, purgomalumClient);
    }



    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> createProductRequest(name, 16_000L))
            .isInstanceOf(IllegalArgumentException.class);
    }


    private ToBeProduct createProductRequest(final String name, final long price) {
        return createProductRequest(name, BigDecimal.valueOf(price));
    }

    private ToBeProduct createProductRequest(final String name, final BigDecimal price) {
        final ToBeProduct product = new ToBeProduct(name,price,purgomalumClient);
        return product;
    }

}
