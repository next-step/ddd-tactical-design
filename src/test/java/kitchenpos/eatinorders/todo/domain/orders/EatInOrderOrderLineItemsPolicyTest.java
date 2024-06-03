package kitchenpos.eatinorders.todo.domain.orders;

import kitchenpos.eatinorders.exception.KitchenPosIllegalArgumentException;
import kitchenpos.support.domain.MenuClient;
import kitchenpos.support.domain.OrderLineItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static kitchenpos.Fixtures.orderLineItem;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("주문아이템 목록")
@ExtendWith(MockitoExtension.class)
class EatInOrderOrderLineItemsPolicyTest {
    @Mock
    private MenuClient menuClient;

    @InjectMocks
    private EatInOrderOrderLineItemsPolicy orderLineItemsPolicy;

    @DisplayName("[성공] 주문아이템 목록은 이미 등록되어진 메뉴를 중복으로 구성하지 않는다.")
    @Test
    void checkDuplicatedMenu() {
        List<OrderLineItem> orderLineItems = List.of(orderLineItem());
        given(menuClient.countMenusByIdIn(any())).willReturn(1);

        assertDoesNotThrow(() -> orderLineItemsPolicy.checkDuplicatedMenu(orderLineItems));
    }

    @DisplayName("[실패] 주문아이템 목록을 구성하는 메뉴가 중복되면 예외를 발생한다. ")
    @Test
    void fail_checkDuplicatedMenu() {
        List<OrderLineItem> orderLineItems = List.of(orderLineItem());
        given(menuClient.countMenusByIdIn(any())).willReturn(0);

        assertThatThrownBy(() -> orderLineItemsPolicy.checkDuplicatedMenu(orderLineItems))
                .isInstanceOf(KitchenPosIllegalArgumentException.class);
    }
}
