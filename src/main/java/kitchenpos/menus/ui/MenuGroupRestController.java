package kitchenpos.menus.ui;

import kitchenpos.menus.application.MenuGroupService;
import kitchenpos.menus.application.dto.request.MenuGroupCreateRequest;
import kitchenpos.menus.application.dto.response.MenuGroupCreateResponse;
import kitchenpos.menus.application.dto.response.MenuGroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<MenuGroupCreateResponse> create(@RequestBody final MenuGroupCreateRequest request) {
        final MenuGroupCreateResponse response = menuGroupService.create(request);
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupResponse>> findAll() {
        return ResponseEntity.ok(menuGroupService.findAll());
    }
}
