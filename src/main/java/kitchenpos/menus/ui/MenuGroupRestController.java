package kitchenpos.menus.ui;

import kitchenpos.menus.application.CreateMenuGroupService;
import kitchenpos.menus.application.FindAllMenuGroupService;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.ui.dto.CreateMenuGroupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/menu-groups")
@RestController
public class MenuGroupRestController {
    private final CreateMenuGroupService createMenuGroupService;

    private final FindAllMenuGroupService findAllMenuGroupService;

    public MenuGroupRestController(
        CreateMenuGroupService createMenuGroupService,
        FindAllMenuGroupService findAllMenuGroupService
    ) {
        this.createMenuGroupService = createMenuGroupService;
        this.findAllMenuGroupService = findAllMenuGroupService;
    }

    @PostMapping
    public ResponseEntity<MenuGroup> create(@RequestBody final CreateMenuGroupRequest request) {
        final MenuGroup response = createMenuGroupService.create(request);
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroup>> findAll() {
        return ResponseEntity.ok(findAllMenuGroupService.findAll());
    }
}
