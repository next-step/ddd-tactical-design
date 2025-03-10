package kitchenpos.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface OpenHostService {

    @AliasFor(annotation = Component.class)
    String value() default "";

    /**
     * 이 서비스가 제공하는 기능에 대한 설명입니다.
     *
     * @return 서비스 설명
     */
    String description() default "";

    /**
     * 이 서비스를 사용하는 다운스트림(사용자) 바운디드 컨텍스트들입니다.
     *
     * @return 다운스트림 컨텍스트 목록
     */
    String[] downstreamContexts() default {};
}
