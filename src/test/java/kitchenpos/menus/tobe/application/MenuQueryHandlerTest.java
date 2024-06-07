//package kitchenpos.menus.tobe.application;
//
//import static kitchenpos.Fixtures.menu;
//import static kitchenpos.Fixtures.menuGroup;
//import static kitchenpos.Fixtures.menuProduct;
//import static kitchenpos.Fixtures.product;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//import kitchenpos.menus.tobe.domain.repository.InMemoryMenuGroupRepository;
//import kitchenpos.menus.tobe.domain.repository.InMemoryMenuRepository;
//import kitchenpos.menus.tobe.domain.entity.Menu;
//import kitchenpos.menus.tobe.domain.entity.MenuGroup;
//import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
//import kitchenpos.menus.tobe.domain.repository.MenuRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//class MenuQueryHandlerTest {
//    private MenuRepository menuRepository;
//    private MenuGroupRepository menuGroupRepository;
//    private MenuQueryHandler menuQueryHandler;
//
//    @BeforeEach
//    public void setup() {
//        this.menuRepository = new InMemoryMenuRepository();
//        this.menuGroupRepository = new InMemoryMenuGroupRepository();
//        this.menuQueryHandler = new MenuQueryHandler(menuRepository, menuGroupRepository);
//    }
//
//    @DisplayName("메뉴의 목록을 조회할 수 있다.")
//    @Test
//    void findAllMenuGroupMenu() {
//        menuRepository.save(menu(19_000L, true, menuProduct(product("후라이드", 16_000L), 2L)));
//        final List<Menu> actual = menuQueryHandler.findAllMenu();
//        assertThat(actual).hasSize(1);
//    }
//
//    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
//    @Test
//    void findAll() {
//        menuGroupRepository.save(menuGroup("두마리메뉴"));
//        final List<MenuGroup> actual = menuQueryHandler.findAllMenuGroup();
//        assertThat(actual).hasSize(1);
//    }
//}
