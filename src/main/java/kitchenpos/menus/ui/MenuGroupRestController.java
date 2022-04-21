package kitchenpos.menus.ui;

import kitchenpos.menus.application.MenuGroupService;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.ui.dto.MenuGroupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/menu-groups")
@RestController
public class MenuGroupRestController {
    private final MenuGroupService menuGroupService;

    public MenuGroupRestController(final MenuGroupService menuGroupService) {
        this.menuGroupService = menuGroupService;
    }

    @PostMapping
    public ResponseEntity<MenuGroup> create(@RequestBody final MenuGroupRequest request) {
        final MenuGroup response = menuGroupService.create(request);
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroup>> findAll() {
        return ResponseEntity.ok(menuGroupService.findAll());
    }
}
