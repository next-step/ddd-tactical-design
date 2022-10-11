package kitchenpos.products.application;

import static kitchenpos.products.ProductFixtures.INVALID_ID;
import static kitchenpos.products.ProductFixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.event.ProductPriceChangedEvent;
import kitchenpos.products.domain.InMemoryProductRepository;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.ui.request.ProductChangePriceRequest;
import kitchenpos.products.ui.request.ProductCreateRequest;
import kitchenpos.products.ui.response.ProductResponse;
import kitchenpos.profanity.infra.FakeProfanityCheckClient;
import kitchenpos.profanity.infra.ProfanityCheckClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;
    private ProductRepository productRepository;
    private ProfanityCheckClient profanityCheckClient;

    @Spy
    private ApplicationEventPublisher publisher;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        profanityCheckClient = new FakeProfanityCheckClient();
        productService = new ProductService(
            productRepository,
            profanityCheckClient,
            publisher
        );
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        ProductCreateRequest request = createProductRequest("후라이드", 16_000L);
        ProductResponse response = productService.create(request);
        assertThat(response).isNotNull();
        assertAll(
            () -> assertThat(response.getId()).isNotNull(),
            () -> assertThat(response.getName()).isEqualTo(request.getName()),
            () -> assertThat(response.getPrice()).isEqualTo(request.getPrice())
        );
    }

    private ProductCreateRequest createProductRequest(String name, long price) {
        return new ProductCreateRequest(name, BigDecimal.valueOf(price));
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        ProductChangePriceRequest request = createChangePriceRequest(15_000L);
        ProductResponse response = productService.changePrice(productId, request);

        assertThat(response.getPrice()).isEqualTo(request.getPrice());
        verify(publisher).publishEvent(new ProductPriceChangedEvent(productId, BigDecimal.valueOf(15_000)));
    }

    @DisplayName("상품이 존재하지 않으면 상품의 가격을 변경할 수 없다.")
    @Test
    void productNotFoundException() {
        ProductChangePriceRequest request = createChangePriceRequest(15_000L);
        assertThatThrownBy(() -> productService.changePrice(INVALID_ID, request))
            .isExactlyInstanceOf(NoSuchElementException.class)
            .hasMessage("상품을 찾을 수 없습니다.");
    }

    @DisplayName("새로운 상품의 가격이 0보다 작으면 변경할 수 없다.")
    @ValueSource(strings = {"-1000", "-1"})
    @ParameterizedTest
    void negativeChangePriceException(BigDecimal invalidPrice) {
        UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        ProductChangePriceRequest request = createChangePriceRequest(invalidPrice);
        assertThatThrownBy(() -> productService.changePrice(productId, request))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("상품 가격은 0보다 작을 수 없습니다.");
    }

    @DisplayName("새로운 상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @Test
    void nullChangePriceException() {
        UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        ProductChangePriceRequest request = createChangePriceRequest(null);
        assertThatThrownBy(() -> productService.changePrice(productId, request))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("올바르지 않은 상품 가격입니다.");
    }


    private ProductChangePriceRequest createChangePriceRequest(long price) {
        return createChangePriceRequest(BigDecimal.valueOf(price));
    }

    private ProductChangePriceRequest createChangePriceRequest(BigDecimal price) {
        return new ProductChangePriceRequest(price);
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(product("후라이드", 16_000L));
        productRepository.save(product("양념치킨", 16_000L));
        List<ProductResponse> actual = productService.findAll();
        assertThat(actual).hasSize(2);
    }
}
