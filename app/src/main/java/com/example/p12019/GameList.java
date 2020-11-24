package com.example.p12019;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import Database.DBHandler;
import Database.DatabaseMaster;

public class GameList extends AppCompatActivity {

    private static final String TAG = "GameList";
    private List<DatabaseMaster.Game> games = new ArrayList<>();
    private RecyclerView recyclerView;
    private DBHandler dbHandler = new DBHandler(GameList.this);
    private GameListAdapter gameListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        initViews();
        initObjects();
    }

    public void initViews() {
        recyclerView = findViewById(R.id.gameRecycler);
    }

    public void initObjects() {
        games.addAll(dbHandler.viewGames());
        gameListAdapter = new GameListAdapter(getApplicationContext(), games);
        recyclerView.setAdapter(gameListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}