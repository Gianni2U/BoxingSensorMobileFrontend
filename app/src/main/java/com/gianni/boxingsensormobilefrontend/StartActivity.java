package com.gianni.boxingsensormobilefrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StartActivity extends AppCompatActivity {

    private Button register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        storeNewUserData();

        register =findViewById(R.id.button_register);
        login = findViewById(R.id.button_login);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(StartActivity.this , RegisterActivity.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(StartActivity.this , LoginActivity.class));
                finish();
            }
        });
    }
    private void storeNewUserData() {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://boxing-sensor-databas-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = rootNode.getReference("newpath");

        reference.setValue("eentest");

    }
}
