package com.gianni.boxingsensormobilefrontend;

import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.icu.util.Output;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.content.SharedPreferences;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.UUID;

public class SpeedTraining extends AppCompatActivity {
    private int TrainingModeID;
    private static final String TAG = "Bluetooth";
    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private boolean isSensorActive;
    private boolean isGameStopped = false;
    BluetoothSocket btSocket = null;
    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    Toast toast;
    private DatabaseReference mDatabase;
    private int seconds;
    private void ProgramModeToDatabase()
    {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://boxing-sensor-databas-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = rootNode.getReference("user_state");
        reference.setValue("2");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_training);
        mEditTextInput = findViewById(R.id.edit_text_input);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonSet = findViewById(R.id.button_set);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        //System.out.println(btAdapter.getBondedDevices());
        //BluetoothDevice hc05 = btAdapter.getRemoteDevice("98:D3:31:FD:95:4D");
        //System.out.println(hc05.getName());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ProgramModeToDatabase();

        int counter =0;

        //This is where the bluetooth connection will be made
        /*do {

            try {
                OutputStream outputStream = null;
                btSocket = hc05.createRfcommSocketToServiceRecord(mUUID);
                System.out.println(btSocket);
                btSocket.connect();
                System.out.println(btSocket.isConnected());
                // toast.makeText(getApplicationContext(),"Bluetooth connected", Toast.LENGTH_SHORT);
               // toast.show();
               outputStream = btSocket.getOutputStream();
                outputStream.write(7);
                Log.d(TAG, "Aanroepen!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            counter++;
        }while(!btSocket.isConnected() && counter < 3);

        try {

        }catch (IOException e)
        {
            e.printStackTrace();
        }
        */

        /* InputStream inputStream = null;
        try
        {
            inputStream = btSocket.getInputStream();
            inputStream.skip(inputStream.available());
            for(int i = 0; i<26;i++)
            {
                byte b = (byte) inputStream.read();
                System.out.println((char)b);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        try {
            btSocket.close();
            System.out.println(btSocket.isConnected());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditTextInput.getText().toString();
                if (input.length() == 0) {
                    MakeToast("Field can't be empty");
                    return;
                }
                long millisInput = Long.parseLong(input) * 60000;

                if (millisInput == 0) {
                    MakeToast("Please put a positive number");
                    return;
                }
                setTime(millisInput);
                mEditTextInput.setText("");
            }
        });
        mEditTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String input = mEditTextInput.getText().toString();
                if (input.length() == 0) {
                    return;
                }
                seconds = convertMinToSeconds(Integer.parseInt(input));
                FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://boxing-sensor-databas-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference reference = rootNode.getReference("/Training_Mode_1/Seconds");
                reference.setValue(seconds);
            }
        });
        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }
    /*private void sendSensorStatus()
    {
        //Send boolean (isSensorActive) over bluetooth to Arduino
        OutputStream outputStream = null;
        try {
            outputStream = btSocket.getOutputStream();
            outputStream.write(7);
            Log.d(TAG, "Aanroepen!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    */
    private int convertMinToSeconds(int min)
    {
      return min * 60;
    }
    private void setSensorStatusTrue()
    {
        isSensorActive = true;
    }
    public void MakeToast(String msg)
    {
        Toast.makeText(SpeedTraining.this,msg,Toast.LENGTH_SHORT).show();
    }
    private void setSensorStatusFalse()
    {
        isSensorActive = false;
    }
    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }
    private void startTimer() {
        setSensorStatusTrue();
        //sendSensorStatus();
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                MakeToast("Finished");
                setSensorStatusFalse();
                //sendSensorStatus();
                isGameStopped = true;
                SendGameStatus(isGameStopped);
                mTimerRunning = false;
                updateWatchInterface();
            }
        }.start();
        mTimerRunning = true;
        updateWatchInterface();
    }
    public  void SendGameStatus(boolean isGameStopped)
    {
        if(isGameStopped == true)
        {
         MakeToast("Game is stopped");
        }
    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        MakeToast("Pause");
        updateWatchInterface();
    }
    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        MakeToast("Reset");
        updateCountDownText();
        updateWatchInterface();
    }
    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        mTextViewCountDown.setText(timeLeftFormatted);
    }
    private void updateWatchInterface() {
        if (mTimerRunning) {
            mEditTextInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
        } else {
            mEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("Start");
            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }
            if (mTimeLeftInMillis < mStartTimeInMillis) {
                mButtonReset.setVisibility(View.VISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        editor.apply();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);
        updateCountDownText();
        updateWatchInterface();
        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();

            } else {
                startTimer();
            }
        }

    }
}