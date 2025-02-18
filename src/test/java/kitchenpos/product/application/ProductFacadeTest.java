package kitchenpos.product.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.fixture.MenuFixture;
import kitchenpos.product.application.dto.ProductRequest;
import kitchenpos.product.application.dto.ProductResponse;
import kitchenpos.product.application.facade.ProductFacade;
import kitchenpos.product.domain.fixture.ProductFixture;
import kitchenpos.product.domain.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductFacadeTest {

    @InjectMocks
    private ProductFacade productFacade;
    @Mock
    private ProductService productService;

    private ProductRequest.Create chicken;
    private ProductRequest.UpdatePrice updateChicken;

    private Menu chickenMenu;

    @BeforeEach
    void setUp() {
        chicken = ProductFixture.init().create();
        updateChicken = ProductFixture.init().update();
        chickenMenu = MenuFixture.init().create();
    }

    @Nested
    @DisplayName("상품 등록")
    class 상품_등록 {

        @Test
        @DisplayName("성공")
        void 상품등록_성공() {

            var result = productService.create(chicken.toVo());

            assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.name(), chicken.name()),
                () -> assertEquals(result.price(), chicken.price()),
                () -> assertThatCode(() -> {
                    productFacade.create(chicken);
                }).doesNotThrowAnyException()
            );

        }

        @DisplayName("상품명을 반드시 가지며 비속어를 포함하면 안된다.")
        @ParameterizedTest
        @ValueSource(strings = {"나쁜", "XXX"})
        void 상품명_비속어_검사(final String name) {
            chicken = ProductFixture.test(name, null).create();

            assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> productService.create(chicken.toVo()));
        }

        @DisplayName("상품가격은 0원 이상이어야 한다.")
        @ParameterizedTest
        @ValueSource(ints = {-10000, 0, 10000})
        void 상품가격_허용범위_검사(final int price) {
            chicken = ProductFixture.test(null, BigDecimal.valueOf(price)).create();

            if (price < 0) {
                assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> productFacade.create(chicken));
            }
        }

    }

    @Nested
    @DisplayName("상품 수정")
    class 상품_수정 {

        @ParameterizedTest
        @DisplayName("성공")
        @ValueSource(ints = {0, 1000, 10000})
        void 상품수정_성공(final int price) {

            final UUID productId = productFacade.create(chicken).id();

            final BigDecimal changePrice = BigDecimal.valueOf(price);

            updateChicken = new ProductRequest.UpdatePrice(productId, changePrice);

            var result = productFacade.changePrice(updateChicken);

            assertAll(
                () -> assertEquals(BigDecimal.valueOf(price), result.price())
            );

        }

        @DisplayName("상품가격이 0원 이상이어야 한다.")
        @ParameterizedTest
        @ValueSource(ints = {-10000, 0, 10000})
        void 상품가격_허용범위_검사(final int price) {
            updateChicken = ProductFixture.test(null, BigDecimal.valueOf(price)).update();

            if (price < 0) {
                assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> productFacade.changePrice(updateChicken));
            }
        }

//        @DisplayName("메뉴의 가격이 메뉴 구성 상품들의 총 금액보다 크면 해당 메뉴는 숨겨진다.")
//        @ParameterizedTest
//        @CsvSource({"100000, 100"})
//        void 가격비교_숨김처리(final int price1, final int price2) {
//            chicken.setPrice(BigDecimal.valueOf(price1));
//            productRepository.save(chicken);
//
//            chickenMenu = MenuFixture.test(
//                null,
//                null,
//                null,
//                true,
//                List.of(new MenuProductFixture(
//                    new ProductFixture(
//                        chicken.getId(),
//                        null,
//                        BigDecimal.valueOf(price2)
//                    ).create(),
//                    100
//                ).create())
//            ).create();
//            menuRepository.save(chickenMenu);
//
//            productService.changePrice(chicken.getId(), chicken);
//
//            assertThat(chickenMenu.isDisplayed()).isFalse();
//        }

    }

    @Nested
    @DisplayName("상품 조회")
    class 상품_조회 {

        @Test
        @DisplayName("성공 : 특정 조건 없이 상품의 모든 목록을 조회할 수 있다.")
        void 상품목록_조회() {
            List<ProductResponse.GetProduct> result = productFacade.findAll();

            assertAll(
                () -> assertThat(result).isEmpty(),
                () -> assertEquals(result.size(), 0)
            );
        }
    }
}
