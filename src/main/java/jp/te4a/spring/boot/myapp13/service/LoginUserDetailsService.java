package jp.te4a.spring.boot.myapp13.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.te4a.spring.boot.myapp13.bean.UserBean;
import jp.te4a.spring.boot.myapp13.repository.UserRepository;
import jp.te4a.spring.boot.myapp13.security.LoginUserDetails;

@Service
public class LoginUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override //認証（パスワード確認）のため、ユーザ名を指定してDBからユーザ情報取得
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserBean> opt = userRepository.findById(username);
        UserBean userBean = opt.orElseThrow(() -> new UsernameNotFoundException("The requested user is not found."));
        return new LoginUserDetails(userBean, true, true, true, getAuthorities(userBean));
    }
    private Collection<GrantedAuthority> getAuthorities(UserBean userBean) {
        /*List<GrantedAuthority> authList = null;
        if(管理者の条件) {
            authList = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER",
            "ROLE_OTHER");
            } else if(一般ユーザの条件) {
             authList = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_OTHER");
            } else {
                authList = AuthorityUtils.createAuthorityList("ROLE_OTHER");
            }
        }渡されたユーザ情報(userBean)から判断して、権限を切り替える処理を記述する*/

        return AuthorityUtils.createAuthorityList("ROLE_USER"); //権限を指定
    }
}
