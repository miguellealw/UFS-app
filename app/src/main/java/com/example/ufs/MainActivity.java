package com.example.ufs;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ufs.data.model.StudentModel;

public class MainActivity extends AppCompatActivity {
    Button Signin;
    Button Register;
    EditText ed1,ed2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Signin = (Button)findViewByID(R.id.@+id/signinbutton);
        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);
        Register = (Button)findViewByID(R.id.button);

        Register.setOnClickListener(new View.onClickListener())
        {
            public void onClick(View view)
            {
                startActivity(new android.Intent(getApplicationContext(), Registration.class));
            }
        }
        Signin.setOnClickListener(new View.onClickListener())
        {
            public void onClick(View view)
            {

            }
        }
    }
}