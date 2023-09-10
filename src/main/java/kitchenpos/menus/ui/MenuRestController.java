package kitchenpos.menus.ui;

import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.dto.MenuChangePriceRequest;
import kitchenpos.menus.dto.MenuCreateRequest;
import kitchenpos.menus.dto.MenuResponse;
import kitchenpos.menus.tobe.domain.Menu;
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
    public ResponseEntity<MenuResponse> create(@RequestBody final MenuCreateRequest request) {
        final Menu response = menuService.create(request);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getId()))
                .body(MenuResponse.fromEntity(response));
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<MenuResponse> changePrice(@PathVariable final UUID menuId, @RequestBody final MenuChangePriceRequest request) {
        Menu response = menuService.changePrice(menuId, request.getPrice());
        return ResponseEntity.ok(MenuResponse.fromEntity(response));
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<MenuResponse> display(@PathVariable final UUID menuId) {
        Menu response = menuService.display(menuId);
        return ResponseEntity.ok(MenuResponse.fromEntity(response));
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<MenuResponse> hide(@PathVariable final UUID menuId) {
        Menu response = menuService.hide(menuId);
        return ResponseEntity.ok(MenuResponse.fromEntity(response));
    }

    @GetMapping
    public ResponseEntity<List<MenuResponse>> findAll() {
        List<Menu> responses = menuService.findAll();
        return ResponseEntity.ok(MenuResponse.fromEntities(responses));
    }
}
