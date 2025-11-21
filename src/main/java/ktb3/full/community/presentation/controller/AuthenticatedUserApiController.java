package ktb3.full.community.presentation.controller;

import jakarta.validation.Valid;
import ktb3.full.community.common.annotation.resolver.Authentication;
import ktb3.full.community.dto.request.UserAccountUpdateRequest;
import ktb3.full.community.dto.request.UserPasswordUpdateRequest;
import ktb3.full.community.dto.response.ApiSuccessResponse;
import ktb3.full.community.dto.response.UserAccountResponse;
import ktb3.full.community.presentation.api.AuthenticatedUserApi;
import ktb3.full.community.service.UserDeleteService;
import ktb3.full.community.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class AuthenticatedUserApiController implements AuthenticatedUserApi {

    private final UserService userService;
    private final UserDeleteService userDeleteService;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<UserAccountResponse>> getUserAccount(@Authentication Long loggedInUserId) {
        UserAccountResponse userAccountResponse = userService.getUserAccount(loggedInUserId);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(userAccountResponse));
    }

    @PatchMapping
    public ResponseEntity<ApiSuccessResponse<UserAccountResponse>> updateUserAccount(
            @Authentication Long loggedInUserId,
            @Valid @ModelAttribute UserAccountUpdateRequest userAccountUpdateRequest) {
        UserAccountResponse userAccountResponse = userService.updateAccount(loggedInUserId, userAccountUpdateRequest);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(userAccountResponse));
    }

    @PatchMapping("/password")
    public ResponseEntity<ApiSuccessResponse<Void>> updatePassword(
            @Authentication Long loggedInUserId,
            @Valid @RequestBody UserPasswordUpdateRequest userPasswordUpdateRequest) {
        userService.updatePassword(loggedInUserId, userPasswordUpdateRequest);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.getBaseResponse());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiSuccessResponse<Void>> logout() {
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.getBaseResponse());
    }

    @DeleteMapping
    public ResponseEntity<ApiSuccessResponse<Void>> deleteUserAccount(@Authentication Long loggedInUserId) {
        userDeleteService.deleteAccount(loggedInUserId);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.getBaseResponse());
    }
}
