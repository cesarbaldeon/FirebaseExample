package pe.edu.cibertec.fireexample.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import pe.edu.cibertec.fireexample.ui.main.MainActivity;
import pe.edu.cibertec.fireexample.R;
import pe.edu.cibertec.fireexample.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,LoginContract.LoginView {

    //@BindView(R.id.et_username)
    EditText _etUsername;
    @BindView(R.id.et_password)
    EditText _etPassword;

   // @BindView(R.id.login)
    Button _loginButton;
    //@BindView(R.id.btn_register)
    Button _registerButton;

   // @BindView(R.id.google_button)
    SignInButton _googleButton;

    private GoogleSignInClient googleSignInClient;
    LoginContract.LoginPresenter<LoginContract.LoginView> _presenter;

    private static  final int RC_SIGN_IN =5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _presenter =  new LoginPresenterImpl<LoginContract.LoginView>();
        _presenter.attach(this);
        _presenter.onCheckedUserLogged();
        _initialize();
        _googleConfiguration();
        _setListener();

    }

    private void _googleConfiguration(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

    }


    private void _initialize(){
        _etUsername = findViewById(R.id.et_username);
        _etPassword = findViewById(R.id.et_password);
        _loginButton = findViewById(R.id.login);
        _registerButton = findViewById(R.id.btn_register);
        _googleButton = findViewById(R.id.google_button);
    }

    private void _setListener(){
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = _etUsername.getText().toString();
                String password = _etPassword.getText().toString();
                _presenter.login(username,password);
            }
        });

        _registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        _googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _signIn();
            }
        });
    }

    private void _signIn(){
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                if (account != null){
                    _authWithGoogleAccount(account);
                }

            }
        }
    }

    private void _authWithGoogleAccount(GoogleSignInAccount account) {
        final AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        _presenter.googleLogin(authCredential);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
       if(connectionResult.getErrorMessage() != null ){
           Log.d("SIGNIN_ERROR",connectionResult.getErrorMessage());
       }
    }

    @Override
    public void onLoginResult(boolean isSuccess) {
        if(isSuccess){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(LoginActivity.this,"An error ocurred",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onUserLogger(boolean isSuccess) {
        if(isSuccess) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}