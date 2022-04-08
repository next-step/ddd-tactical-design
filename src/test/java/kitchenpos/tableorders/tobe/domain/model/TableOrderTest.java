package kitchenpos.tableorders.tobe.domain.model;

import kitchenpos.global.domain.vo.Price;
import kitchenpos.tableorders.tobe.domain.dto.CreatOrderLineItemCommand;
import kitchenpos.tableorders.tobe.domain.dto.MenuResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TableOrderTest {

    public OrderLineItems 정상적으로_생성된_주문_라인_아이템;
    public OrderTable 정상적으로_지정된_테이블;
    public TableOrder 정상적으로_생성된_주문;

    @BeforeEach
    void setup() {
        UUID 메뉴_아이디 = UUID.randomUUID();
        BigDecimal 주문_메뉴_가격 = BigDecimal.valueOf(1000L);
        int 주문_메뉴_수량 = 1;

        CreatOrderLineItemCommand command = new CreatOrderLineItemCommand(메뉴_아이디, 주문_메뉴_가격, 주문_메뉴_수량);
        Map<UUID, MenuResponse> menus = new HashMap<>();
        menus.put(메뉴_아이디, new MenuResponse(메뉴_아이디, new Price(주문_메뉴_가격), true));

        정상적으로_생성된_주문_라인_아이템 = new OrderLineItems(
                Arrays.asList(command),
                menus
        );

        정상적으로_지정된_테이블 = new OrderTable("루프탑 테이블");
        정상적으로_지정된_테이블.assign(2);

        정상적으로_생성된_주문 = new TableOrder(정상적으로_지정된_테이블, 정상적으로_생성된_주문_라인_아이템);
    }


    @DisplayName("매장 주문(table order) 주문은 지정(assign) 된  주문테이블(order table)을 가진다.")
    @Test
    void create01() {
        OrderTable 미지정_테이블 = new OrderTable("루프탑 테이블");

        assertThatThrownBy(() -> new TableOrder(미지정_테이블, 정상적으로_생성된_주문_라인_아이템))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("매장 주문을 이용하기 위해서는 반드시 테이블이 선택되어야 합니다.");

    }

    @DisplayName("매장 주문(table order)이 접수되면 대기 상태를 가진다.")
    @Test
    void create02() {
        assertThat(정상적으로_생성된_주문.getStatus()).isEqualTo(OrderStatus.WAITING);
    }


    @DisplayName("매장 주문(table order)이 요청 상태일때만 수락할 수 있다.")
    @Test
    void accept01() {
        //given
        정상적으로_생성된_주문.accept();

        //when & then
        assertThatThrownBy(() -> 정상적으로_생성된_주문.accept())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("주문이 대기 상태가 아닙니다.");
    }

    @DisplayName("매장 주문(table order)을 수락할 수 있다.")
    @Test
    void accept02() {
        //when
        정상적으로_생성된_주문.accept();

        //then
        assertThat(정상적으로_생성된_주문.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }


    @DisplayName("매장 주문(table order)이 수락 상태일때만 준비 완료할 수 있다.")
    @Test
    void serve01() {
        //given

        //when & then
        assertThatThrownBy(() -> 정상적으로_생성된_주문.serve())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("주문이 수락 상태가 아닙니다.");
    }

    @DisplayName("매장 주문(table order)을 준비 완료할 수 있다.")
    @Test
    void serve02() {
        //given
        정상적으로_생성된_주문.accept();

        //when
        정상적으로_생성된_주문.serve();

        //then
        assertThat(정상적으로_생성된_주문.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("매장 주문(table order)이 준비 완료 상태일때만 완료할 수 있다.")
    @Test
    void complete01() {
        //given

        //when & then
        assertThatThrownBy(() -> 정상적으로_생성된_주문.complete())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("주문이 준비완료 상태가 아닙니다.");
    }

    @DisplayName("매장 주문(table order) 이 주문완료 된 경우 테이블을 정리(clean) 해야 한다. ")
    @Test
    void complete02() {
        //given
        정상적으로_생성된_주문.accept();
        정상적으로_생성된_주문.serve();

        //when
        정상적으로_생성된_주문.complete();

        //when & then
        assertAll(
                () -> assertThat(정상적으로_지정된_테이블.isEmpty()).isTrue(),
                () -> assertThat(정상적으로_지정된_테이블.getNumberOfGuests()).isEqualTo(0)
        );
    }

    @DisplayName("매장 주문(table order)을 주문 완료할 수 있다.")
    @Test
    void complete03() {
        //given
        정상적으로_생성된_주문.accept();
        정상적으로_생성된_주문.serve();

        //when
        정상적으로_생성된_주문.complete();

        //then
        assertThat(정상적으로_생성된_주문.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }


}
