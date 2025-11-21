package ktb3.full.community.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiErrorCode {

    // 400 Bad Request
    INVALID_INPUT("4001", "입력값이 올바르지 않습니다."),
    HTTP_MESSAGE_NOT_READABLE("4002", "요청 본문의 형식이 올바르지 않습니다."),
    MISSING_SERVLET_REQUEST_PARAMETER("4003", "필수 요청 파라미터가 누락되었습니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH("4004", "요청 파라미터의 타입이 올바르지 않습니다."),
    CONSTRAINT_VIOLATION("4005", "요청 파라미터의 값이 올바르지 않습니다."),
    INVALID_SORT_PROPERTY("4006", "정렬 기준이 올바르지 않습니다."),

    // 401 Unauthorized
    INVALID_CREDENTIALS("4011", "아이디 또는 비밀번호가 틀렸습니다."),
    LOGIN_REQUIRED("4012", "로그인이 필요합니다."),

    // 403 Forbidden
    NO_PERMISSION("4031", "접근 권한이 존재하지 않습니다."),

    // 404 Not Found
    USER_NOT_FOUND("4041", "회원을 찾을 수 없습니다."),
    POST_NOT_FOUND("4042", "게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND("4043", "댓글을 찾을 수 없습니다."),
    NO_RESOURCE_FOUND("4044", "요청 URL이 올바르지 않습니다."),

    // 405 Method Not Allowed
    METHOD_NOT_ALLOWED("4051", "지원하지 않는 요청 메서드입니다."),

    // 409 Conflict
    DUPLICATED_EMAIL("4091", "중복된 이메일입니다."),
    DUPLICATED_NICKNAME("4092", "중복된 닉네임입니다."),

    // 415 Unsupported Media Type
    UNSUPPORTED_MEDIA_TYPE("4151", "지원하지 않는 미디어 타입입니다."),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR("5001", "현재 서버에서 요청을 처리할 수 없습니다. 잠시 후 다시 시도해주세요."),
    IMAGE_UPLOAD_FAILED("5002", "이미지 업로드에 실패했습니다."),
    ;

    private final String code;
    private final String message;
}