package kitchenpos.menus.ui;

import kitchenpos.menus.application.TobeMenuService;
import kitchenpos.menus.domain.tobe.domain.vo.MenuId;
import kitchenpos.menus.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/menus")
@RestController
public class TobeMenuRestController {
    private final TobeMenuService menuService;

    public TobeMenuRestController(final TobeMenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<MenuRegisterResponse> create(@RequestBody final MenuRegisterRequest request) {
        final MenuRegisterResponse response = menuService.create(request);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getMenuId()))
                .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<MenuPriceChangeResponse> changePrice(@PathVariable final MenuId menuId, @RequestBody final MenuPriceChangeRequest request) {
        request.setMenuId(menuId);
        final MenuPriceChangeResponse response = menuService.changePrice(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<MenuDisplayResponse> display(@PathVariable final MenuDisplayRequest request) {
        final MenuDisplayResponse response = menuService.display(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<MenuHideResponse> hide(@PathVariable final MenuHideRequest request) {
        final MenuHideResponse response = menuService.hide(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuDto>> findAll() {
        return ResponseEntity.ok(menuService.findAll());
    }
}
