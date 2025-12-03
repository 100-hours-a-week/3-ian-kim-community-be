package ktb3.full.community.service;

import ktb3.full.community.common.exception.*;
import ktb3.full.community.domain.entity.User;
import ktb3.full.community.dto.request.UserAccountUpdateRequest;
import ktb3.full.community.dto.request.UserPasswordUpdateRequest;
import ktb3.full.community.dto.request.UserRegisterRequest;
import ktb3.full.community.dto.response.UserAccountUpdateResponse;
import ktb3.full.community.dto.response.UserAccountResponse;
import ktb3.full.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public long register(UserRegisterRequest request) {
        validateEmailDuplication(request.getEmail());
        validateNicknameDuplication(request.getNickname());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return userRepository.save(request.toUserEntity(encodedPassword)).getId();
    }

    public UserAccountResponse getUserAccount(long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return UserAccountResponse.from(user);
    }

    @Transactional
    public UserAccountUpdateResponse updateAccount(long userId, UserAccountUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (request.getNickname() != null) {
            validateIsNicknameChanged(user.getNickname(), request.getNickname());
            validateNicknameDuplication(request.getNickname());
            user.updateNickname(request.getNickname());
        }

        if (request.getProfileImageName() != null) {
            user.updateProfileImageName(request.getProfileImageName());
        }

        return UserAccountUpdateResponse.from(user);
    }

    @Transactional
    public void updatePassword(long userId, UserPasswordUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        validateIsPasswordChanged(user.getPassword(), request.getPassword());
        user.updatePassword(passwordEncoder.encode(request.getPassword()));
    }

    private void validateEmailDuplication(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
    }

    private void validateNicknameDuplication(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new DuplicatedNicknameException();
        }
    }

    private void validateIsPasswordChanged(String oldEncodedPassword, String newRawPassword) {
        if (passwordEncoder.matches(newRawPassword, oldEncodedPassword)) {
            throw new CannotChangeSamePasswordException();
        }
    }

    private void validateIsNicknameChanged(String oldNickname, String newNickname) {
        if (oldNickname.equals(newNickname)) {
            throw new CannotChangeSameNicknameException();
        }
    }
}
