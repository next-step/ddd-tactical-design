package kitchenpos.menus.tobe.application;

import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;

class MenuQueryHandlerTest {
    private MenuRepository menuRepository;
    private MenuQueryHandler menuQueryHandler;

    @BeforeEach
    public void setup() {
        this.menuRepository = new InMemoryMenuRepository();
        this.menuQueryHandler = new MenuQueryHandler(menuRepository);
    }

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuRepository.save(menu(19_000L, true, menuProduct(product("후라이드", 16_000L), 2L)));
        final List<Menu> actual = menuQueryHandler.findAll();
        assertThat(actual).hasSize(1);
    }
}
