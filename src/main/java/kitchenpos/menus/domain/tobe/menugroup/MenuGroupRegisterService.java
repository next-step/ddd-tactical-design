package kitchenpos.menus.domain.tobe.menugroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MenuGroupRegisterService {
    private final TobeMenuGroupRepository menuGroupRepository;

    @Autowired
    public MenuGroupRegisterService(TobeMenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    public MenuGroup findMenuGroup(UUID menuGroupId) {
        return menuGroupRepository.findById(menuGroupId)
                .orElseThrow(() -> new MenuGroupNotFoundException("MenuGroup not found"));
    }
}
