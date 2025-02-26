package kitchenpos.menu.ui;

import java.net.URI;
import kitchenpos.menu.application.MenuGroupService;
import kitchenpos.menu.application.dto.CreateMenuGroupServiceRq;
import kitchenpos.menu.application.dto.MenuGroupServiceRs;
import kitchenpos.menu.ui.dto.CreateMenuGroupRq;
import kitchenpos.menu.ui.dto.MenuGroupRs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/menu-groups")
@RestController
public class MenuGroupRestController {
    private final MenuGroupService menuGroupService;

    public MenuGroupRestController(final MenuGroupService menuGroupService) {
        this.menuGroupService = menuGroupService;
    }

    @PostMapping
    public ResponseEntity<MenuGroupRs> create(@RequestBody final CreateMenuGroupRq request) {
        MenuGroupServiceRs response = menuGroupService.create(
                new CreateMenuGroupServiceRq(request.getName()));
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
                .body(new MenuGroupRs(response));
    }

//    @GetMapping
//    public ResponseEntity<List<MenuGroupRs>> findAll() {
//        return ResponseEntity.ok(
//                menuGroupService.findAll().stream()
//                        .map(MenuGroupRs::new)
//                        .toList()
//        );
//    }
}
