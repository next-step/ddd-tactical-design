package kitchenpos.menus.tobe.ui.dto;

public class MenuGroupRequest {
    
    public static class Create {
        private String name;

        public Create(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
