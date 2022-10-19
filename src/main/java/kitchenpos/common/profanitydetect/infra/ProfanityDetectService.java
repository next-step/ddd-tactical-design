package kitchenpos.common.profanitydetect.infra;

public interface ProfanityDetectService {

    /**
     * 대상 문자열이 비속어를 포함하는지 검사한다.
     *
     * @param text 대상 문자열
     * @return 대상 문자열이 비속어를 포함하면 `true`, 포함하지 않으면 `false`.
     */
    boolean profanityIn(String text);
}
