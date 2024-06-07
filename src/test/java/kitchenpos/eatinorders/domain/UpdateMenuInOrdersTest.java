package kitchenpos.eatinorders.domain;

import kitchenpos.ToBeFixtures;
import kitchenpos.eatinorders.EatInOrderFixture;
import kitchenpos.eatinorders.tobe.domain.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.entity.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.entity.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.service.ChangedMenuSender;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@DisplayName("주문 BC 메뉴 업데이트 도메인 서비스")
public class UpdateMenuInOrdersTest {
    @Autowired
    private ChangedMenuSender changedMenuSender;
    @MockBean
    private EatInOrderRepository orderRepository;
    @MockBean
    private OrderTableRepository tableRepository;

    private UUID 메뉴_식별자;

    @BeforeEach
    void setUp() {
        메뉴_식별자 = UUID.randomUUID();
    }

    @Test
    @DisplayName("메뉴가 비공개로 변경될 경우 예외가 발생한다.")
    public void exception_changedDisplayed() {
        EatInOrder 매장_식사 = EatInOrderFixture.eatInOrderOf(createDefaultOrderLineItems(), UUID.randomUUID());
        Assertions.assertThat(매장_식사.getId()).isNotNull();

        Mockito.when(orderRepository.findAllByMenuId(메뉴_식별자))
                .thenReturn(List.of(매장_식사));

        Menu 메뉴_비공개 = ToBeFixtures.menuCreateOf(메뉴_식별자, BigDecimal.ONE, false);
        Assertions.assertThatThrownBy(
                () -> changedMenuSender.sendAtChangedMenu(메뉴_비공개)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("메뉴 금액이 변경되어 주문 항목 금액과 다를 경우 예외가 발생한다.")
    public void exception_changedPrice() {
        EatInOrder 매장_식사 = EatInOrderFixture.eatInOrderOf(createDefaultOrderLineItems(), UUID.randomUUID());
        Assertions.assertThat(매장_식사.getId()).isNotNull();

        Mockito.when(orderRepository.findAllByMenuId(메뉴_식별자))
                .thenReturn(List.of(매장_식사));

        Menu 메뉴_금액_변경 = ToBeFixtures.menuCreateOf(메뉴_식별자, BigDecimal.TWO, true);
        Assertions.assertThatThrownBy(
                () -> changedMenuSender.sendAtChangedMenu(메뉴_금액_변경)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    private OrderLineItems createDefaultOrderLineItems() {
        OrderLineItem orderLineItem = EatInOrderFixture.orderLineItemOf(
                1, BigDecimal.ONE, 메뉴_식별자);
        return new OrderLineItems(List.of(orderLineItem));
    }
}
