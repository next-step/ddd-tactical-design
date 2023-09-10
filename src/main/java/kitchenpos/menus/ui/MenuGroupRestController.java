package kitchenpos.menus.ui;

import kitchenpos.menus.application.MenuGroupService;
import kitchenpos.menus.dto.MenuGroupCreateRequest;
import kitchenpos.menus.dto.MenuGroupResponse;
import kitchenpos.menus.tobe.domain.MenuGroup;
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
        final MenuGroup response = menuGroupService.create(request.toEntity());
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
                .body(MenuGroupResponse.fromEntity(response));
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupResponse>> findAll() {
        List<MenuGroupResponse> responses = menuGroupService.findAll()
                .stream()
                .map(MenuGroupResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
        return ResponseEntity.ok(responses);
    }
}
