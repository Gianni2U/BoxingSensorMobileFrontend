package com.gianni.boxingsensormobilefrontend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerTraining extends AppCompatActivity {
    private int TrainingModeID;
    DatabaseReference reff;
    TextView TVtop1;
    Button GetDataBTN;
    DataSnapshot snapshot;
    String url;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_training);
        TVtop1 = findViewById(R.id.Top1TV);
        ProgramModeToDatabase();
        GetDataBTN = findViewById(R.id.GetDatabutton);
        GetDataBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ShowData();
                //Log.d("Bericht","Button geklikt");

            }
        });

    }
    private void ProgramModeToDatabase()
    {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://boxing-sensor-databas-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = rootNode.getReference("user_state");
        reference.setValue("1");
    }
    private void ShowData()
    {
        /*
        url = "https://boxing-sensor-databas-default-rtdb.europe-west1.firebasedatabase.app/Training_Mode_2/2021-04-27/Samuel/Punches/-MZJwyoZEGdMbqb_M75K.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("YEes","Yoehoe");
                            JSONArray jsonArray = response.getJSONArray("Punches");

                            for(int i = 0; i <jsonArray.length();i++)
                            {
                                JSONObject Punches = jsonArray.getJSONObject(i);
                                String Top1 = Punches.getString("-MZJwyoZEGdMbqb_M75K");
                                TVtop1.append(Top1);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
        */
        /*
        reff = FirebaseDatabase.getInstance().getReference().child("Training_Mode_2").child("2021-04-27/Samuel/Punches");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TVtop1 = findViewById(R.id.Top1TV);
                Log.d("Waarde","GElukt");
                String name = dataSnapshot.child("-MZJwyoZEGdMbqb_M75K").getValue().toString();
                TVtop1.setText(name);
                Toast.makeText(getApplicationContext(),"Data uithalen",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
}
}
