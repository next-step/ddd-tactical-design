package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    private PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @Test
    @DisplayName("이름과 가격 그리고 UUID 생성 여부 확인하기")
    void create() {
        //given
        Product product = new Product("후라이드치킨", 18000, purgomalumClient);

        //when then
        assertThat(product.getId()).isNotNull();
        assertThat(product.getName()).isEqualTo(new ProductName("후라이드치킨"));
        assertThat(product.getPrice()).isEqualTo(new ProductPrice(18000));
    }

    @Test
    @DisplayName("욕설이 있으면 생성 불가")
    void canNotCreateHaveProfanity() {

        assertThatThrownBy(() -> new Product("욕설치킨", 18000, purgomalumClient))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Product("욕설비속어", 18000, purgomalumClient))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Product("비속어치킨욕설", 18000, purgomalumClient))
                .isExactlyInstanceOf(IllegalArgumentException.class);

    }

    @Test
    @DisplayName("ID, 이름, 가격을 이용한 생성자 생성시 값 일치 확인하기")
    void get() {
        //given
        UUID id = UUID.randomUUID();
        Product product = new Product(id, "후라이드치킨", 18000);

        //when then
        assertThat(product.getId()).isEqualTo(id);
        assertThat(product.getName()).isEqualTo(new ProductName("후라이드치킨"));
        assertThat(product.getPrice()).isEqualTo(new ProductPrice(18000));
    }

    @Nested
    public class ChangePrice {
        private Product product;

        @BeforeEach
        void setUp() {
            //given
            UUID id = UUID.randomUUID();
            product = new Product(id, "후라이드치킨",18000);
        }

        @Test
        @DisplayName("가격 변경 성공")
        void success() {
            //when
            product.changePrice(20000);

            //then
            assertThat(product.getPrice()).isEqualTo(new ProductPrice(20000));
        }

        @Test
        @DisplayName("가격을 null 또는 음수를 입력할 수 없다.")
        void canNotPriceNullOrMinus() {
            //when then
            assertThatThrownBy(() -> product.changePrice(-1000))
                    .isExactlyInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> product.changePrice(-1))
                    .isExactlyInstanceOf(IllegalArgumentException.class);

        }

    }


}
