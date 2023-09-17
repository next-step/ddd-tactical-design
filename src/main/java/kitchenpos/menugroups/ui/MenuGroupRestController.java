package kitchenpos.menugroups.ui;

import kitchenpos.menugroups.application.MenuGroupService;
import kitchenpos.menugroups.domain.MenuGroup;
import kitchenpos.menugroups.dto.MenuGroupCreateRequest;
import kitchenpos.menugroups.dto.MenuGroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/menu-groups")
@RestController
public class MenuGroupRestController {
    private final MenuGroupService menuGroupService;

    public MenuGroupRestController(final MenuGroupService menuGroupService) {
        this.menuGroupService = menuGroupService;
    }

    @PostMapping
    public ResponseEntity<MenuGroupResponse> create(@RequestBody final MenuGroupCreateRequest request) {
        final MenuGroupResponse response = menuGroupService.create(request.toEntity());
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupResponse>> findAll() {
        List<MenuGroupResponse> responses = menuGroupService.findAll();
        return ResponseEntity.ok(responses);
    }
}
