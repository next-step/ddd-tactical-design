package kitchenpos.commons.tobe.domain.service;

@FunctionalInterface
public interface Policy<T> {

    void enforce(T t);
}
