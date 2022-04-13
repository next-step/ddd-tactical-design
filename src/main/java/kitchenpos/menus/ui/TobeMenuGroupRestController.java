package kitchenpos.menus.ui;

import kitchenpos.menus.application.TobeMenuGroupService;
import kitchenpos.menus.domain.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.dto.MenuGroupDto;
import kitchenpos.menus.dto.MenuGroupRegisterRequest;
import kitchenpos.menus.dto.MenuGroupRegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/menu-groups/tobe")
@RestController
public class TobeMenuGroupRestController {
    private final TobeMenuGroupService menuGroupService;

    public TobeMenuGroupRestController(final TobeMenuGroupService menuGroupService) {
        this.menuGroupService = menuGroupService;
    }

    @PostMapping
    public ResponseEntity<MenuGroupRegisterResponse> create(@RequestBody final MenuGroupRegisterRequest request) {
        final MenuGroupRegisterResponse response = menuGroupService.create(request);
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupDto>> findAll() {
        return ResponseEntity.ok(
                menuGroupService.findAll()
        );
    }
}
