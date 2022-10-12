package kitchenpos.products.tobe.application.dto;

import java.util.UUID;

public class ProductCommand {

    public static class Create {

        private final String name;
        private final int price;

        public Create(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }
    }

    public static class ChangePrice {

        private final UUID id;
        private final int price;

        public ChangePrice(UUID id, int price) {
            this.id = id;
            this.price = price;
        }

        public UUID getId() {
            return id;
        }

        public int getPrice() {
            return price;
        }
    }
}
