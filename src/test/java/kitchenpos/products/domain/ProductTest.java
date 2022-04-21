package kitchenpos.products.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import kitchenpos.fakeobject.FakeProductPurgomalumClient;
import kitchenpos.products.exception.ProductNameException;
import kitchenpos.products.exception.ProductPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * ### 상품
 *
 * - 상품을 등록할 수 있다.
 * - 상품의 가격이 올바르지 않으면 등록할 수 없다.
 *   - 상품의 가격은 0원 이상이어야 한다.
 * - 상품의 이름이 올바르지 않으면 등록할 수 없다.
 *   - 상품의 이름에는 비속어가 포함될 수 없다.
 * - 상품의 가격을 변경할 수 있다.
 * - 상품의 가격이 올바르지 않으면 변경할 수 없다.
 *   - 상품의 가격은 0원 이상이어야 한다.
 *   ---------------------????------------------
 * - 상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.
 * - 상품의 목록을 조회할 수 있다.
 */
@DisplayName("상품 테스트")
class ProductTest {

    @DisplayName("상품 생성 테스트")
    @Nested
    class CreateProduct {

        @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
        @Test
        void product_price_illegal() {
            assertThatThrownBy(() -> new Product(new ProductPrice(BigDecimal.valueOf(-100)), new ProductName("my product", new FakeProductPurgomalumClient())))
                    .isInstanceOf(ProductPriceException.class);
        }

        @DisplayName("상품의 이름에 비속어가 포함되어있다면 등록할 수 없다.")
        @Test
        void product_name_contain_profanities() {
            assertThatThrownBy(() -> new Product(new ProductPrice(BigDecimal.TEN), new ProductName("비속어", new FakeProductPurgomalumClient())))
                    .isInstanceOf(ProductNameException.class);
        }
    }

    @DisplayName("상품의 가격과 이름이 올바르면 상품을 등록할 수 있다.")
    @Test
    void create_product() {
        assertDoesNotThrow(() -> new Product(new ProductPrice(BigDecimal.TEN), new ProductName("my Product", new FakeProductPurgomalumClient())));
    }
}
