package ktb3.full.community.presentation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import ktb3.full.community.common.annotation.constraint.EmailPattern;
import ktb3.full.community.common.annotation.constraint.NicknamePattern;
import ktb3.full.community.dto.request.UserLoginRequest;
import ktb3.full.community.dto.request.UserRegisterRequest;
import ktb3.full.community.dto.response.ApiSuccessResponse;
import ktb3.full.community.dto.response.UserAccountResponse;
import ktb3.full.community.dto.response.UserProfileResponse;
import ktb3.full.community.dto.response.UserValidationResponse;
import ktb3.full.community.presentation.api.UserApi;
import ktb3.full.community.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Validated
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserApiController implements UserApi {

    private final UserService userService;

    @GetMapping("/email-validation")
    public ResponseEntity<ApiSuccessResponse<UserValidationResponse>> validateEmailAvailable(@EmailPattern @RequestParam("email") String email) {
        UserValidationResponse userValidationResponse = userService.validateEmailAvailable(email);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(userValidationResponse));
    }

    @GetMapping("/nickname-validation")
    public ResponseEntity<ApiSuccessResponse<UserValidationResponse>> validateNicknameAvailable(@NicknamePattern @RequestParam("nickname") String nickname) {
        UserValidationResponse userValidationResponse = userService.validateNicknameAvailable(nickname);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(userValidationResponse));
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<Void>> signUp(@Valid @ModelAttribute UserRegisterRequest userRegisterRequest) {
        long userId = userService.register(userRegisterRequest);
        return ResponseEntity.created(URI.create(String.format("/users/%d", userId)))
                .body(ApiSuccessResponse.getBaseResponse());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResponse<UserAccountResponse>> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        UserAccountResponse userAccountResponse = userService.login(userLoginRequest);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(userAccountResponse));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiSuccessResponse<UserProfileResponse>> getUserProfile(@Positive @PathVariable("userId") long userId) {
        UserProfileResponse userProfile = userService.getUserProfile(userId);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(userProfile));
    }
}
