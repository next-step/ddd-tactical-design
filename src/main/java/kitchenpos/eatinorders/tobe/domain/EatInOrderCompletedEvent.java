package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;

public record EatInOrderCompletedEvent (
        UUID orderTableId
){

}
