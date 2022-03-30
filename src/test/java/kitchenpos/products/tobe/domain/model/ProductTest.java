package kitchenpos.products.tobe.domain.model;

import kitchenpos.fake.global.infrastructure.external.FakeBannedWordCheckClient;
import kitchenpos.global.infrastructure.external.BannedWordCheckClient;
import kitchenpos.products.tobe.domain.model.vo.Price;
import kitchenpos.products.tobe.domain.model.vo.ProductName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    public final BannedWordCheckClient bannedWordCheckClient = new FakeBannedWordCheckClient();

    @Test
    @DisplayName("상품(Product) 은 반드시 식별자와 이름 그리고 가격을 가진다.")
    void create() {

        Product product = new Product("미트파이", BigDecimal.valueOf(1000L), bannedWordCheckClient);

        assertAll(
                () -> assertThat(product.getId()).isNotNull(),
                () -> assertThat(product.getName()).isEqualTo(new ProductName("미트파이", text -> false)),
                () -> assertThat(product.getPrice()).isEqualTo(new Price(BigDecimal.valueOf(1000L)))
        );
    }

    @Test
    @DisplayName("상품(Product)의 가격은 변경될 수 있다.")
    void changePrice() {

        //given
        long 변경할_가격 = 1500L;
        Product 미트파이 = new Product("미트파이", BigDecimal.valueOf(1000L), bannedWordCheckClient);

        //when
        미트파이.changePrice(BigDecimal.valueOf(변경할_가격));

        //then
        assertThat(미트파이.getPrice()).isEqualTo(new Price(BigDecimal.valueOf(변경할_가격)));
    }


}
