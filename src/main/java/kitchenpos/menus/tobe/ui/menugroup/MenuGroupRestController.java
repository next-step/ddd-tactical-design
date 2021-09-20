package kitchenpos.menus.tobe.ui.menugroup;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kitchenpos.menus.tobe.application.menugroup.MenuGroupService;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;

@RequestMapping("/api/menu-groups")
@RestController(value = "TobeMenuGroupRestController")
public class MenuGroupRestController {
	private final MenuGroupService menuGroupService;

	public MenuGroupRestController(MenuGroupService menuGroupService) {
		this.menuGroupService = menuGroupService;
	}

	@PostMapping
	public ResponseEntity<MenuGroup> create(@Valid @RequestBody MenuGroupCreateRequest request) {
		MenuGroup response = menuGroupService.create(request);
		return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
			.body(response);
	}

	@GetMapping
	public ResponseEntity<List<MenuGroup>> findAll() {
		return ResponseEntity.ok(menuGroupService.findAll());
	}
}
