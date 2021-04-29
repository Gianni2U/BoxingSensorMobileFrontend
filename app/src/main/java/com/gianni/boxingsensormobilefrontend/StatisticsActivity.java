package com.gianni.boxingsensormobilefrontend;

import androidx.appcompat.app.AppCompatActivity;
import static com.gianni.boxingsensormobilefrontend.RegisterActivity.userInstances;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }


}