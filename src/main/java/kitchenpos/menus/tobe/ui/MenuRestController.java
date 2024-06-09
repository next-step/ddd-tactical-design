package kitchenpos.menus.tobe.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.application.MenuCommandHandler;
import kitchenpos.menus.tobe.application.query.MenuConverter;
import kitchenpos.menus.tobe.application.query.MenuQueryHandler;
import kitchenpos.menus.tobe.application.query.result.MenuQueryResult;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.dto.MenuChangePriceDto;
import kitchenpos.menus.tobe.dto.MenuCreateDto;
import kitchenpos.menus.tobe.ui.view.MenuViewModel;
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
    private final MenuCommandHandler menuCommandHandler;
    private final MenuQueryHandler menuQueryHandler;
    private final MenuConverter menuConverter;

    public MenuRestController(MenuCommandHandler menuCommandHandler, MenuQueryHandler menuQueryHandler,
                              MenuConverter menuConverter) {
        this.menuCommandHandler = menuCommandHandler;
        this.menuQueryHandler = menuQueryHandler;
        this.menuConverter = menuConverter;
    }

    @PostMapping
    public ResponseEntity<MenuViewModel> create(@RequestBody final MenuCreateDto request) {
        final Menu menu = menuCommandHandler.createMenu(request);
        final MenuViewModel response = MenuViewModel.from(menu);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getId()))
                             .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<MenuViewModel> changePrice(@PathVariable final UUID menuId, @RequestBody final MenuChangePriceDto request) {
        Menu menu = menuCommandHandler.changePrice(menuId, request);
        final MenuViewModel response = MenuViewModel.from(menu);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<MenuViewModel> display(@PathVariable final UUID menuId) {
        Menu menu = menuCommandHandler.display(menuId);
        final MenuViewModel response = MenuViewModel.from(menu);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<MenuViewModel> hide(@PathVariable final UUID menuId) {
        Menu menu = menuCommandHandler.hide(menuId);
        MenuViewModel response = MenuViewModel.from(menu);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuViewModel>> findAll() {
        List<MenuQueryResult> menus = menuQueryHandler.findAllMenu();
        List<MenuViewModel> response = menuConverter.convert(menus);
        return ResponseEntity.ok(response);
    }
}
