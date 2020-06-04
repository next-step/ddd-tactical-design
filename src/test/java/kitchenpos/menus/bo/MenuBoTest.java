package kitchenpos.menus.bo;

import static kitchenpos.menus.Fixtures.twoChickens;
import static kitchenpos.menus.Fixtures.twoFriedChickens;
import static kitchenpos.menus.Fixtures.twoFriedChickensRequest;
import static kitchenpos.products.Fixtures.friedChicken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import kitchenpos.menus.model.MenuCreateRequest;
import kitchenpos.menus.tobe.domain.group.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.infra.MenuProductServiceAdapter;
import kitchenpos.products.bo.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuBoTest {

    private MenuBo menuBo;
    private MenuGroupRepository menuGroupRepository;
    private MenuRepository menuRepository;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        menuBo = new MenuBo(
            menuRepository = new InMemoryMenuRepository(),
            menuGroupRepository = new InMemoryMenuGroupRepository(),
            new MenuProductServiceAdapter(productRepository = new InMemoryProductRepository())
        );
        menuGroupRepository.save(twoChickens());
        productRepository.save(friedChicken());
    }

    @DisplayName("1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        // given
        final MenuCreateRequest createRequest = twoFriedChickensRequest();

        // when
        final Menu actual = menuBo.create(createRequest);

        // then
        assertCreatedMenu(createRequest, actual);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"-1000", "33000"})
    void create(final BigDecimal price) {
        // given
        final MenuCreateRequest createRequest = twoFriedChickensRequest();
        createRequest.setPrice(price);

        // when, then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> menuBo.create(createRequest));

    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @ParameterizedTest
    @NullSource
    void create(final Long menuGroupId) {
        // given
        final MenuCreateRequest request = twoFriedChickensRequest();
        request.setMenuGroupId(menuGroupId);

        // when
        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> menuBo.create(request));
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final Menu twoFriedChickens = menuRepository.save(twoFriedChickens());

        // when
        final List<Menu> actual = menuBo.list();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(Arrays.asList(twoFriedChickens));
    }

    private void assertCreatedMenu(final MenuCreateRequest request, final Menu actual) {
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(request.getName()),
            () -> assertThat(actual.getPrice().getValue()).isEqualTo(request.getPrice()),
            () -> assertThat(actual.getMenuGroupId()).isEqualTo(request.getMenuGroupId())

        );

        int requestProductSize = request.getMenuProducts().size();
        for (int i = 0; i < requestProductSize; i++) {
            MenuCreateRequest.MenuProduct requestMenuProduct = request.getMenuProducts().get(i);
            MenuProduct menuProduct = actual.getMenuProducts().getMenuProducts().get(i);
            assertAll(
                () -> assertThat(requestMenuProduct.getProductId())
                    .isEqualTo(menuProduct.getProductId()),
                () -> assertThat(requestMenuProduct.getQuantity())
                    .isEqualTo(menuProduct.getQuantity())
            );
        }


    }
}
