package kitchenpos.reader;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductPriceReader {

    BigDecimal getProductPriceById(UUID productId);
}
