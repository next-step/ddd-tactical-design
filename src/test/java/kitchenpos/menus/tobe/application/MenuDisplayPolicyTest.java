package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.fixtures.FakeMenuGroupRepository;
import kitchenpos.menus.tobe.fixtures.FakeMenuRepository;
import kitchenpos.menus.tobe.fixtures.MenuStep;
import kitchenpos.products.tobe.Money;
import kitchenpos.products.tobe.application.ProductPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class MenuDisplayPolicyTest {


    private MenuDisplayPolicy menuDisplayPolicy;
    private MenuStep menuStep;

    @BeforeEach
    void setUp() {

        FakeMenuRepository menuRepository = new FakeMenuRepository();

        menuDisplayPolicy = new MenuDisplayPolicy() {
            @Override
            public void display(ProductPrice productId) {
                menuRepository.findAllByProductId(productId.productId())
                        .forEach(menu -> menu.changeProductPrice(productId.productId(), productId.money()));
            }
        };

        menuStep = new MenuStep(menuRepository, new FakeMenuGroupRepository());
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다")
    @Test
    public void displayTest() {


        // given
        UUID productId = UUID.randomUUID();
        Menu menu = menuStep.메뉴_생성(
                new MenuProduct(productId, 1L, Money.from(5_000)),
                Money.from(5_000)
        );
        ProductPrice productPrice = new ProductPrice(productId, Money.from(1_000));

        // when
        menuDisplayPolicy.display(productPrice);

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }

}