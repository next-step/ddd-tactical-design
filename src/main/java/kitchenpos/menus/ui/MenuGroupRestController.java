package kitchenpos.menus.ui;

import kitchenpos.menus.tobe.application.ToBeMenuGroupService;
import kitchenpos.menus.tobe.domain.ToBeMenuGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/menu-groups")
@RestController
public class MenuGroupRestController {
    private final ToBeMenuGroupService menuGroupService;

    public MenuGroupRestController(final ToBeMenuGroupService menuGroupService) {
        this.menuGroupService = menuGroupService;
    }

    @PostMapping
    public ResponseEntity<ToBeMenuGroup> create(@RequestBody final ToBeMenuGroup request) {
        final ToBeMenuGroup response = menuGroupService.create(request);
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ToBeMenuGroup>> findAll() {
        return ResponseEntity.ok(menuGroupService.findAll());
    }
}
