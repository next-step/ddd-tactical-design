package kitchenpos.menu.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import kitchenpos.menu.application.MenuService;
import kitchenpos.menu.tobe.application.dto.ChangeMenuPriceRequest;
import kitchenpos.menu.tobe.application.dto.ChangeMenuPriceResponse;
import kitchenpos.menu.tobe.application.dto.CreateMenuRequest;
import kitchenpos.menu.tobe.application.dto.CreateMenuResponse;
import kitchenpos.menu.tobe.application.dto.DisplayMenuResponse;
import kitchenpos.menu.tobe.application.dto.HideMenuResponse;
import kitchenpos.menu.tobe.application.dto.QueryMenuResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/menus")
@RestController
public class MenuRestController {

    private final MenuService menuService;

    public MenuRestController(final MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<CreateMenuResponse> create(@Valid @RequestBody final CreateMenuRequest request) {
        final var response = menuService.create(request);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<ChangeMenuPriceResponse> changePrice(@PathVariable final UUID menuId, @RequestBody final ChangeMenuPriceRequest request) {
        return ResponseEntity.ok(menuService.changePrice(menuId, request));
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<DisplayMenuResponse> display(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(menuService.display(menuId));
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<HideMenuResponse> hide(@PathVariable final UUID menuId) {
        return ResponseEntity.ok(menuService.hide(menuId));
    }

    @GetMapping
    public ResponseEntity<List<QueryMenuResponse>> findAll() {
        return ResponseEntity.ok(menuService.findAll());
    }
}
