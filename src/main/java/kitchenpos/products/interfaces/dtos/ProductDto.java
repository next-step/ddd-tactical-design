package kitchenpos.products.interfaces.dtos;

import java.math.BigDecimal;
import java.util.UUID;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import kitchenpos.products.domain.dots.ProductCommand.ChangePriceCommand;
import kitchenpos.products.domain.dots.ProductCommand.RegisterProductCommand;
import kitchenpos.products.domain.dots.ProductInfo;

/**
 * create Product dto
 * change Price dto
 */
public class ProductDto {

    public static class RegisterProductRequest {
        @NotNull
        private final String name;
        @Max(value = 0)
        private final BigDecimal price;

        public RegisterProductRequest(String name, BigDecimal price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public RegisterProductCommand toCommand() {
            return new RegisterProductCommand(price, name);
        }
    }

    public static class ChangePriceRequest {
        @Max(value = 0)
        private final BigDecimal price;

        public ChangePriceRequest(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public ChangePriceCommand toCommand() {
            return new ChangePriceCommand(price);
        }
    }

    public static class ProductResponse {
        private final UUID id;
        private final BigDecimal price;
        private final String name;

        public ProductResponse(ProductInfo productInfo) {
            this.id = productInfo.getId();
            this.price = productInfo.getPrice();
            this.name = productInfo.getName();
        }
    }

}
