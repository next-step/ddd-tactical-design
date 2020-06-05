package kitchenpos.menus.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.menus.bo.MenuBo;
import kitchenpos.menus.model.MenuCreateRequest;
import kitchenpos.menus.model.MenuView;
import kitchenpos.menus.model.MenuView.MenuProductView;
import kitchenpos.menus.tobe.domain.menu.Menu;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuRestController {

    private final MenuBo menuBo;

    public MenuRestController(final MenuBo menuBo) {
        this.menuBo = menuBo;
    }

    @PostMapping("/api/menus")
    public ResponseEntity<MenuView> create(@RequestBody final MenuCreateRequest request) {
        final Menu created = menuBo.create(request);
        final URI uri = URI.create("/api/menus/" + created.getId());
        return ResponseEntity.created(uri)
            .body(map(created))
            ;
    }

    @GetMapping("/api/menus")
    public ResponseEntity<List<MenuView>> list() {
        return ResponseEntity.ok()
            .body(
                menuBo.list().stream()
                    .map(menu -> map(menu))
                    .collect(Collectors.toList())
            )
            ;
    }


    private MenuView map(Menu menu) {
        List<MenuView.MenuProductView> menuProductViews = menu.getMenuProducts().getMenuProducts()
            .stream()
            .map(
                menuProduct -> MenuProductView.Builder.builder()
                    .withProductId(menuProduct.getProductId())
                    .withQuantity(menuProduct.getQuantity())
                    .build()
            )
            .collect(Collectors.toList());

        return MenuView.Builder.builder()
            .withId(menu.getId())
            .withName(menu.getName())
            .withPrice(menu.getPrice().getValue())
            .withMenuGroupId(menu.getMenuGroupId())
            .withMenuProducts(menuProductViews)
            .build();


    }
}
