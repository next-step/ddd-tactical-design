package kitchenpos.menus.ui;

import kitchenpos.menus.application.TobeMenuService;
import kitchenpos.menus.domain.tobe.domain.TobeMenu;
import kitchenpos.menus.domain.tobe.domain.vo.MenuId;
import kitchenpos.menus.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/menus")
@RestController
public class TobeMenuRestController {
    private final TobeMenuService menuService;

    public TobeMenuRestController(final TobeMenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<MenuRegisterResponse> create(@RequestBody final MenuRegisterRequest request) {
        final TobeMenu menu = menuService.create(request);
        final MenuRegisterResponse response = new MenuRegisterResponse(menu);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getMenuId()))
            .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<MenuPriceChangeResponse> changePrice(@PathVariable final MenuId menuId, @RequestBody final MenuPriceChangeRequest request) {
        request.setMenuId(menuId);
        final TobeMenu menu = menuService.changePrice(request);
        final MenuPriceChangeResponse response = new MenuPriceChangeResponse(menu);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<MenuDisplayResponse> display(@PathVariable final MenuDisplayRequest request) {
        final TobeMenu menu = menuService.display(request);
        final MenuDisplayResponse response = new MenuDisplayResponse(menu);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<MenuHideResponse> hide(@PathVariable final MenuHideRequest request) {
        final TobeMenu menu = menuService.hide(request);
        final MenuHideResponse response = new MenuHideResponse(menu);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuDto>> findAll() {
        return ResponseEntity.ok(menuService.findAll().stream().map(MenuDto::new).collect(Collectors.toList()));
    }
}
