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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth myAuth;
    private EditText userName;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myAuth = FirebaseAuth.getInstance();

        userName = (EditText) findViewById(R.id.userName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        Button register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(this);

    }

    public void register(String email,String password){
        myAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(RegisterActivity.this, "Başarıyla kayıt olundu!"
                                    , Toast.LENGTH_SHORT).show();

                            User user = new User(userName.getText().toString());
                            DatabaseReference databaseReferenceUsers = FirebaseDatabase
                                    .getInstance().getReference();
                            databaseReferenceUsers.child("users")
                                    .child(myAuth.getCurrentUser().getUid()).setValue(user);

                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);

                        }else{
                            //hata durumu
                            Toast.makeText(RegisterActivity.this, "Kayıt başarısız!"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.registerButton){

            register(this.email.getText().toString(),this.password.getText().toString());

        }

    }
}


