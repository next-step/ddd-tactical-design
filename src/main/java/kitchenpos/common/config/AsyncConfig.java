package kitchenpos.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(20);
        taskExecutor.setMaxPoolSize(50);
        taskExecutor.setQueueCapacity(200);

        taskExecutor.setThreadNamePrefix("async-task-");
        taskExecutor.setThreadGroupName("async-group");

        return taskExecutor;
    }
}
