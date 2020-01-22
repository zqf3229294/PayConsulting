package edu.gatech.payconsulting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddConsultingActivity extends AppCompatActivity {
//    private DatabaseReference mDatabase;
    private TextView errorInformation;
    private EditText consultingDes;
    private EditText consultingPayAmount;
    private Button saveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_consulting);

        errorInformation = (TextView) findViewById(R.id.errorLabel);
        consultingDes = (EditText) findViewById(R.id.consultingDes);
        consultingPayAmount = (EditText) findViewById(R.id.consultingPayAmount);
        saveBtn = (Button) findViewById(R.id.saveBtn);

    }
    public void onSaveConsulting(View view){
        String cDes = consultingDes.getText().toString();
        String cPay = consultingPayAmount.getText().toString();
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //get input user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = "";
        String email = "";
        if (user != null) {
            // Name, email address
            name = user.getDisplayName();
            email = user.getEmail();
        }
        // Create a new record
        Map<String, Object> record = new HashMap<>();
        record.put("consultingDes", cDes);
        record.put("consultingPayAmount", cPay);
        record.put("name", name);
        record.put("email", email);
        record.put("isAccept", false);


        // Add a new document with a generated ID
        db.collection("records")
                .add(record)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddConsultingActivity.this, "Record Added Successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddConsultingActivity.this, "Record Added Failure!", Toast.LENGTH_SHORT).show();
                    }
                });
        startActivity(new Intent(this,MainActivity.class));


    }
}
