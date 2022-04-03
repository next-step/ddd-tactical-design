package kitchenpos.products.tobe.application;

import kitchenpos.products.infra.MockPurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.MockProductRepository;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.dto.CreateProductRequest;
import kitchenpos.products.tobe.dto.ProductResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Spy
    private ProductRepository productRepository = new MockProductRepository();

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

    @Test
    @DisplayName("상품 생성 성공")
    void createSuccess() {
        // given
        CreateProductRequest request = new CreateProductRequest("product", 1000L);

        // when
        ProductResponse productResponse = sut.create(request);

        // then
        assertAll(
            () -> assertThat(productResponse.getName()).isEqualTo("product"),
            () -> assertThat(productResponse.getPrice()).isEqualTo(1000L)
        );
    }
}
