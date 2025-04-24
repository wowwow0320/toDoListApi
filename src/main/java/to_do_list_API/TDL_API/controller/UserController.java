package to_do_list_API.TDL_API.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import to_do_list_API.TDL_API.SessionConst;
import to_do_list_API.TDL_API.domain.User;
import to_do_list_API.TDL_API.dto.UserNicknameDto;
import to_do_list_API.TDL_API.service.UserService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;

    @PostMapping("/join")
    public ResponseEntity<User> join(@RequestBody User user){
        User savedUser = userService.Join(user);
        return ResponseEntity.ok(savedUser);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user, HttpServletRequest request){
        Optional<User> loginUser = userService.login(user);  // 로그인 시도

        if (loginUser.isPresent()) {
            HttpSession session = request.getSession(true); // 세션 생성
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser.get()); // 사용자 저장
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호 불일치");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 있으면 가져오고, 없으면 null
        if (session != null) {
            session.invalidate(); // 세션 무효화 (로그아웃 처리)
        }
        return ResponseEntity.ok("로그아웃 성공");
    }

    @GetMapping("/myPage")
    public ResponseEntity<?> myInformation(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user){
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다.");
        }
        int userId = user.getPid();
        Optional<User> savedUser = userService.getUser(userId);
        if (savedUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 회원의 정보를 불러오는데 실패하였습니다.");
        }
        return ResponseEntity.ok(savedUser.get());
    }
    @PostMapping("/changeNickname")
    public ResponseEntity<?> changeNickname(@RequestBody UserNicknameDto nicknameDto
            ,@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User user){
        Optional<User> changeNickname = userService.changeNickname(user.getPid(), nicknameDto.getNickname());
        if(changeNickname.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 회원의 정보를 불러오는데 실패하였습니다.");
        }
        return ResponseEntity.ok(changeNickname);
    }


}
