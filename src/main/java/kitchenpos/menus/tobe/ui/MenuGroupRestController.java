package kitchenpos.menus.tobe.ui;

import kitchenpos.menus.tobe.application.MenuGroupService;
import kitchenpos.menus.tobe.dto.CreateMenuGroupRequest;
import kitchenpos.menus.tobe.dto.MenuGroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/tobe/menu-groups")
@RestController("TobeMenuGroupRestController")
public class MenuGroupRestController {
    private final MenuGroupService menuGroupService;

    public MenuGroupRestController(final MenuGroupService menuGroupService) {
        this.menuGroupService = menuGroupService;
    }

    @PostMapping
    public ResponseEntity<MenuGroupResponse> create(@RequestBody final CreateMenuGroupRequest request) {
        final MenuGroupResponse response = menuGroupService.create(request);
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupResponse>> findAll() {
        return ResponseEntity.ok(menuGroupService.findAll());
    }
}
