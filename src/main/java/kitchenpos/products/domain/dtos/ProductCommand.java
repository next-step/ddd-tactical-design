package kitchenpos.products.domain.dtos;

import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import kitchenpos.products.exception.ProductNameException;
import kitchenpos.products.exception.ProductPriceException;

public class ProductCommand {

    public static class RegisterProductCommand {
        @NotNull
        private final BigDecimal price;
        @NotNull
        private final String name;

        public RegisterProductCommand(BigDecimal price, String name) {
            validatePrice(price);
            validateName(name);
            this.price = price;
            this.name = name;
        }

        private void validateName(String name) {
            if (Objects.isNull(name)) {
                throw new ProductNameException(null);
            }
        }

        public BigDecimal getPrice() {
            return price;
        }

        public String getName() {
            return name;
        }
    }

    public static class ChangePriceCommand {
        @NotNull
        private final BigDecimal price;

        public ChangePriceCommand(BigDecimal price) {
            validatePrice(price);
            this.price = price;
        }

        public BigDecimal getPrice() {
            return price;
        }
    }

    private static void validatePrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductPriceException(price);
        }
    }
}
