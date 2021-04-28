package com.gianni.boxingsensormobilefrontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PowerTraining extends AppCompatActivity {
    private int TrainingModeID;
    ValueEventListener postListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_training);
        ProgramModeToDatabase();
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }
    private void ProgramModeToDatabase()
    {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://boxing-sensor-databas-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = rootNode.getReference("user_state");
        reference.setValue("1");
    }
}