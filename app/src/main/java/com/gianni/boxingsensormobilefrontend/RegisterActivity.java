package com.gianni.boxingsensormobilefrontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

public class RegisterActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button register;
    private FirebaseAuth auth;
    private boolean runOnce = false;
    public static String txt_email;
    public static Integer userInstances = 0;
    final FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://boxing-sensor-databas-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference dbreference = rootNode.getReference("UserCredentials");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);

        auth = FirebaseAuth.getInstance();
        
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(RegisterActivity.this , "Empty credentials!"  , Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password too short" , Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txt_email, txt_password);
                }
            }
        });
    }


    private void registerUser(String email, String password) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    storeNewUserData(txt_email);
                    DatabaseReference ref_UID = dbreference.child("UID").child("UIDcount");
                    Log.d("Firebase", ref_UID.get().toString());

                    //Toast.makeText(RegisterActivity.this, "User was registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, AddInfoActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void storeNewUserData(final String email) {

        DatabaseReference myRef = dbreference.child("UID").child("UIDcount");
        //Adding eventListener to that reference
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Getting the string value of that node
                String value =  dataSnapshot.getValue(Long.class).toString();
                userInstances = Integer.parseInt(value) ;

                while (!runOnce) {
                    dbreference.child("UID").child("User_" + (userInstances+1)).child("email").setValue(txt_email);
                    dbreference.child("UID").child("UIDcount").setValue(userInstances+1);
                    runOnce = true;
                }

                Toast.makeText(RegisterActivity.this, "Data Received: " + value, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("empty", "onCancelled: Something went wrong! Error:" + databaseError.getMessage() );

            }
        });



    }

}
