package kitchenpos.menus.ui;

import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.ui.dto.MenuRequest;
import kitchenpos.menus.ui.dto.MenuResponse;
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
    public ResponseEntity<MenuResponse> create(@RequestBody final MenuRequest request) {
        final MenuResponse response = menuService.create(request);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<MenuResponse> changePrice(@PathVariable final UUID menuId, @RequestBody final MenuRequest request) {
        return ResponseEntity.ok(menuService.changePrice(menuId, request));
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<MenuResponse> display(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(menuService.display(menuId));
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<MenuResponse> hide(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(menuService.hide(menuId));
    }

    @GetMapping
    public ResponseEntity<List<MenuResponse>> findAll() {
        return ResponseEntity.ok(menuService.findAll());
    }
}
