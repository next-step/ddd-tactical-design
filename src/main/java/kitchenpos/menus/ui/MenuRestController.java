package kitchenpos.menus.ui;

import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.model.MenuModel;
import kitchenpos.menus.model.MenuRequest;
import kitchenpos.menus.domain.Menu;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/menus")
@RestController
public class MenuRestController {
    private final MenuService menuService;

    public MenuRestController(final MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<MenuModel> create(@RequestBody final MenuRequest request) {
        final MenuModel response = menuService.create(request.price(), request.menuGroupId(), request.name(), request.isDisplayed(), request.menuProductRequests());
        return ResponseEntity.created(URI.create("/api/menus/" + response.id()))
            .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<MenuModel> changePrice(@PathVariable final UUID menuId, @RequestBody final Menu request) {
        return ResponseEntity.ok(menuService.changePrice(menuId, request.priceValue()));
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<MenuModel> display(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(menuService.display(menuId));
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<MenuModel> hide(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(menuService.hide(menuId));
    }

    @GetMapping
    public ResponseEntity<List<Menu>> findAll() {
        return ResponseEntity.ok(menuService.findAll());
    }
}
