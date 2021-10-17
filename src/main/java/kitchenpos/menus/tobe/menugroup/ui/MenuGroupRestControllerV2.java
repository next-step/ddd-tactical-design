package kitchenpos.menus.tobe.menugroup.ui;

import java.net.URI;
import java.util.List;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.menus.tobe.menugroup.application.MenuGroupServiceV2;
import kitchenpos.menus.tobe.menugroup.dto.MenuGroupResponse;
import kitchenpos.menus.tobe.menugroup.domain.model.MenuGroupV2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v2/menu-groups")
@RestController
public class MenuGroupRestControllerV2 {

    private final MenuGroupServiceV2 menuGroupServiceV2;

    public MenuGroupRestControllerV2(final MenuGroupServiceV2 menuGroupServiceV2) {
        this.menuGroupServiceV2 = menuGroupServiceV2;
    }

    @PostMapping
    public ResponseEntity<MenuGroupResponse> create(@RequestBody final DisplayedName displayedName) {
        final MenuGroupV2 menuGroupV2 = menuGroupServiceV2.create(displayedName);
        final MenuGroupResponse response = MenuGroupResponse.from(menuGroupV2);
        return ResponseEntity.created(URI.create("/api/v2/menu-groups/" + response.getId()))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupResponse>> findAll() {
        final List<MenuGroupV2> menuGroupV2s = menuGroupServiceV2.findAll();
        return ResponseEntity.ok(MenuGroupResponse.from(menuGroupV2s));
    }

}
