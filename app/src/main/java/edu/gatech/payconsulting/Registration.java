package edu.gatech.payconsulting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
    EditText name_input, email_input, password_input;
    Button registration_btn;
    String name, email, password;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name_input = findViewById(R.id.name_input);
        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);
        registration_btn = findViewById(R.id.registration_btn);
        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = name_input.getText().toString().trim();
                email = email_input.getText().toString().trim();
                password = password_input.getText().toString().trim();

                if (!validRegister()) return;

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Registration.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                            // go to home
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Registration.this, "Register Failed! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    // check if registration info is valid
    private boolean validRegister() {
        if (name.equals("")) {
            name_input.setError("Input Your Name");
            return false;
        }
        if (email.equals("")) {
            email_input.setError("Input Your Email");
            return false;
        }
        if (password.equals("")) {
            password_input.setError("Input Your Password");
            return false;
        }
        return true;
    }

    public void gotoLogin(View view) {
        startActivity(new Intent(this, Login.class));
    }
}
