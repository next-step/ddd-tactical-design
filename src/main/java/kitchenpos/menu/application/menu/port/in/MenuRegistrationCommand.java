package kitchenpos.menu.application.menu.port.in;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.menu.MenuPrice;
import kitchenpos.menu.domain.menu.Quantity;
import kitchenpos.support.vo.Name;
import org.apache.commons.lang3.tuple.Pair;

public final class MenuRegistrationCommand {

    private final Name nameCandidate;
    private final MenuPrice price;
    private final List<Pair<UUID, Quantity>> productIdAndQuantities;
    private final UUID menuGroupId;

    public MenuRegistrationCommand(final Name nameCandidate, final MenuPrice price,
        final List<Pair<UUID, Quantity>> productIdAndQuantities, final UUID menuGroupId) {

        this.nameCandidate = nameCandidate;
        this.price = price;
        this.productIdAndQuantities = productIdAndQuantities;
        this.menuGroupId = menuGroupId;
    }

    public Name getNameCandidate() {
        return nameCandidate;
    }

    public MenuPrice getPrice() {
        return price;
    }

    public List<Pair<UUID, Quantity>> getProductIdAndQuantities() {
        return productIdAndQuantities;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }


    public Quantity getProductQuantity(final UUID productId) {
        return productIdAndQuantities.stream()
            .filter(it -> it.getLeft().equals(productId))
            .findFirst()
            .map(Pair::getRight)
            .orElseThrow();
    }
}
