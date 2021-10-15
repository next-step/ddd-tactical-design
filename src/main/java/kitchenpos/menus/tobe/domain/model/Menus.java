package kitchenpos.menus.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Menus {

    private final List<Menu> menus;

    public Menus(List<Menu> menus) {
        this.menus = menus;
    }

    public Optional<Menu> getMenu(final UUID menuId) {
        return menus.stream()
                .filter(menu -> menuId.equals(menu.getId()))
                .findAny();
    }

    public boolean isSizeEqual(final int size) {
        return menus.size() == size;
    }

    public boolean existsMenuNotDisplay() {
        return menus.stream()
                .anyMatch(Predicate.not(Menu::isDisplayed));
    }

    public Map<UUID, Price> getPrices() {
        return menus.stream()
                .collect(Collectors.toMap(Menu::getId, menu -> new Price(menu.getPrice())));
    }
}
