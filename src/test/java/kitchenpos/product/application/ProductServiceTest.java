package kitchenpos.product.application;


import kitchenpos.infra.FakePurgomalumClient;
import kitchenpos.infra.PurgomalumClient;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.product.tobe.domain.*;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPriceService;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static kitchenpos.MoneyConstants.*;
import static kitchenpos.fixture.tobe.ProductFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();
    private ProductService productService;

    private MenuGroup menuGroup;
    private MenuProducts menuProducts;
    private MenuNameFactory menuName;
    private MenuProduct menuProduct;
    private Menu menu;

    final private static String FAIL_PREFIX = "[실패] ";

    @BeforeEach
    void setUp() {
        ProductPriceService productDomainService = new FakeProductDomainService(productRepository, menuRepository);
        productService = new ProductService(productRepository, purgomalumClient, productDomainService);

        menuName = new MenuNameFactory(purgomalumClient);
        menuGroup = new MenuGroup("메뉴그룹명");

    }

    @Nested
    @DisplayName("상품 등록 테스트")
    class SaveProduct {

        @ParameterizedTest
        @ValueSource(longs = {빵원, 만원})
        @DisplayName("상품을 정상적으로 등록할 수 있다.")
        void success(final long price) {
            var product = createProduct(상품명, price);
            var response = createProduct(상품명, price);

            given(productRepository.save(any())).willReturn(response);

            Product actual = productService.create(product);

            assertAll(
                    "상품 정보 그룹 Assertions",
                    () -> assertNotNull(actual.getId()),
                    () -> assertEquals(response.getProductName(), actual.getProductName()),
                    () -> assertEquals(response.getProductPrice(), actual.getProductPrice())
            );
        }
    }

    @Nested
    @DisplayName("상품 가격 변경 테스트")
    class ChangePrice {

        @ParameterizedTest
        @ValueSource(longs = {오천원, 빵원})
        @DisplayName("상품 가격은 변경할 수 있다.")
        void success(final long changingPrice) {
            final var product = createProduct(만원);
            menuProduct = MenuProduct.of(product.getId(), product.getProductPrice().longValue(), 1);
            menuProducts = new MenuProducts(List.of(menuProduct));
            menu = Menu.of(menuName.create("메뉴이름"), new MenuPrice(만원), menuGroup.getId(), true, menuProducts);

            given(productRepository.findById(product.getId())).willReturn(Optional.ofNullable(product));
            given(menuRepository.findAllByProductId(product.getId())).willReturn(List.of(menu));

            product.updateProductPrice(BigDecimal.valueOf(changingPrice));

            Product response = productService.changePrice(product.getId(), product);

            assertThat(response.getId()).isEqualTo(product.getId());
            assertAll(
                    "변경된 상품 정보 그룹 Assertions",
                    () -> assertEquals(response.getId(), product.getId()),
                    () -> assertEquals(response.getProductPrice(), BigDecimal.valueOf(changingPrice))
            );
        }

        @Test
        @DisplayName(FAIL_PREFIX + "등록되어 있지 않은 상품 정보인 경우 변경할 수 없다.")
        void notFoundTest() {
            final var product = createProduct(만원);

            assertThrows(NoSuchElementException.class, () -> productService.changePrice(product.getId(), product));
        }
    }

}
