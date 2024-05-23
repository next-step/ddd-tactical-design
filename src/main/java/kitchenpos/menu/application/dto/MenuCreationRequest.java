package kitchenpos.menu.application.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public record MenuCreationRequest(
	UUID menuGroupId,
	String name,
	BigDecimal price,
	boolean displayed,
	Map<UUID, Long> menuProductQuantities
) {
}
