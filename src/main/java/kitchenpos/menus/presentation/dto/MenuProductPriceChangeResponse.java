package kitchenpos.menus.presentation.dto;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuId;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class MenuProductPriceChangeResponse {

    private List<MenuProductPriceChangeResult> results;

    public MenuProductPriceChangeResponse(List<MenuProductPriceChangeResult> results) {
        this.results = results;
    }

    public static MenuProductPriceChangeResponse from(List<Menu> menus) {
        List<MenuProductPriceChangeResult> results = menus.stream()
                .map(menu -> new MenuProductPriceChangeResult(menu.getId(), menu.isDisplayed()))
                .collect(toList());

        return new MenuProductPriceChangeResponse(results);
    }

    public static class MenuProductPriceChangeResult {
        private MenuId id;
        private boolean displayed;

        public MenuProductPriceChangeResult(MenuId id, boolean displayed) {
            this.id = id;
            this.displayed = displayed;
        }

        public MenuId getId() {
            return id;
        }

        public boolean isDisplayed() {
            return displayed;
        }
    }

    public List<MenuProductPriceChangeResult> getResults() {
        return results;
    }
}
