package kitchenpos.eatinorders.domain.menu;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderMenu(UUID id, BigDecimal price, boolean isDisplayed) {


}
