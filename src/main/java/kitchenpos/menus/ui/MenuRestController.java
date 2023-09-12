package kitchenpos.menus.ui;

import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.ui.request.MenuChangePriceRequest;
import kitchenpos.menus.ui.request.MenuCreateRequest;
import kitchenpos.menus.ui.response.MenuResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<MenuResponse> create(@Validated @RequestBody final MenuCreateRequest request) {
        Menu menu = menuService.create(request);
        MenuResponse menuResponse = MenuResponse.of(menu);
        return ResponseEntity.created(URI.create("/api/menus/" + menuResponse.getId()))
            .body(menuResponse);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<MenuResponse> changePrice(@PathVariable final UUID menuId, @Validated @RequestBody final MenuChangePriceRequest request) {
        Menu menu = menuService.changePrice(menuId, request);
        return ResponseEntity.ok(MenuResponse.of(menu));
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<MenuResponse> display(@PathVariable final UUID menuId) {
        Menu menu = menuService.display(menuId);
        return ResponseEntity.ok(MenuResponse.of(menu));
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<MenuResponse> hide(@PathVariable final UUID menuId) {
        Menu menu = menuService.hide(menuId);
        return ResponseEntity.ok(MenuResponse.of(menu));
    }

    @GetMapping
    public ResponseEntity<List<MenuResponse>> findAll() {
        List<MenuResponse> menuResponses = menuService.findAll()
                .stream()
                .map(MenuResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(menuResponses);
    }
}
