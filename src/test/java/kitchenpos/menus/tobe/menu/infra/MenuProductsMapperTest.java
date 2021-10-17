package kitchenpos.menus.tobe.menu.infra;

import static kitchenpos.fixture.ProductFixture.PRODUCT1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.menu.domain.model.MenuProducts;
import kitchenpos.menus.tobe.menu.dto.MenuProductRequest;
import kitchenpos.products.tobe.application.TobeInMemoryProductRepository;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductsMapperTest {

    private ProductRepository productRepository;
    private MenuProductsMapper menuProductsMapper;
    private Product product;

    @BeforeEach
    void setUp() {
        productRepository = new TobeInMemoryProductRepository();
        menuProductsMapper = new MenuProductsMapper(productRepository);
        product = PRODUCT1();
    }

    @DisplayName("상품이 하나라도 등록되지 않은 경우 List<MenuProductRequest>를 MenuProducts로 매핑할 수 없다")
    @Test
    void canNotMapToMenuProducts() {
        final List<MenuProductRequest> menuProductRequests = Collections.singletonList(new MenuProductRequest(UUID.randomUUID(), 2L));

        assertThatThrownBy(() -> menuProductsMapper.toMenuProducts(menuProductRequests))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품이 모두 등록된 경우 List<MenuProductRequest>를 MenuProducts로 매핑할 수 있다")
    @Test
    void toMenuProducts() {
        productRepository.save(product);
        final List<MenuProductRequest> menuProductRequests = Collections.singletonList(new MenuProductRequest(product.getId(), 2L));

        final MenuProducts menuProducts = menuProductsMapper.toMenuProducts(menuProductRequests);

        assertThat(menuProducts).isNotNull();
    }

}
