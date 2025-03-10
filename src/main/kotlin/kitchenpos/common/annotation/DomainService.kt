package kitchenpos.common.annotation

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Service


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Service
annotation class DomainService(
    @get:AliasFor(annotation = Service::class)
    val value: String = ""
)
