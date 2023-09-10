package kitchenpos.common.exception;

public enum KitchenPosExceptionType implements KitchenPosThrowable {

    BAD_REQUEST( "은(는) 잘못된 요청입니다."),
    METHOD_NOT_ALLOWED( "은(는) 허용되지 않은 기능입니다."),
    NOT_FOUND( "을(를) 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR( "은(는) 서버 내부 에러입니다."),
    ;

    private final String message;

    KitchenPosExceptionType(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
