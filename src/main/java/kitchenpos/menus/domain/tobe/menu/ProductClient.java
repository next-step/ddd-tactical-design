package kitchenpos.menus.domain.tobe.menu;

import java.util.List;
import java.util.UUID;

public interface ProductClient {
  int countProductIds(List<UUID> productIds);
}
