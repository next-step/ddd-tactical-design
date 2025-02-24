package kitchenpos.product.tobe.infra

import java.util.*
import kitchenpos.product.tobe.domain.Product
import kitchenpos.product.tobe.domain.ProductRepository
import org.springframework.data.jpa.repository.JpaRepository

interface JpaProductRepository : ProductRepository, JpaRepository<Product, UUID>
