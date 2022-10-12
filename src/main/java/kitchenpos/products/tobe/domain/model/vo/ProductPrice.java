package kitchenpos.products.tobe.domain.model.vo;

import kitchenpos.global.domain.vo.ValueObject;
import org.springframework.util.Assert;

public class ProductPrice extends ValueObject {

    private final int price;

    public ProductPrice(int price) {
        Assert.isTrue(price >= 0, "상품 가격은 0원 미만일 수 없습니다.");
        this.price = price;
    }
}
