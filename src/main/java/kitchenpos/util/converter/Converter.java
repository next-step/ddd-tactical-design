package kitchenpos.util.converter;

public interface Converter<S, D> {

    D convert(final S source);
}
