package jp.te4a.spring.boot.myapp13.security;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import jp.te4a.spring.boot.myapp13.bean.UserBean;
import lombok.Data;

@Data
public class LoginUserDetails extends User {
    private final UserBean user;    //認証に使うユーザクラス
    public LoginUserDetails(        //認証ユーザ作成(コンストラクタ)
        UserBean userBean,              //アカウント無効、認証無効、  
        boolean accountNonExpried,      //ロック状態を設定可能
        boolean credenttialsNonExpired, //今回は全て該当なし（true）
        boolean accountNonLocked,       //でユーザを認証
        Collection<GrantedAuthority> authorities) {//認証情報呼び出し時にリストで設定
            super(userBean.getUsername(),userBean.getPassword(),true,true,true,true,authorities);
            this.user = userBean;
    }
}

/*認証の際、ロック中（パスワード複数回間違い）などをDBに記録しておき、
  該当ユーザだった場合にロック無効フラグをfalseにすると、
  ロック中を示すExceptionを発生させることができる*/
