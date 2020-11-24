package com.example.p12019;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import Database.DBHandler;

public class AddGame extends AppCompatActivity {

    ImageView addGame;
    EditText name;
    EditText yearGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        addGame = (ImageView)findViewById(R.id.addGame);
        name = (EditText) findViewById(R.id.editTextGameName);
        yearGame = (EditText)findViewById(R.id.editTextGameYear);

        addGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());

                if (dbHandler.addGame(name.getText().toString().trim(), Integer.parseInt(yearGame.getText().toString().trim())) == true) {
                    Toast.makeText(AddGame.this, "Game added Successfully!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddGame.this, "Database error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}