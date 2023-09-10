package kitchenpos.common;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * 도메인 서비스를 나타내는 어노테이션
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface DomainService {
}
