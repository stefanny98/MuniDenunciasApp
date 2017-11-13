package com.aquino.munidenuncias;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.textUser);
        password = (EditText)findViewById(R.id.textPassword);

    }

    public void login(View view) {

    }

    public void register(View view) {

    }
}
