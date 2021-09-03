package kitchenpos.products.tobe.ui.dto;

import java.math.BigDecimal;

public class ProductRequests {

    public static class Create {

        private String displayedName;
        private BigDecimal price;

        public Create(String displayedName, BigDecimal price) {
            this.displayedName = displayedName;
            this.price = price;
        }

        public String getDisplayedName() {
            return displayedName;
        }

        public BigDecimal getPrice() {
            return price;
        }
    }

    public static class ChangePrice {

        private BigDecimal price;

        public ChangePrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getPrice() {
            return price;
        }
    }
}
