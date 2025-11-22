package ktb3.full.community.common.exception;

import ktb3.full.community.common.exception.base.BadRequestException;

public class CannotChangeSamePasswordException extends BadRequestException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.CANNOT_CHANGE_SAME_PASSWORD;
    }
}
