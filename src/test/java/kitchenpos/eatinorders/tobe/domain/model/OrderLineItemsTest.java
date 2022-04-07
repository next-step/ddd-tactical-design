package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.eatinorders.tobe.domain.dto.CreatOrderLineItemCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderLineItemsTest {


    @DisplayName("주문하려는 메뉴는 반드시 존재해야 한다.")
    @Test
    void create() {
        List<CreatOrderLineItemCommand> commands = new ArrayList<>();
        commands.add(new CreatOrderLineItemCommand());

        assertThatThrownBy(() -> new OrderLineItems(commands, Collections.emptyMap()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴가 존재하지 않습니다.");
    }

}
