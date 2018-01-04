package socialnetwork.sosyalmedya;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth myAuth;
    private EditText password;
    private EditText email;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        Button login = (Button) findViewById(R.id.loginButton);
        Button register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(this);
        login.setOnClickListener(this);


    }

    public void login(String email,String password){
        myAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user=myAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this,
                                    "Giriş başarılı!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this,HomePageActivity.class);
                            startActivity(intent);

                        }else
                            Toast.makeText(
                                    LoginActivity.this, "Yanlış e-mail ya da parola!"
                                    , Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.loginButton){
            login(this.email.getText().toString(),this.password.getText().toString());
        }else if(id == R.id.registerButton){
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    }
}


