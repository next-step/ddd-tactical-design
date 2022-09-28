package kitchenpos.products.application;

import org.junit.jupiter.api.BeforeEach;

public class ProductServiceTest {

//    private ProductRepository productRepository;
//    private MenuRepository menuRepository;
//    private PurgomalumClient purgomalumClient;
//    private ProductService productService;
//
//    @BeforeEach
//    void setUp() {
//        productRepository = new InMemoryProductRepository();
//        menuRepository = new InMemoryMenuRepository();
//        purgomalumClient = new FakePurgomalumClient();
//        productService = new ProductService(productRepository, menuRepository, purgomalumClient);
//    }
//
//    @DisplayName("상품의 가격을 변경할 수 있다.")
//    @Test
//    void changePrice() {
//        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
//        final Product expected = changePriceRequest(15_000L);
//        final Product actual = productService.changePrice(productId, expected);
//        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
//    }
//
//    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
//    @ValueSource(strings = "-1000")
//    @NullSource
//    @ParameterizedTest
//    void changePrice(final BigDecimal price) {
//        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
//        final Product expected = changePriceRequest(price);
//        assertThatThrownBy(() -> productService.changePrice(productId, expected))
//            .isInstanceOf(IllegalArgumentException.class);
//    }
//
//    @DisplayName("상품의 목록을 조회할 수 있다.")
//    @Test
//    void findAll() {
//        productRepository.save(product("후라이드", 16_000L));
//        productRepository.save(product("양념치킨", 16_000L));
//        final List<Product> actual = productService.findAll();
//        assertThat(actual).hasSize(2);
//    }

}
