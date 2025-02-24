package kitchenpos.menus.ui.tobe;


import kitchenpos.menus.application.tobe.MenuService;
import kitchenpos.menus.ui.dto.MenuChangePriceRequest;
import kitchenpos.menus.ui.dto.MenuChangePriceResponse;
import kitchenpos.menus.ui.dto.MenuCreateRequest;
import kitchenpos.menus.ui.dto.MenuCreateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/api/menus")
@RestController
public class MenuRestController {
    private final MenuService menuService;

    public MenuRestController(final MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<MenuCreateResponse> create(@RequestBody final MenuCreateRequest request) {
        final MenuCreateResponse response = menuService.create(request);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<MenuChangePriceResponse> changePrice(@RequestBody final MenuChangePriceRequest request) {
        return ResponseEntity.ok(menuService.changePrice(request));
    }
/*
    @PutMapping("/{menuId}/display")
    public ResponseEntity<Menu> display(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(menuService.display(menuId));
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<Menu> hide(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(menuService.hide(menuId));
    }

    @GetMapping
    public ResponseEntity<List<Menu>> findAll() {
        return ResponseEntity.ok(menuService.findAll());
    }*/
}
