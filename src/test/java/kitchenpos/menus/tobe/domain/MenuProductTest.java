package kitchenpos.menus.tobe.domain;

import static java.math.BigDecimal.valueOf;
import kitchenpos.menus.tobe.infra.DefaultProfanities;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuProductQuantityException;
import kitchenpos.menus.tobe.domain.vo.Profanities;
import kitchenpos.products.tobe.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

class MenuProductTest {

    private Profanities profanities;

    @BeforeEach
    void setUp() {
        profanities = new DefaultProfanities();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100, -99999})
    void 메뉴에_등록된_상품의_수량은_0개_이상이어야_한다(int invalidQuantity) {
        // given
        MenuGroup menuGroup = new MenuGroup("메인 메뉴");
        Menu menu = new Menu(menuGroup, "후라이드치킨", 20_000, true, profanities);
        Product product = new Product("후라이드치킨", valueOf(20_000));

        // when & then
        Assertions.assertThatThrownBy(() -> new MenuProduct(menu, product, 20_000, invalidQuantity, UUID.randomUUID()))
                .isInstanceOf(InvalidMenuProductQuantityException.class)
                .hasMessage("메뉴에 등록된 상품의 수량은 0개 이상이어야 합니다.");
    }
}
