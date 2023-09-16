package kitchenpos.menus.ui;

import kitchenpos.menus.application.MenuChangePriceRequest;
import kitchenpos.menus.application.MenuCreateRequest;
import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.tobe.domain.NewMenu;
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
    public ResponseEntity<NewMenu> create(@RequestBody final MenuCreateRequest request) {
        final NewMenu response = menuService.create(request);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<NewMenu> changePrice(@PathVariable final UUID menuId, @RequestBody final MenuChangePriceRequest request) {
        return ResponseEntity.ok(menuService.changePrice(menuId, request));
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<NewMenu> display(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(menuService.display(menuId));
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<NewMenu> hide(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(menuService.hide(menuId));
    }

    @GetMapping
    public ResponseEntity<List<NewMenu>> findAll() {
        return ResponseEntity.ok(menuService.findAll());
    }
}
