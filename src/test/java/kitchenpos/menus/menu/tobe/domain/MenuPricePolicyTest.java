package kitchenpos.menus.menu.tobe.domain;

import kitchenpos.common.domain.FakeProfanity;
import kitchenpos.common.domain.Profanity;
import kitchenpos.common.domain.vo.DisplayedName;
import kitchenpos.common.domain.vo.Price;
import kitchenpos.menus.menu.tobe.domain.vo.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MenuPricePolicyTest {

    private MenuPricePolicy pricePolicy;
    private MenuRepository menuRepository;
    private Profanity profanity;

    private UUID productId;
    private Menu menu;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        pricePolicy = new MenuPricePolicy(menuRepository);

        profanity = new FakeProfanity();
        final DisplayedName displayedName = DisplayedName.valueOf("메뉴", profanity);
        productId = UUID.randomUUID();
        final MenuProduct menuProduct = MenuProduct.create(productId, Price.valueOf(10_000L), Quantity.valueOf(1L));
        menu = Menu.create(displayedName, Price.valueOf(10_000L), UUID.randomUUID(), true, MenuProducts.of(menuProduct));
        menuRepository.save(menu);
    }

    @DisplayName("메뉴 가격 정책")
    @Nested
    class ChangedProductPriceTest {

        @DisplayName("메뉴상품의 가격을 변경한다.")
        @Test
        void case_1() {
            pricePolicy.changedProductPrice(productId, 80_000L);

            final Menu result = menuRepository.findById(MenuPricePolicyTest.this.menu.id()).get();
            assertAll(
                    () -> assertThat(result.menuProducts().totalAmount()).isEqualTo(Price.valueOf(80_000L)),
                    () -> assertThat(result.isDisplayed()).isTrue()
            );
        }

        @DisplayName("메뉴 가격이 메뉴 상품의 금액 총합보다 크면 메뉴는 숨겨진다.")
        @Test
        void case_2() {
            pricePolicy.changedProductPrice(productId, 8_000L);

            final Menu result = menuRepository.findById(MenuPricePolicyTest.this.menu.id()).get();
            assertAll(
                    () -> assertThat(result.menuProducts().totalAmount()).isEqualTo(Price.valueOf(8_000L)),
                    () -> assertThat(result.isDisplayed()).isFalse()
            );
        }
    }

}
