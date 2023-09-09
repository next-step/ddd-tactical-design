package kitchenpos.products.application;

import static java.util.List.of;
import static java.util.stream.Collectors.toList;
import static kitchenpos.products.ProductFixture.createRequestBuilder;
import static kitchenpos.products.ProductFixture.updateRequestBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductServiceTest {

    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private PurgomalumClient purgomalumClient;
    private ProductService productService;

    static Stream<List<Product>> test6MethodSource() {
        return Stream.of(
            of(createRequestBuilder().name("P1").price(BigDecimal.ONE).build(),
                createRequestBuilder().name("P2").price(BigDecimal.TEN).build()),
            of(createRequestBuilder().name("P1").price(BigDecimal.ONE).build()),
            of()
        );
    }

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        purgomalumClient = new FakePurgomalumClient();
        productService = new ProductService(productRepository, menuRepository, purgomalumClient);
    }

    @DisplayName("등록할 상품의 가격이 0보다 작으면 에러를 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    void test1(int price) {
        //given
        Product request = createRequestBuilder()
            .name("name")
            .price(BigDecimal.valueOf(price))
            .build();

        //when && then
        assertThatThrownBy(
            () -> productService.create(request)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("등록할 상품의 이름은 반드시 있어야 한다")
    @ParameterizedTest
    @NullSource
    void test2(String name) {
        //given
        Product request = createRequestBuilder()
            .name(name)
            .price(BigDecimal.valueOf(1L))
            .build();

        //when && //then
        assertThatThrownBy(
            () -> productService.create(request)
        ).isInstanceOf(IllegalArgumentException.class);

    }

    @DisplayName("등록할 상품의 이름은 비속어를 넣을 수 없다")
    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설"})
    void test3(String profanity) {
        //given
        Product request = createRequestBuilder()
            .name(profanity)
            .price(BigDecimal.valueOf(1L))
            .build();

        //when && //then
        assertThatThrownBy(
            () -> productService.create(request)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 정보를 저장할수 있다")
    @ParameterizedTest
    @ValueSource(longs = {0L, 1L, 10L, 100L, 10_000L, 100_000L, 1_000_000L, 10_000_000L, 100_000_000L})
    void test4(long price) {
        //given
        Product request = createRequestBuilder()
            .name("name")
            .price(BigDecimal.valueOf(price))
            .build();

        //when
        Product result = productService.create(request);

        //then
        assertAll(
            () -> assertThat(result.getId()).isNotNull(),
            () -> assertThat(result.getName()).isEqualTo(request.getName()),
            () -> assertThat(result.getPrice()).isEqualTo(request.getPrice())
        );
    }

    @DisplayName("상품의 가격을 수정할수 있다")
    @ParameterizedTest
    @ValueSource(longs = {0L, 1L, 10L, 100L, 10_000L, 100_000L, 1_000_000L, 10_000_000L, 100_000_000L})
    void test5(long price) {
        //given
        Product savedProduct = productService.create(createRequestBuilder()
            .name("name")
            .price(BigDecimal.valueOf(5L))
            .build());

        Product request = updateRequestBuilder()
            .name("name")
            .price(BigDecimal.valueOf(price))
            .build();

        //when
        Product changedProduct = productService.changePrice(savedProduct.getId(), request);

        //then
        assertAll(
            () -> assertThat(changedProduct.getPrice()).isEqualByComparingTo(request.getPrice())
        );
    }

    @DisplayName("모든 상품의 정보를 조회할수 있다")
    @ParameterizedTest
    @MethodSource("test6MethodSource")
    void test6(List<Product> createRequests) {
        //given
        for (Product product : createRequests) {
            productService.create(product);
        }
        List<String> expectNames = createRequests.stream()
            .map(Product::getName)
            .collect(toList());

        //when
        List<Product> resultProducts = productService.findAll();

        //then
        assertAll(
            () -> assertThat(resultProducts).extracting("name")
                .containsExactlyElementsOf(expectNames)
        );

    }
}
