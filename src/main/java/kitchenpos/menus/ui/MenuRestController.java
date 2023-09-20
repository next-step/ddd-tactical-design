package kitchenpos.menus.ui;

import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.dto.MenuChangePriceRequest;
import kitchenpos.menus.dto.MenuCreateRequest;
import kitchenpos.menus.dto.MenuResponse;
import kitchenpos.menus.tobe.domain.menu.MenuId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/menus")
@RestController
public class MenuRestController {
    private final MenuService menuService;

    public MenuRestController(final MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<MenuResponse> create(@RequestBody final MenuCreateRequest request) {
        final MenuResponse response = menuService.create(request);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<MenuResponse> changePrice(@PathVariable final MenuId menuId, @RequestBody final MenuChangePriceRequest request) {
        MenuResponse response = menuService.changePrice(menuId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<MenuResponse> display(@PathVariable final MenuId menuId) {
        MenuResponse response = menuService.display(menuId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<MenuResponse> hide(@PathVariable final MenuId menuId) {
        MenuResponse response = menuService.hide(menuId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuResponse>> findAll() {
        List<MenuResponse> responses = menuService.findAll();
        return ResponseEntity.ok(responses);
    }
}
