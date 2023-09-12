package kitchenpos.products.tobe.application;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.TobeProduct;
import kitchenpos.products.tobe.domain.TobeProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TobeProductServiceTest {
    private TobeProductRepository tobeProductRepository;
    private PurgomalumClient purgomalumClient;
    private TobeProductService tobeProductService;

    private InMemoryApplicationEventPublisher publisher;


    @BeforeEach
    void setUp() {
        publisher = new InMemoryApplicationEventPublisher();
        tobeProductRepository = new InMemoryProductRepository();
        purgomalumClient = new FakePurgomalumClient();
        tobeProductService = new TobeProductService(tobeProductRepository, purgomalumClient, publisher);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final TobeProduct expected = createProductRequest("후라이드", 16_000L);
        final TobeProduct actual = tobeProductService.create(new BigDecimal(16_000L), "후라이드");
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        assertThatThrownBy(() -> tobeProductService.create(price, "후라이드"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> tobeProductService.create(BigDecimal.valueOf(16_000L), name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID productId = tobeProductRepository.save(product("후라이드", 16_000L)).getId();
        final TobeProduct expected = changePriceRequest(15_000L);
        final TobeProduct actual = tobeProductService.changePrice(productId, BigDecimal.valueOf(15_000L));
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        assertThat(actual).isEqualTo(publisher.poll());
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final UUID productId = tobeProductRepository.save(product("후라이드", 16_000L)).getId();
        assertThatThrownBy(() -> tobeProductService.changePrice(productId, price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        tobeProductRepository.save(product("후라이드", 16_000L));
        tobeProductRepository.save(product("양념치킨", 16_000L));
        final List<TobeProduct> actual = tobeProductService.findAll();
        assertThat(actual).hasSize(2);
    }

    private TobeProduct createProductRequest(final String name, final long price) {
        return createProductRequest(name, BigDecimal.valueOf(price));
    }

    private TobeProduct createProductRequest(final String name, final BigDecimal price) {
        return product(name, price);
    }

    private TobeProduct changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private TobeProduct changePriceRequest(final BigDecimal price) {
        return product("후라이드 치킨", price);
    }

    public TobeProduct product() {
        return product("후라이드", 16_000L);
    }

    public TobeProduct product(final String name, final long price) {
        return product(name, BigDecimal.valueOf(price));
    }

    public TobeProduct product(final String name, final BigDecimal price) {
        return new TobeProduct(UUID.randomUUID(), new ProductName(name, purgomalumClient),
                               new ProductPrice(price));
    }
}
