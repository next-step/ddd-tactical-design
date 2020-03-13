package kitchenpos.menus.tobe.v2.api;

import kitchenpos.menus.tobe.v2.application.MenuService;
import kitchenpos.menus.tobe.v2.domain.Menu;
import kitchenpos.menus.tobe.v2.dto.MenuRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class MenuApiController {
    private final MenuService menuService;

    public MenuApiController(final MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/api/v2/menus")
    public ResponseEntity<Menu> create(@RequestBody final MenuRequestDto menuRequestDto) {
        final Menu created = menuService.create(menuRequestDto);
        final URI uri = URI.create("/api/menus/" + created.getId());
        return ResponseEntity.created(uri)
                .body(created)
                ;
    }

    @GetMapping("/api/v2/menus")
    public ResponseEntity<List<Menu>> list() {
        return ResponseEntity.ok()
                .body(menuService.list())
                ;
    }
}
