package kitchenpos.eatinorder.tobe.domain;

import kitchenpos.eatinorder.application.EatInOrderService;
import kitchenpos.eatinorder.tobe.domain.order.ClearedTable;
import kitchenpos.eatinorder.tobe.domain.order.EatInOrder;
import kitchenpos.eatinorder.tobe.domain.order.EatInOrderStatus;
import kitchenpos.eatinorder.tobe.domain.order.EatInOrderRepository;
import kitchenpos.eatinorder.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorder.tobe.domain.ordertable.OrderTableRepository;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class EatInOrderServiceTest {
    @Mock
    private EatInOrderRepository orderRepository;
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private OrderTableRepository orderTableRepository;

    private ClearedTable clearedTable;

    private EatInOrderService eatInOrderService;

    @BeforeEach
    void setUp() {
        eatInOrderService = new EatInOrderService(orderRepository, menuRepository, orderTableRepository, clearedTable);
    }

    @Nested
    @DisplayName("주문 접수")
    class Waiting {

        @Test
        @DisplayName("주문이 접수완료된 경우 주문상태가 대기중(WAITING)이 된다.")
        void success1() {

        }

        @Test
        @DisplayName("하나의 주문에 여러개 메뉴 주문이 가능하다.")
        void success2() {

        }

        @Test
        @DisplayName("주문타입 정보가 없는 경우 주문을 받을 수 없다.")
        void typeFail() {

        }

        //        @ParameterizedTest
//        @NullAndEmptySource
        @DisplayName("메뉴를 하나도 선택하지 않은 경우 주문할 수 없다.")
        void fail1() {

        }

        @Test
        @DisplayName("주문한 메뉴들 중에 하나라도 등록되어 있지 않은 메뉴가 포함되어 있으면 주문할 수 없다.")
        void fail2() {

        }

        @Test
        @DisplayName("미노출된 메뉴가 포함되어있는 경우 주문할 수 없다.")
        void fail3() {

        }

        @Test
        @DisplayName("주문한 메뉴의 금액과 등록된 메뉴 금액이 다른 경우 주문할 수 없다.")
        void fail4() {

        }

        @Nested
        @DisplayName("매장 주문")
        class EatIn {

            @Test
            @DisplayName("매장주문을 접수받을 수 있다.")
            void success() {

            }

            @ParameterizedTest
            @ValueSource(ints = {-1})
            @DisplayName("0개보다 적게 취소주문이 가능하다.")
            void fail2(int input) {

            }

            @ParameterizedTest
            @NullSource
            @DisplayName("등록된 테이블정보가 아닌 경우 접수가 불가하다.")
            void fail1(OrderTable input) {

            }

            @Test
            @DisplayName("손님이 착석중이 아닌 테이블로 접수가 들어온 경우 접수가 불가능하다.")
            void fail2() {

            }
        }
    }

    @Nested
    @DisplayName("주문 수락")
    class Accepted {
        @Test
        @DisplayName("주문수락이 완료된 경우 주문상태가 대기중 에서 수락(ACCEPTED) 상태로 변경된다.")
        void success() {

        }

        @Test
        @DisplayName("접수된적 없는 주문인 경우 수락이 불가능하다.")
        void fail1() {

        }

        @Test
        @DisplayName("주문상태가 대기중이 아닌 경우 처리가 불가능하다.")
        void fail2() {

        }
    }

    @Nested
    @DisplayName("음식 제공 완료")
    class Served {
        @Test
        @DisplayName("처리가 되는경우 주문상태가 수락에서 에서 음식제공 완료(SERVED) 로 변경된다.")
        void success() {

        }

        @Test
        @DisplayName("접수된적 없는 주문인 경우 처리할 수 없다.")
        void fail1() {

        }

        @Test
        @DisplayName("주문상태가 수락상태가 아닌 경우 처리할 수 없다.")
        void fail2() {

        }
    }

    @Nested
    @DisplayName("주문 처리 완료")
    class Completed {
        @ParameterizedTest
        @MethodSource("localParameters")
        @DisplayName("완료 상태가 되면 주문 처리 완료(COMPLETED) 가 된다.")
        void success(EatInOrderStatus orderStatus) {

        }

        static Stream<Arguments> localParameters() {
            return Stream.of(
                    Arguments.of(EatInOrderStatus.SERVED)
            );
        }

        //        @ParameterizedTest
        @DisplayName("메징식사인 경우 주문상태가 완료가 되면 주문테이블의 손님 수와 착석여부 상태를 빈 좌석으로 정리한다. (손님 수 0명, 미착석 상태로 변경)")
        void success2() {

        }

        @Test
        @DisplayName("접수된적 없는 주문인 경우 처리할 수 없다.")
        void fail1() {

        }

        //        @ParameterizedTest
        @DisplayName("음식 제공 완료 상태가 아니면 처리할 수 없다.")
        void fail3() {

        }

    }
}
