package kitchenpos.products.tobe.application;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.infra.MockPurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.MockProductRepository;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.dto.CreateProductRequest;
import kitchenpos.products.tobe.dto.ModifyProductPriceRequest;
import kitchenpos.products.tobe.dto.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.newProduct;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Spy
    private ProductRepository productRepository = new MockProductRepository();

    @Mock
    private MenuRepository menuRepository;

    @Spy
    private PurgomalumClient purgomalumClient = new MockPurgomalumClient();

    @InjectMocks
    private ProductService sut;

    @ParameterizedTest
    @ValueSource(strings = {"shit", "fuck"})
    @DisplayName("상품명에 비속어 포함 시 상품 생성 실패")
    void createFail(String name) {
        // given
        CreateProductRequest request = new CreateProductRequest(name, 1000L);

        // when
        assertThatIllegalArgumentException().isThrownBy(() -> sut.create(request));
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(longs = -1000L)
    @NullSource
    @ParameterizedTest
    void create(final Long price) {
        // given
        CreateProductRequest request = new CreateProductRequest("name", price);

        // when
        assertThatIllegalArgumentException().isThrownBy(() -> sut.create(request));
    }

    @Test
    @DisplayName("상품 생성 성공")
    void createSuccess() {
        // given
        CreateProductRequest request = new CreateProductRequest("product", 1000L);

        // when
        ProductResponse productResponse = sut.create(request);

        // then
        assertAll(
            () -> assertThat(productResponse.getId()).isNotBlank(),
            () -> assertThat(productResponse.getName()).isEqualTo("product"),
            () -> assertThat(productResponse.getPrice()).isEqualTo(1000L)
        );
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        // given
        UUID productId = productRepository.save(newProduct("후라이드", 16_000L)).getId();
        ModifyProductPriceRequest expected = new ModifyProductPriceRequest(15_000L);

        // when
        ProductResponse actual = sut.changePrice(productId, expected);

        // then
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(longs = -1000L)
    @NullSource
    @ParameterizedTest
    void changePrice(Long price) {
        // given
        UUID productId = productRepository.save(newProduct("후라이드", 16_000L)).getId();
        ModifyProductPriceRequest expected = new ModifyProductPriceRequest(price);

        // when
        assertThatThrownBy(() -> sut.changePrice(productId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        // given
        UUID id = productRepository.save(newProduct("후라이드", 16_000L)).getId();
        ModifyProductPriceRequest request = new ModifyProductPriceRequest(8_000L);

        Menu menu = menu(19_000L, true, menuProduct(product("후라이드", 16_000L), 1L));
        given(menuRepository.findAllByProductId(any())).willReturn(Collections.singletonList(menu));

        // when
        sut.changePrice(id, request);

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        // given
        productRepository.save(newProduct("후라이드", 16_000L));
        productRepository.save(newProduct("양념치킨", 16_000L));

        // when
        List<ProductResponse> actual = sut.findAll();

        // then
        assertThat(actual).hasSize(2);
        assertThat(actual).extracting(ProductResponse::getName).containsOnly("후라이드", "양념치킨");
    }
}
