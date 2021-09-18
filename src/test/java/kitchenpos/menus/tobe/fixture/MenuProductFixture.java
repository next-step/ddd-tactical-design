package kitchenpos.menus.tobe.fixture;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.Price;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import static kitchenpos.menus.tobe.application.FakeInMemoryProductRepository.INVALID_ID;

public class MenuProductFixture {
    public static MenuProduct menuProduct() {
        long seq = new Random().nextLong();
        UUID productId = UUID.randomUUID();
        long quantity = 2L;
        Price price = new Price(BigDecimal.valueOf(15_000L));
        return new MenuProduct(seq, productId, quantity, price);
    }

    public static MenuProduct menuProduct_with_non_existent_product() {
        long seq = new Random().nextLong();
        UUID productId = INVALID_ID;
        long quantity = 2L;
        Price price = new Price(BigDecimal.valueOf(15_000L));
        return new MenuProduct(seq, productId, quantity, price);
    }

    public static MenuProduct menuProduct(Long seq, UUID productId,long quantity, Price price) {
        return new MenuProduct(seq, productId, quantity, price);
    }
}
