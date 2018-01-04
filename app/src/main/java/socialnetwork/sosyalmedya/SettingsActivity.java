package socialnetwork.sosyalmedya;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth myAuth;
    EditText newPassword;
    EditText oldPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        myAuth = FirebaseAuth.getInstance();
        newPassword = (EditText) findViewById(R.id.newPassword);
        oldPassword = (EditText) findViewById(R.id.oldPassword);
        Button changeButton = (Button) findViewById(R.id.changeButton);
        changeButton.setOnClickListener(this);

    }

    public void updateProfile() {
        final FirebaseUser firebaseUser = myAuth.getCurrentUser();
        String email = firebaseUser.getEmail();

        AuthCredential credential = EmailAuthProvider
                .getCredential(email, oldPassword.getText().toString());
        firebaseUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public static final String TAG ="" ;

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            firebaseUser.updatePassword(newPassword.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Password updated");
                                    } else {
                                        Log.d(TAG, "Error password not updated");
                                    }
                                }
                            });
                        } else {
                            Log.d(TAG, "Error auth failed");
                        }
                    }
                });
    }




    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.changeButton){
            updateProfile();
            Intent intent = new Intent(SettingsActivity.this,ProfilActivity.class);
            startActivity(intent);
        }
    }
}
