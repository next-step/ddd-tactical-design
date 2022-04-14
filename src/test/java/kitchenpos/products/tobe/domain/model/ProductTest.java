package kitchenpos.products.tobe.domain.model;

import kitchenpos.fake.global.infrastructure.external.FakeBannedWordCheckClient;
import kitchenpos.global.domain.vo.DisplayedName;
import kitchenpos.global.domain.vo.Price;
import kitchenpos.global.infrastructure.external.BannedWordCheckClient;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductTest {

    public final BannedWordCheckClient bannedWordCheckClient = new FakeBannedWordCheckClient();

    @Test
    @org.junit.jupiter.api.DisplayName("상품(Product) 은 반드시 식별자와 이름 그리고 가격을 가진다.")
    void create() {
        final DisplayedName name = new DisplayedName("미트파이", bannedWordCheckClient);
        final Price price = new Price(BigDecimal.valueOf(1000L));
        Product product = new Product(name, price);
        assertAll(
                () -> assertThat(product.getId()).isNotNull(),
                () -> assertThat(product.getName()).isEqualTo(new DisplayedName("미트파이", text -> false)),
                () -> assertThat(product.getPrice()).isEqualTo(new Price(BigDecimal.valueOf(1000L)))
        );
    }

    @Test
    @org.junit.jupiter.api.DisplayName("상품(Product)의 가격은 변경될 수 있다.")
    void changePrice() {

        //given
        Price 변경할_가격 = new Price(BigDecimal.valueOf(1_500L));
        final DisplayedName name = new DisplayedName("미트파이", bannedWordCheckClient);
        final Price price = new Price(BigDecimal.valueOf(1000L));
        Product 미트파이 = new Product(name, price);

        //when
        미트파이.changePrice(변경할_가격);

        //then
        assertThat(미트파이.getPrice()).isEqualTo(변경할_가격);
    }


}
