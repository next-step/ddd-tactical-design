package kitchenpos.menus.tobe.v2.api;



import kitchenpos.menus.tobe.v2.application.MenuGroupService;
import kitchenpos.menus.tobe.v2.domain.MenuGroup;
import kitchenpos.menus.tobe.v2.dto.MenuGroupRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class MenuGroupApiController {
    private final MenuGroupService menuGroupBoService;

    public MenuGroupApiController(final MenuGroupService menuGroupBoService) {
        this.menuGroupBoService = menuGroupBoService;
    }

    @PostMapping("/api/v2/menu-groups")
    public ResponseEntity<MenuGroup> create(@RequestBody final MenuGroupRequestDto menuGroupRequestDto) {
        final MenuGroup created = menuGroupBoService.create(menuGroupRequestDto);
        final URI uri = URI.create("/api/menu-groups/" + created.getId());
        return ResponseEntity.created(uri)
                .body(created)
                ;
    }

    @GetMapping("/api/v2/menu-groups")
    public ResponseEntity<List<MenuGroup>> list() {
        return ResponseEntity.ok()
                .body(menuGroupBoService.list())
                ;
    }
}
