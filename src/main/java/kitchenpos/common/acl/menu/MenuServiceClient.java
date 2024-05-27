package kitchenpos.common.acl.menu;

import java.util.UUID;

public interface MenuServiceClient {
    void hideMenuBasedOnProductPrice(UUID productId);
}
