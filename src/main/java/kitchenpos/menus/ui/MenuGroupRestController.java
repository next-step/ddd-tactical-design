package kitchenpos.menus.ui;

import kitchenpos.menus.application.MenuGroupService;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.ui.request.MenuGroupCreateRequest;
import kitchenpos.menus.ui.response.MenuGroupResponse;
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
        MenuGroup menuGroup = menuGroupService.create(request);
        MenuGroupResponse response = MenuGroupResponse.of(menuGroup);
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupResponse>> findAll() {
        List<MenuGroupResponse> menuGroupResponses = menuGroupService.findAll()
                .stream()
                .map(MenuGroupResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(menuGroupResponses);
    }
}
