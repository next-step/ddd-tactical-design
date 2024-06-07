package kitchenpos.products.tobe.domain.entity;

import kitchenpos.products.tobe.domain.vo.DisplayedName;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Menu {
    private final UUID id;
    private final DisplayedName displayedName;
    private final List<Product> products;

    public Menu(UUID id, DisplayedName displayedName) {
        this.id = (id != null) ? id : UUID.randomUUID();
        this.displayedName = displayedName;
        this.products = new ArrayList<>();
    }

    public Menu(DisplayedName displayedName) {
        this(UUID.randomUUID(), displayedName);
    }

    public UUID getId() {
        return this.id;
    }

    public int getPrice(){
        int price = 0;
        for (Product product : this.products) {
            price += product.getPrice().getValue();
        }
        return price;
    }

    public DisplayedName getName() {
        return this.displayedName;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addProducts(List<Product> products) {
        for (Product product : products) {
            this.addProduct(product);
        }
    }
}
