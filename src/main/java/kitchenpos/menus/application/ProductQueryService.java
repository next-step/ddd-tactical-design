package kitchenpos.menus.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface ProductQueryService {
    Map<UUID, BigDecimal> findAllByIdIn(List<UUID> productIds);
}
