package practice.security.config.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티 session 을 만들어 준다. (Security ContextHolder)
// security session 에 들어 갈 수 있는 오브젝트가 정해져 있다.
// Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 됨.
// User 오브젝트 타입 -> UserDetails 타입 객체여야 한다.

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import practice.security.model.User;

import java.util.ArrayList;
import java.util.Collection;

// security session -> Authentication -> UserDetails
public class PrincipalDetails implements UserDetails {

    private User user; // composition

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 User 의 권한을 리턴하는 곳!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        // 우리 사이트에서 1년동안 회원이 로그인을 안하면 휴면계정으로 전환
        // 현재시간 - 로긴시간 -> 1년을 초과하면 return false;로 하면 된다.
        // 지금은 구현 안함.

        return true;
    }
}
