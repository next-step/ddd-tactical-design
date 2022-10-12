package kitchenpos.products.tobe.domain.model.vo;

import java.util.Objects;
import org.springframework.util.Assert;

public class ProductPrice {

    private final int price;

    public ProductPrice(int price) {
        Assert.isTrue(price >= 0, "상품 가격은 0원 미만일 수 없습니다.");
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductPrice that = (ProductPrice) o;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
