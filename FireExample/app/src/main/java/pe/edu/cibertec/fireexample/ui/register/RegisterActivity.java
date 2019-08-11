package pe.edu.cibertec.fireexample.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pe.edu.cibertec.fireexample.R;
import pe.edu.cibertec.fireexample.ui.login.LoginActivity;
import pe.edu.cibertec.fireexample.ui.main.MainActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.RegisterView {

    EditText _etUsername;
    EditText _etPassword;

    Button _btnRegister;

    RegisterContract.RegisterPresenter<RegisterContract.RegisterView> _presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _presenter = new RegisterPresenterImpl<RegisterContract.RegisterView>();
        _presenter.attach( this);
        _initialize();
        _setListener();
    }

    private void _initialize(){
        _etUsername = findViewById(R.id.et_username);
        _etPassword = findViewById(R.id.et_password);
        _btnRegister = findViewById(R.id.btn_register);
    }

    private void _setListener(){
        _btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = _etUsername.getText().toString();
                String password = _etPassword.getText().toString();
                _presenter.register(username,password);
            }
        });

    }

    @Override
    public void onRegisterResult(boolean isSuccess) {
        if(isSuccess){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(RegisterActivity.this,"An error ocurred",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
