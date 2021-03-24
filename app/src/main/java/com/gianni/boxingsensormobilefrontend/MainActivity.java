package com.gianni.boxingsensormobilefrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button logout;
    //private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //private DatabaseReference myRef = database.getReference("UserData");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });

      //  myRef.child("UID").setValue("BE000001");
        // FirebaseDatabase.getInstance().getReference().child("UserData").child("UID").setValue("BE190U98");

      /*  HashMap<String, Object>map = new HashMap<>();
        map.put("Name" , "Gianni Utoro");
        map.put("Email", "s109211@ap.be");
        map.put("Trainingdata", "Basically Mike Tyson");

        FirebaseDatabase.getInstance().getReference().child("UserData").updateChildren(map);
            */


    }
}
