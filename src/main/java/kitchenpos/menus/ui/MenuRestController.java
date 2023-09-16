package kitchenpos.menus.ui;

import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.application.dto.MenuPriceChangeRequest;
import kitchenpos.menus.application.dto.MenuResponse;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.application.dto.MenuCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/api/menus")
@RestController
public class MenuRestController {
    private final MenuService menuService;

    public MenuRestController(final MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<MenuResponse> create(@RequestBody final MenuCreateRequest request) {
        final Menu menu = menuService.create(request);
        return ResponseEntity.created(URI.create("/api/menus/" + menu.getId()))
            .body(MenuResponse.from(menu));
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<MenuResponse> changePrice(@PathVariable final UUID menuId, @RequestBody final MenuPriceChangeRequest request) {
        Menu menu = menuService.changePrice(menuId, request);
        return ResponseEntity.ok(MenuResponse.from(menu));
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<MenuResponse> display(@PathVariable final UUID menuId) {
        Menu menu = menuService.display(menuId);
        return ResponseEntity.ok(MenuResponse.from(menu));
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<MenuResponse> hide(@PathVariable final UUID menuId) {
        Menu menu = menuService.hide(menuId);
        return ResponseEntity.ok(MenuResponse.from(menu));
    }

    @GetMapping
    public ResponseEntity<List<MenuResponse>> findAll() {
        List<Menu> menus = menuService.findAll();
        List<MenuResponse> responses = menus.stream()
                .map(MenuResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
