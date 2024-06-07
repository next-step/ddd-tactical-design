package kitchenpos.menus.tobe.ui;

import kitchenpos.menus.tobe.application.MenuQueryHandler;
import kitchenpos.menus.tobe.application.MenuCommandHandler;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.dto.MenuChangePriceDto;
import kitchenpos.menus.tobe.dto.MenuCreateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/menus")
@RestController
public class MenuRestController {
    private final MenuCommandHandler menuCommandHandler;
    private final MenuQueryHandler menuQueryHandler;

    public MenuRestController(MenuCommandHandler menuCommandHandler, MenuQueryHandler menuQueryHandler) {
        this.menuCommandHandler = menuCommandHandler;
        this.menuQueryHandler = menuQueryHandler;
    }

    @PostMapping
    public ResponseEntity<Menu> create(@RequestBody final MenuCreateDto request) {
        final Menu response = menuCommandHandler.create(request);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getId()))
                             .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<Menu> changePrice(@PathVariable final UUID menuId, @RequestBody final MenuChangePriceDto request) {
        return ResponseEntity.ok(menuCommandHandler.changePrice(menuId, request));
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<Menu> display(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(menuCommandHandler.display(menuId));
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<Menu> hide(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(menuCommandHandler.hide(menuId));
    }

    @GetMapping
    public ResponseEntity<List<Menu>> findAll() {
        return ResponseEntity.ok(menuQueryHandler.findAll());
    }
}
