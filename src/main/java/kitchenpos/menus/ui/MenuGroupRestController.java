package kitchenpos.menus.ui;

import kitchenpos.menus.application.MenuGroupService;
import kitchenpos.menus.model.MenuGroupModel;
import kitchenpos.menus.model.MenuGroupRequest;
import kitchenpos.menus.tobe.domain.MenuGroup;
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
    public ResponseEntity<MenuGroupModel> create(@RequestBody final MenuGroupRequest request) {
        final MenuGroupModel response = menuGroupService.create(request.name());
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.id()))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroup>> findAll() {
        return ResponseEntity.ok(menuGroupService.findAll());
    }
}
