package kitchenpos.products.tobe.domain.application;

import kitchenpos.Fixtures;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.dto.ProductPriceChangeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChangePriceTest {
    ChangePrice changePrice;
    ProductRepository productRepository;
    MenuRepository menuRepository;

    @BeforeEach
    public void setup() {
        this.productRepository = new InMemoryProductRepository();
        this.menuRepository = new InMemoryMenuRepository();
        this.changePrice = new DefaultChangePrice(productRepository);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final ProductPriceChangeDto request = Fixtures.changePriceRequest(15_000L);
        final Product actual = changePrice.execute(productId, request);
        assertThat(actual.getPrice()).isEqualTo(request.getPrice());
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final ProductPriceChangeDto request = Fixtures.changePriceRequest(price);
        assertThatThrownBy(() -> changePrice.execute(productId, request))
            .isInstanceOf(IllegalArgumentException.class);
    }

}
