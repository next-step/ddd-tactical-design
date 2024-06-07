package kitchenpos.products.tobe.domain.entity;

import kitchenpos.products.tobe.domain.ProductRepositoryImpl;
import kitchenpos.products.tobe.domain.vo.DisplayedName;
import kitchenpos.products.tobe.domain.vo.Price;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Menu {
    private final UUID id;
    private final DisplayedName displayedName;
    private final List<UUID> productIds;
    private final Price price;
    private boolean isDisplay;
    private final ProductRepositoryImpl repo;


    public Menu(UUID id, DisplayedName displayedName, Price price, ProductRepositoryImpl repo) {
        this.id = (id != null) ? id : UUID.randomUUID();
        this.displayedName = displayedName;
        this.price = price;
        this.productIds = new ArrayList<>();
        this.isDisplay = true;
        this.repo = repo;
    }

    public Menu(DisplayedName displayedName, Price price, ProductRepositoryImpl repo) {
        this(UUID.randomUUID(), displayedName, price, repo);
    }

    public UUID getId() {
        return this.id;
    }

    public boolean isShow() {
        return this.isDisplay;
    }

    public int getProductsTotalPrice() {
        int price = 0;
        for (UUID productId : this.productIds) {
            Optional<Product> product = this.repo.findById(productId);
            if (product.isPresent()) {
                price += product.get().getPrice().getValue();
            }
        }
        return price;
    }

    public DisplayedName getName() {
        return this.displayedName;
    }

    public void registerProduct(Product product) {
        this.productIds.add(product.getId());
        this.compareProductsTotalPriceWithMenuPrice();
    }

    private void compareProductsTotalPriceWithMenuPrice() {
        int totalPrice = this.getProductsTotalPrice();
        if (totalPrice > this.price.getValue()) {
            this.isDisplay = false;
        }
    }
}
