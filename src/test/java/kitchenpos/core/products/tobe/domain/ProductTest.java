package kitchenpos.core.products.tobe.domain;

import kitchenpos.config.UnitTest;
import kitchenpos.core.products.application.dto.CreateProductRequest;
import kitchenpos.core.shared.value.Money;
import kitchenpos.fixture.ProductFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
@DisplayName("[Product] Product 테스트")
class ProductTest {
    @Test
    @DisplayName("성공: 유효한 값으로 Product 생성")
    void testCreateProductSuccess() {
        // given
        CreateProductRequest request = ProductFixtures.createProductRequest("후라이드", 16_000L);

        // when
        Product product = Product.create(request.id(), request.name(), request.price());

        // then
        assertNotNull(product);
        assertEquals(request.id(), product.getId());
        assertEquals(request.name(), product.getName());
        assertEquals(request.price(), product.getPrice());
    }

    @Test
    @DisplayName("실패: null id로 Product 생성 시 예외 발생")
    void testCreateProductFailWhenIdIsNull() {
        // given
        CreateProductRequest request = ProductFixtures.createProductRequest("후라이드", 16_000L);

        // when & then
        Exception exception = assertThrows(NullPointerException.class, () ->
                Product.create(null, request.name(), request.price())
        );
        assertTrue(exception.getMessage().contains("id는 null이 될 수 없습니다."));
    }

    @Test
    @DisplayName("실패: null name으로 Product 생성 시 예외 발생")
    void testCreateProductFailWhenNameIsNull() {
        // given
        CreateProductRequest request = ProductFixtures.createProductRequest("후라이드", 16_000L);

        // when & then
        Exception exception = assertThrows(NullPointerException.class, () ->
                Product.create(request.id(), null, request.price())
        );
        assertTrue(exception.getMessage().contains("name은 null이 될 수 없습니다."));
    }

    @Test
    @DisplayName("실패: null price로 Product 생성 시 예외 발생")
    void testCreateProductFailWhenPriceIsNull() {
        // given
        CreateProductRequest request = ProductFixtures.createProductRequest("후라이드", 16_000L);

        // when & then
        Exception exception = assertThrows(NullPointerException.class, () ->
                Product.create(request.id(), request.name(), null)
        );
        assertTrue(exception.getMessage().contains("price은 null이 될 수 없습니다."));
    }

    @Test
    @DisplayName("성공: changePrice를 통해 Product 가격 변경")
    void testChangePrice() {
        // given
        CreateProductRequest request = ProductFixtures.createProductRequest("후라이드", 16_000L);
        Product product = Product.create(request.id(), request.name(), request.price());

        ProductPrice newPrice = ProductPrice.of(Money.wons(15_000));

        // when
        product.changePrice(newPrice);

        // then
        assertEquals(newPrice, product.getPrice());
    }
}