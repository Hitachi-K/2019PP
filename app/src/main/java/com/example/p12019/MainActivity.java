package com.example.p12019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Database.DBHandler;

public class MainActivity extends AppCompatActivity {

    Button login, register;
    EditText uName, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mapping the variables to the items in the layout
        login = (Button)findViewById(R.id.btnLogin);
        register = (Button)findViewById(R.id.btnRegister);
        uName = (EditText)findViewById(R.id.editTextUsername1);
        pass = (EditText)findViewById(R.id.editTextPassword1);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());

                int check = dbHandler.loginUser(uName.getText().toString().trim(), pass.getText().toString().trim());

                if (check == 0) {
                    Toast.makeText(MainActivity.this, "Username or Password is missing", Toast.LENGTH_SHORT).show();
                }
                else if (check == 1) {
                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, GameList.class);
                    startActivity(intent);
                }
                else if (check == 2) {
                    Toast.makeText(MainActivity.this, "Admin Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AddGame.class);
                    startActivity(intent);
                }
                else if (check == 3) {
                    Toast.makeText(MainActivity.this, "Invalid Login Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());

                long val = dbHandler.registerUser(uName.getText().toString().trim(), pass.getText().toString().trim());

                if (val > 0) {
                    Toast.makeText(MainActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, GameList.class);
                    startActivity(intent);

                    if (uName.getText().toString().trim().equals("admin")) {
                        Intent intent2 = new Intent(MainActivity.this, AddGame.class);
                        startActivity(intent2);
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}