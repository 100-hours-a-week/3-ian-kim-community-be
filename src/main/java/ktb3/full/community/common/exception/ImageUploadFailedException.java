package ktb3.full.community.common.exception;

import ktb3.full.community.common.exception.base.InternalServerErrorException;

public class ImageUploadFailedException extends InternalServerErrorException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.IMAGE_UPLOAD_FAILED;
    }
}
