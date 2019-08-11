package pe.edu.cibertec.fireexample.ui.login;

import com.google.firebase.auth.AuthCredential;

import pe.edu.cibertec.fireexample.base.BasePresenter;
import pe.edu.cibertec.fireexample.base.BaseView;

public class LoginContract {

    public interface LoginView extends BaseView{
        void onLoginResult(boolean isSuccess);
        void onUserLogger(boolean isSuccess);
    }

    public interface LoginPresenter<V extends LoginView> extends BasePresenter<V>{
        void onCheckedUserLogged();
        void login(String username,String password);
        void googleLogin(AuthCredential credential);
    }

}
