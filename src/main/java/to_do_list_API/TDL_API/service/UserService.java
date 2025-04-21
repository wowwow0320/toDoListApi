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
                .filter(m -> m.getPw().equals(user.getPw()));
    }
    public Optional<User> getUser(int userId){
        return userRepository.findByPid(userId);
    }
}
