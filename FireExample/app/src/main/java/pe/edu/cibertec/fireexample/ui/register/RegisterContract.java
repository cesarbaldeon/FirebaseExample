package pe.edu.cibertec.fireexample.ui.register;

import com.google.firebase.auth.AuthCredential;

import pe.edu.cibertec.fireexample.base.BasePresenter;
import pe.edu.cibertec.fireexample.base.BaseView;
import pe.edu.cibertec.fireexample.ui.login.LoginContract;

public class RegisterContract {

        public interface RegisterView extends BaseView {
            void onRegisterResult(boolean isSuccess);
        }

        public interface RegisterPresenter<V extends RegisterContract.RegisterView> extends BasePresenter<V> {
            void register(String username,String password);
        }

}
