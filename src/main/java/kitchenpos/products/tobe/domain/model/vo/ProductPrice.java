package kitchenpos.products.tobe.domain.model.vo;

import java.math.BigDecimal;
import kitchenpos.global.domain.vo.ValueObject;
import org.springframework.util.Assert;

public class ProductPrice extends ValueObject {

    private final BigDecimal price;

    public ProductPrice(int price) {
        Assert.isTrue(price >= 0, "상품 가격은 0원 미만일 수 없습니다.");
        this.price = BigDecimal.valueOf(price);
    }
}
