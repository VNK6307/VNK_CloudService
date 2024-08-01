package wind.laguna.mycloud5.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wind.laguna.mycloud5.entities.AuthRequest;
import wind.laguna.mycloud5.entities.UserInfo;
import wind.laguna.mycloud5.logout.BlackList;
import wind.laguna.mycloud5.responces.LoginResponse;
import wind.laguna.mycloud5.services.JwtService;
import wind.laguna.mycloud5.services.UserInfoService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserInfoService userInfoService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BlackList blackList;

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody AuthRequest authRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String jwtToken = jwtService.generateToken(authRequest.getUsername());
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setJwtToken(jwtToken);
            return ResponseEntity.ok(loginResponse);

        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    @PostMapping("/logout")
    public String logoutUser(HttpServletRequest request) {
        String authHeader = request.getHeader("auth-token");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            token = authHeader.substring(7);
        }
        blackList.blacklistToken(token);
        return "You have successfully logged out!";
    }
}
