package kitchenpos.menu.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.application.MenuQueryService;
import kitchenpos.menu.application.MenuService;
import kitchenpos.menu.application.dto.ChangeMenuPriceServiceRq;
import kitchenpos.menu.application.dto.MenuServiceRs;
import kitchenpos.menu.application.dto.SimpleMenuServiceRs;
import kitchenpos.menu.domain.model.MenuSummary;
import kitchenpos.menu.ui.dto.ChangeMenuPriceRq;
import kitchenpos.menu.ui.dto.CreateMenuRq;
import kitchenpos.menu.ui.dto.MenuRs;
import kitchenpos.menu.ui.dto.SimpleMenuRs;
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
    private final MenuQueryService menuQueryService;

    public MenuRestController(final MenuService menuService, MenuQueryService menuQueryService) {
        this.menuService = menuService;
        this.menuQueryService = menuQueryService;
    }

    @PostMapping
    public ResponseEntity<MenuRs> create(@RequestBody final CreateMenuRq request) {
        MenuServiceRs response = menuService.create(request.toServiceRq());
        return ResponseEntity.created(URI.create("/api/menus/" + response.getId()))
                .body(new MenuRs(response));
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<SimpleMenuRs> changePrice(@PathVariable("menuId") final UUID menuId,
                                                    @RequestBody final ChangeMenuPriceRq request) {
        SimpleMenuServiceRs response = menuService.changePrice(
                menuId,
                new ChangeMenuPriceServiceRq(request.getPrice())
        );
        return ResponseEntity.ok(new SimpleMenuRs(response));
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<SimpleMenuRs> display(@PathVariable("menuId") final UUID menuId) {
        SimpleMenuServiceRs response = menuService.display(menuId);
        return ResponseEntity.ok(new SimpleMenuRs(response));
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<SimpleMenuRs> hide(@PathVariable("menuId") final UUID menuId) {
        SimpleMenuServiceRs response = menuService.hide(menuId);
        return ResponseEntity.ok(new SimpleMenuRs(response));
    }

    @GetMapping
    public ResponseEntity<List<MenuSummary>> findAll() {
        return ResponseEntity.ok(menuQueryService.findAll());
    }
}
