package kitchenpos.menus.helper;

import kitchenpos.global.domain.vo.Price;
import kitchenpos.menus.tobe.domain.dto.ProductResponse;
import kitchenpos.menus.tobe.domain.model.MenuGroup;
import kitchenpos.menus.tobe.domain.model.MenuProduct;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuFixtureFactory {

    public static final MenuGroup 런치_세트_메뉴 = new MenuGroup("런치 세트 메뉴");
    public static final MenuProduct 미트파이_메뉴_상품_1500원_1개 = new MenuProduct(new ProductResponse(UUID.randomUUID(), new Price(BigDecimal.valueOf(1500L))), 1);
    public static final MenuProduct 레몬에이드_메뉴_상품_1000원_1개 = new MenuProduct(new ProductResponse(UUID.randomUUID(), new Price(BigDecimal.valueOf(1000L))), 1);


}
