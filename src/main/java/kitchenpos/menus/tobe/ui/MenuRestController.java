package kitchenpos.menus.tobe.ui;

import kitchenpos.menus.tobe.application.MenuService;
import kitchenpos.menus.tobe.dto.ChangeMenuPriceRequest;
import kitchenpos.menus.tobe.dto.CreateMenuRequest;
import kitchenpos.menus.tobe.dto.MenuResponse;
import kitchenpos.menus.tobe.dto.UpdateMenuStatusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/tobe/menus")
@RestController("TobeMenuRestController")
public class MenuRestController {
    private final MenuService menuService;

    public MenuRestController(final MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<MenuResponse> create(@RequestBody final CreateMenuRequest request) {
        final MenuResponse response = menuService.create(request);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<MenuResponse> changePrice(@PathVariable final UUID menuId, @RequestBody final ChangeMenuPriceRequest request) {
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

    @PutMapping("/status")
    public ResponseEntity<MenuResponse> updateStatus(@RequestBody final UpdateMenuStatusRequest request) {
        final UUID productId = request.getProductId();
        menuService.updateStatus(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<MenuResponse>> findAll() {
        return ResponseEntity.ok(menuService.findAll());
    }

    @PostMapping("/filtered")
    public ResponseEntity<List<MenuResponse>> findAllByIdIn(@RequestBody final List<UUID> menuIds) {
        return ResponseEntity.ok(menuService.findAllByIdIn(menuIds));
    }
}
