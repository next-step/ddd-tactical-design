package kitchenpos.menus.ui;

import kitchenpos.menus.application.*;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.ui.dto.ChangePriceRequest;
import kitchenpos.menus.ui.dto.CreateMenuRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequestMapping("/api/menus")
@RestController
public class MenuRestController {
    private final CreateMenuService createMenuService;
    private final ChangePriceService changePriceService;
    private final DisplayMenuService displayMenuService;
    private final HideMenuService hideMenuService;
    private final FindAllMenuService findAllMenuService;


    public MenuRestController(
        final CreateMenuService createMenuService,
        final ChangePriceService changePriceService,
        final DisplayMenuService displayMenuService,
        final HideMenuService hideMenuService,
        final FindAllMenuService findAllMenuService
    ) {
        this.createMenuService = createMenuService;
        this.changePriceService = changePriceService;
        this.displayMenuService = displayMenuService;
        this.hideMenuService = hideMenuService;
        this.findAllMenuService = findAllMenuService;
    }

    @PostMapping
    public ResponseEntity<Menu> create(@RequestBody final CreateMenuRequest request) {
        if (Objects.isNull(request.createMenuProductRequests) || request.createMenuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final Menu response = createMenuService.create(request);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<Menu> changePrice(@PathVariable final UUID menuId, @RequestBody final ChangePriceRequest request) {
        return ResponseEntity.ok(changePriceService.change(menuId, request));
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<Menu> display(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(displayMenuService.display(menuId));
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<Menu> hide(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(hideMenuService.hide(menuId));
    }

    @GetMapping
    public ResponseEntity<List<Menu>> findAll() {
        return ResponseEntity.ok(findAllMenuService.findAll());
    }
}
