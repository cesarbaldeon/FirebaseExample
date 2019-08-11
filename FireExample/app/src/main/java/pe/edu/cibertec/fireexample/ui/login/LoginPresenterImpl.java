package pe.edu.cibertec.fireexample.ui.login;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pe.edu.cibertec.fireexample.ui.CommonPresenter;

public class LoginPresenterImpl<V extends LoginContract.LoginView>
                               extends CommonPresenter<V> implements LoginContract.LoginPresenter<V> {

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    LoginPresenterImpl(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }


    @Override
    public void onCheckedUserLogged() {
        getView().onUserLogger(firebaseUser!=null);
    }

    @Override
    public void login(String username, String password) {
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
            return;

        firebaseAuth.signInWithEmailAndPassword(username,password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() && task.getResult() != null)
                    getView().onLoginResult(true);
                else
                    getView().onLoginResult(false);
            }
        });
    }

    @Override
    public void googleLogin(AuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() && task.getResult()!=null){
                    getView().onLoginResult(true);
                }
                else
                    getView().onLoginResult(false);
            }
        });
    }
}
