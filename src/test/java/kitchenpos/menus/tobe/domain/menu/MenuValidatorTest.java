package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kitchenpos.ToBeFixtures;
import kitchenpos.common.domain.MenuGroupId;
import kitchenpos.common.domain.ProductId;
import kitchenpos.menus.tobe.application.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.products.application.tobe.InMemoryProductRepository;
import kitchenpos.products.domain.tobe.domain.Product;
import kitchenpos.products.domain.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuValidatorTest {

    private MenuGroupRepository menuGroupRepository;
    private ProductRepository productRepository;
    private MenuValidator menuValidator;

    @BeforeEach
    void setUp() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        menuValidator = new MenuValidator(menuGroupRepository, productRepository);
    }

    @DisplayName("메뉴가 특정 메뉴 그룹에 속하는지 검증할 수 있다.")
    @Test
    void 메뉴그룹검증() {
        final MenuGroupId existingMenuGroupId = menuGroupRepository.save(
            ToBeFixtures.menuGroup()
        ).getId();

        assertDoesNotThrow(
            () -> menuValidator.validateMenuGroup(existingMenuGroupId)
        );
    }

    @DisplayName("메뉴가 특정 메뉴 그룹에 속하는지 검증할 수 있다.")
    @Test
    void 메뉴그룹검증2() {
        final MenuGroupId nonExistingMenuGroupId = new MenuGroupId(UUID.randomUUID());

        assertThatThrownBy(
            () -> menuValidator.validateMenuGroup(nonExistingMenuGroupId)
        ).isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("메뉴에 등록 요청된 상품이 존재하는지 검증할 수 있다.")
    @Test
    void 상품검증() {
        final List<ProductId> existingProductIds = Stream.of(
                productRepository.save(
                    ToBeFixtures.product("후라이드치킨", 16000)
                ),
                productRepository.save(
                    ToBeFixtures.product("양념치킨", 16000)
                )
            )
            .map(Product::getId)
            .collect(Collectors.toList());

        assertDoesNotThrow(
            () -> menuValidator.validateProducts(existingProductIds)
        );
    }

    @DisplayName("메뉴에 등록 요청된 상품이 존재하는지 검증할 수 있다.")
    @Test
    void 상품검증2() {
        final List<ProductId> nonExistingProductIds = Arrays.asList(
            new ProductId(UUID.randomUUID()),
            new ProductId(UUID.randomUUID())
        );

        assertThatThrownBy(
            () -> menuValidator.validateProducts(nonExistingProductIds)
        ).isInstanceOf(NoSuchElementException.class);
    }
}
