package kitchenpos.menus.ui;

import kitchenpos.menus.application.MenuGroupService;
import kitchenpos.menus.shared.dto.MenuGroupDto;
import kitchenpos.menus.shared.dto.request.MenuGroupCreateRequest;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
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
    public ResponseEntity<MenuGroupDto> create(@RequestBody final MenuGroupCreateRequest request) {
        final MenuGroupDto response = menuGroupService.create(request);
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupDto>> findAll() {
        return ResponseEntity.ok(menuGroupService.findAll());
    }
}
