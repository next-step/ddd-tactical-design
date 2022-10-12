package kitchenpos.products.tobe.domain.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import kitchenpos.global.domain.client.ProfanityCheckClient;
import kitchenpos.products.helper.ProductFixtureFactory;
import kitchenpos.products.tobe.domain.model.vo.ProductName;
import kitchenpos.products.tobe.domain.model.vo.ProductPrice;
import org.junit.jupiter.api.Test;

class ProductTest {

    private final ProfanityCheckClient profanityCheckClient = text -> false;

    @Test
    void 상품을_등록_할_수_있다() {
        // given
        var name = new ProductName("후라이드 치킨", profanityCheckClient);
        var price = new ProductPrice(6000);

        // when
        var actual = new Product(name, price);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(name),
            () -> assertThat(actual.getPrice()).isEqualTo(price)
        );
    }

    @Test
    void 상품의_가격을_변경_할_수_있다() {
        // given
        Product product = ProductFixtureFactory.create("후라이드 치킨", 6000);
        var newPrice = new ProductPrice(5000);

        // when
        product.changePrice(newPrice);

        // then
        assertThat(product.getPrice()).isEqualTo(newPrice);
    }
}