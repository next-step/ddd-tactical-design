package kitchenpos.menus.tobe.domain.vo;

import static java.math.BigDecimal.valueOf;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuProductEmptyException;
import kitchenpos.menus.tobe.domain.exception.MenuProductCountMismatchException;
import kitchenpos.menus.tobe.infra.DefaultProfanities;
import kitchenpos.products.tobe.domain.Product;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

class MenuProductsTest {

    private Profanities profanities;

    @BeforeEach
    void setUp() {
        profanities = new DefaultProfanities();
    }

    @Test
    void 메뉴상품은_한개이상_존재해야_한다() {
        // given & when & then
        assertThatThrownBy(() -> new MenuProducts(Collections.emptyList()))
                .isInstanceOf(InvalidMenuProductEmptyException.class)
                .hasMessage("메뉴에 포함된 상품은 한개 이상 존재해야 합니다.");
    }

    @Test
    void 메뉴에_등록된_상품_개수와_실제_상품_개수가_일치해야_한다() {
        // given
        MenuGroup menuGroup = new MenuGroup("메인 메뉴");
        Menu menu = new Menu(menuGroup, "후라이드치킨", 20_000, true, profanities);
        Product product = new Product("후라이드 치킨", valueOf(20_000));

        UUID duplicateProductId = product.getId();

        // MenuProduct 개수는 2개이지만, 실제 Product는 1개
        MenuProduct menuProduct1 = new MenuProduct(menu, product, 20_000, 1, duplicateProductId);
        MenuProduct menuProduct2 = new MenuProduct(menu, product, 20_000, 1, duplicateProductId);

        // when & then
        assertThatThrownBy(() -> new MenuProducts(menuProduct1, menuProduct2))
                .isInstanceOf(MenuProductCountMismatchException.class)
                .hasMessage("메뉴에 등록된 상품 개수와 실제 상품 개수가 일치해야 합니다.");
    }
}
