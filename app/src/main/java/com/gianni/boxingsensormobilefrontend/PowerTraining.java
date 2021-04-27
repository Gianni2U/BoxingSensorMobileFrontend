package com.gianni.boxingsensormobilefrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PowerTraining extends AppCompatActivity {
    private int TrainingModeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_training);
        ProgramModeToDatabase();
    }
    private void ProgramModeToDatabase()
    {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://boxing-sensor-databas-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = rootNode.getReference("user_state");
        reference.setValue("1");
    }

}