package kitchenpos.common.event.publisher;

import java.util.UUID;

public record OrderTableClearEvent(UUID orderTableId) {
}
