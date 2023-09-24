package kitchenpos.menugroups.ui;

import kitchenpos.menugroups.application.MenuGroupService;
import kitchenpos.menugroups.application.dto.MenuGroupResponse;
import kitchenpos.menugroups.ui.dto.MenuGroupRestMapper;
import kitchenpos.menugroups.ui.dto.request.MenuGroupCreateRestRequest;
import kitchenpos.menugroups.ui.dto.response.MenuGroupRestResponse;
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
    public ResponseEntity<MenuGroupRestResponse> create(@RequestBody final MenuGroupCreateRestRequest request) {
        final MenuGroupResponse response = menuGroupService.create(MenuGroupRestMapper.toDto(request));
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
                .body(MenuGroupRestMapper.toRestDto(response));
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupRestResponse>> findAll() {
        List<MenuGroupResponse> responses = menuGroupService.findAll();
        return ResponseEntity.ok(MenuGroupRestMapper.toRestDtos(responses));
    }
}
