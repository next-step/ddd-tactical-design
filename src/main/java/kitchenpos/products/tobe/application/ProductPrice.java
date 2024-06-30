package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.Money;

import java.util.UUID;

public record ProductPrice(UUID productId, Money money) {
}
