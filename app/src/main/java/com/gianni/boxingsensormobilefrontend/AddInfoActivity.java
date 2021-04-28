package com.gianni.boxingsensormobilefrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.gianni.boxingsensormobilefrontend.RegisterActivity.txt_email;
import static com.gianni.boxingsensormobilefrontend.RegisterActivity.userInstances;

public class AddInfoActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText country;
    private Button submit ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        firstName = findViewById(R.id.firstNameField);
        lastName = findViewById(R.id.lastNameField);
        country = findViewById(R.id.countryField);
        submit = findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_firstName = firstName.getText().toString();
                String txt_lastName = lastName.getText().toString();
                String txt_country = country.getText().toString();

                if (TextUtils.isEmpty(txt_firstName) || TextUtils.isEmpty(txt_lastName) || TextUtils.isEmpty(txt_country)){
                    Toast.makeText(AddInfoActivity.this , "One or more empty fields"  , Toast.LENGTH_SHORT).show();
                } else if (txt_country.length() < 4){
                    Toast.makeText(AddInfoActivity.this, "Invalid country" , Toast.LENGTH_SHORT).show();
                } else {
                   storeNewUserData(txt_firstName, txt_lastName, txt_country);
                    Toast.makeText(AddInfoActivity.this, "Data was stored successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddInfoActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }


    private void storeNewUserData(String firstName, String lastName, String countryName) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://boxing-sensor-databas-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference dbreference = rootNode.getReference("UserCredentials");

       //dbreference.child("UID").child(txt_email).child("firstName").setValue(firstName);
        //dbreference.child("UID").child(txt_email).child("lastName").setValue(lastName);

        dbreference.child("UID").child("User_" + (userInstances)).child("firstName").setValue(firstName);
        dbreference.child("UID").child("User_" + (userInstances)).child("lastName").setValue(lastName);
        dbreference.child("UID").child("User_" + (userInstances)).child("country").setValue(countryName);


    }
}