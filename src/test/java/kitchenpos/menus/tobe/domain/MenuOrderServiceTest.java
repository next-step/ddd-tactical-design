package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.domain.event.OrderCreateRequested;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuOrderServiceTest {

    private MenuOrderService menuOrderService;
    private MenuRepository menuRepository;

    @BeforeEach
    public void init() {
        menuRepository = new InMemoryMenuRepository();
        menuOrderService = new MenuOrderService(menuRepository);
    }

    @DisplayName("존재하지 않은 메뉴에 대한 주문 요청이 오면 에러가 발생한다")
    @Test
    void test1() {
        //given
        Menu menu = menuRepository.save(MenuFixture.menu());
        UUID notSavedMenuId = UUID.randomUUID();

        //when && then
        assertThatThrownBy(
            () -> menuOrderService.eventListener(new OrderCreateRequested(List.of(notSavedMenuId)))
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("존재하지 않는 메뉴에 대한 주문이 되었습니다");
    }

    @DisplayName("숨겨진 메뉴에 대한 주문 요청이 오면 에러가 발생한다")
    @Test
    void test2() {
        //given
        Menu displayedMenu = menuRepository.save(MenuFixture.menu());
        Menu hidedMenu = menuRepository.save(MenuFixture.menu());
        hidedMenu.hide();
        OrderCreateRequested orderCreateRequested = new OrderCreateRequested(
            List.of(displayedMenu.getId(), hidedMenu.getId()));

        //when && then
        assertThatThrownBy(
            () -> menuOrderService.eventListener(orderCreateRequested)
        ).isInstanceOf(IllegalStateException.class)
            .hasMessage("표시되지 않은 메뉴에 대해 주문되었습니다");
    }
}