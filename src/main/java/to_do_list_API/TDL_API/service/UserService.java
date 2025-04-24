package to_do_list_API.TDL_API.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import to_do_list_API.TDL_API.domain.User;
import to_do_list_API.TDL_API.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    final private UserRepository userRepository;

    public User Join(User user) {
        return userRepository.save(user);  // 전체 User 엔티티 저장
    }
    public Optional<User> login(User user){
        return userRepository.findByEmail(user.getEmail())
                .filter(m -> m.getPassword().equals(user.getPassword()));
    }
    public Optional<User> getUser(int userId){
        return userRepository.findByPid(userId);
    }
    public Optional<User> changeNickname(int userId, String nickname) {
        Optional<User> savedUser = userRepository.findByPid(userId);

        if (savedUser.isPresent()) {
            User existingUser = savedUser.get();
            existingUser.setNickname(nickname);
            return Optional.of(userRepository.save(existingUser)); // 저장 후 반환
        } else {
            throw new RuntimeException("User not found with pid: " + userId);
        }
    }
}
