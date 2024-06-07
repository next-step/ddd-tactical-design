package kitchenpos.products.tobe.domain.entity;

import kitchenpos.products.tobe.domain.ProductRepositoryImpl;
import kitchenpos.products.tobe.domain.vo.DisplayedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Menu {
    private final UUID id;
    private final DisplayedName displayedName;
    private final List<UUID> productIds;

    public Menu(UUID id, DisplayedName displayedName) {
        this.id = (id != null) ? id : UUID.randomUUID();
        this.displayedName = displayedName;
        this.productIds = new ArrayList<>();
    }

    public Menu(DisplayedName displayedName) {
        this(UUID.randomUUID(), displayedName);
    }

    public UUID getId() {
        return this.id;
    }

    public int getPrice(ProductRepositoryImpl repo){
        int price = 0;
        for (UUID productId : this.productIds) {
            Optional<Product> product = repo.findById(productId);
            if(product.isPresent()){
                price += product.get().getPrice().getValue();
            }
        }
        return price;
    }

    public DisplayedName getName() {
        return this.displayedName;
    }

    public void addProduct(Product product) {
        this.productIds.add(product.getId());
    }

    public void addProducts(List<Product> products) {
        for (Product product : products) {
            this.addProduct(product);
        }
    }
}
