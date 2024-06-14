package fixtures;

import org.springframework.stereotype.Component;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

@Component
public class IntegrationTestDataExecutionListener implements TestExecutionListener {


    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {

    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {

    }
}
