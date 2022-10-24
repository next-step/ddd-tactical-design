package kitchenpos.menu.api;

import java.net.URI;
import java.util.List;
import kitchenpos.menu.application.MenuGroupService;
import kitchenpos.menu.tobe.domain.entity.MenuGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/menu-groups")
@RestController
public class MenuGroupRestController {
    private final MenuGroupService menuGroupService;

    public MenuGroupRestController(final MenuGroupService menuGroupService) {
        this.menuGroupService = menuGroupService;
    }

    @PostMapping
    public ResponseEntity<MenuGroup> create(@RequestBody final MenuGroup request) {
        final MenuGroup response = menuGroupService.create(request);
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.id))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroup>> findAll() {
        return ResponseEntity.ok(menuGroupService.findAll());
    }
}
