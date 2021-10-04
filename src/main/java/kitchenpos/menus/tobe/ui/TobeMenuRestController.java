package kitchenpos.menus.tobe.ui;

import kitchenpos.menus.tobe.application.TobeMenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/tobe/api/menus")
@RestController
public class TobeMenuRestController {
    private final TobeMenuService menuService;

    public TobeMenuRestController(final TobeMenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<MenuForm> create(@RequestBody final MenuForm request) {
        final MenuForm response = menuService.create(request);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<MenuForm> changePrice(@PathVariable final UUID menuId, @RequestBody final MenuForm request) {
        return ResponseEntity.ok(menuService.changePrice(menuId, request));
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<MenuForm> display(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(menuService.display(menuId));
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<MenuForm> hide(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(menuService.hide(menuId));
    }

    @GetMapping
    public ResponseEntity<List<MenuForm>> findAll() {
        return ResponseEntity.ok(menuService.findAll());
    }
}
