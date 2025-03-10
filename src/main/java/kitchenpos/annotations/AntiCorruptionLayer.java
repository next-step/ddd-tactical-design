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
public @interface AntiCorruptionLayer {

    @AliasFor(annotation = Component.class)
    String value() default "";

    /**
     * 이 ACL의 역할에 대한 설명입니다.
     *
     * @return ACL 설명
     */
    String description() default "";

    /**
     * 이 ACL이 보호하려는 업스트림(외부) 바운디드 컨텍스트입니다.
     *
     * @return 업스트림 컨텍스트 이름
     */
    String upstreamContext() default "";

    /**
     * 이 ACL이 보호하는 다운스트림(내부) 바운디드 컨텍스트입니다.
     *
     * @return 다운스트림 컨텍스트 이름
     */
    String downstreamContext() default "";

    /**
     * ACL이 수행하는 변환이나 격리의 유형입니다. (예: MODEL_TRANSLATION, PROTOCOL_CONVERSION, FACADE 등)
     *
     * @return 변환 유형
     */
    String translationType() default "";
}
