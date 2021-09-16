package kitchenpos.menugroups.ui;

import kitchenpos.menugroups.application.MenuGroupService;
import kitchenpos.menugroups.dto.CreateMenuGroupRequest;
import kitchenpos.menugroups.dto.MenuGroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{menuGroupId}")
    public ResponseEntity<MenuGroupResponse> findById(@PathVariable final UUID menuGroupId) {
        return ResponseEntity.ok(menuGroupService.findById(menuGroupId));
    }
}
