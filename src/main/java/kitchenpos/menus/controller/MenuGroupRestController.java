package kitchenpos.menus.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.menus.bo.MenuGroupBo;
import kitchenpos.menus.model.MenuGroupCreateRequest;
import kitchenpos.menus.model.MenuGroupView;
import kitchenpos.menus.tobe.domain.MenuGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuGroupRestController {

    private final MenuGroupBo menuGroupBo;

    public MenuGroupRestController(final MenuGroupBo menuGroupBo) {
        this.menuGroupBo = menuGroupBo;
    }

    @PostMapping("/api/menu-groups")
    public ResponseEntity<MenuGroupView> create(@RequestBody final MenuGroupCreateRequest request) {
        final MenuGroup created = menuGroupBo.create(request);
        final URI uri = URI.create("/api/menu-groups/" + created.getId());
        return ResponseEntity.created(uri)
            .body(map(created))
            ;
    }

    @GetMapping("/api/menu-groups")
    public ResponseEntity<List<MenuGroupView>> list() {
        return ResponseEntity.ok()
            .body(
                menuGroupBo.list().stream()
                    .map(this::map)
                    .collect(Collectors.toList())
            )
            ;
    }

    private MenuGroupView map(MenuGroup menuGroup) {
        return MenuGroupView.MenuGroupViewBuilder.builder()
            .withId(menuGroup.getId())
            .withName(menuGroup.getName())
            .build();
    }
}
