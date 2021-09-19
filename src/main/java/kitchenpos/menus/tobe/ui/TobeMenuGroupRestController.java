package kitchenpos.menus.tobe.ui;

import kitchenpos.menus.tobe.application.TobeMenuGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/tobe/api/menu-groups")
@RestController
public class TobeMenuGroupRestController {
    private final TobeMenuGroupService menuGroupService;

    public TobeMenuGroupRestController(final TobeMenuGroupService menuGroupService) {
        this.menuGroupService = menuGroupService;
    }

    @PostMapping
    public ResponseEntity<MenuGroupForm> create(@RequestBody final MenuGroupForm request) {
        final MenuGroupForm response = menuGroupService.create(request);
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupForm>> findAll() {
        return ResponseEntity.ok(menuGroupService.findAll());
    }
}
