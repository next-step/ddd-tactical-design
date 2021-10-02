package kitchenpos.menus.tobe.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.application.MenuServiceV2;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuPrice;
import kitchenpos.menus.tobe.dto.MenuRequest;
import kitchenpos.menus.tobe.dto.MenuResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v2/menus")
@RestController
public class MenuRestControllerV2 {
    private final MenuServiceV2 menuServiceV2;

    public MenuRestControllerV2(final MenuServiceV2 menuServiceV2) {
        this.menuServiceV2 = menuServiceV2;
    }

    @PostMapping
    public ResponseEntity<MenuResponse> create(@RequestBody final MenuRequest request) {
        final Menu menu = menuServiceV2.create(request);
        final MenuResponse response = MenuResponse.from(menu);
        return ResponseEntity.created(URI.create("/api/menus/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{menuId}/price")
    public ResponseEntity<MenuResponse> changePrice(@PathVariable final UUID menuId, @RequestBody final MenuPrice menuPrice) {
        final Menu menu = menuServiceV2.changePrice(menuId, menuPrice);
        final MenuResponse response = MenuResponse.from(menu);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{menuId}/display")
    public ResponseEntity<MenuResponse> display(@PathVariable final UUID menuId) {
        final Menu menu = menuServiceV2.display(menuId);
        final MenuResponse response = MenuResponse.from(menu);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{menuId}/hide")
    public ResponseEntity<MenuResponse> hide(@PathVariable final UUID menuId) {
        final Menu menu = menuServiceV2.hide(menuId);
        final MenuResponse response = MenuResponse.from(menu);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuResponse>> findAll() {
        final List<Menu> menus = menuServiceV2.findAll();
        final List<MenuResponse> response = MenuResponse.from(menus);
        return ResponseEntity.ok(response);
    }
}
