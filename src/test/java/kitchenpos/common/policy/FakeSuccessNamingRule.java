package kitchenpos.common.policy;

public class FakeSuccessNamingRule implements NamingRule {
    @Override
    public boolean checkRule(String name) {
        return true;
    }
}
