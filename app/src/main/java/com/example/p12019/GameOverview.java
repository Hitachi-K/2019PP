package com.example.p12019;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Database.DBHandler;
import Database.DatabaseMaster;

public class GameOverview extends AppCompatActivity {

    EditText Comment;
    Button addComment;
    RatingBar rating;
    TextView currentRating, gameName;

    private List<DatabaseMaster.Comments> comments = new ArrayList<>();
    private RecyclerView recyclerView;
    private DBHandler dbHandler = new DBHandler(GameOverview.this);
    private CommentListAdapter commentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_overview);

        Comment = (EditText)findViewById(R.id.editTextComment);
        addComment = (Button)findViewById(R.id.btnAddComment);
        rating = (RatingBar)findViewById(R.id.ratingBar);
        gameName = (TextView)findViewById(R.id.gameName);
        currentRating = (TextView)findViewById(R.id.currentRating);

        Intent intent = getIntent();
        gameName.setText(intent.getStringExtra("name"));
        String gName = intent.getStringExtra("name");
        float avgRate = dbHandler.calcAvgRating(gName);
        currentRating.setText(String.valueOf(avgRate));
        initViews();
        initObjects();

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long val = dbHandler.insertComments(gName, Comment.getText().toString().trim(), (int)rating.getRating());
                if (val > 0 ) {
                    Toast.makeText(GameOverview.this, "Comment Added!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(GameOverview.this, "Database Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.commentRecycler);
    }

    private void initObjects() {
        Intent intent = getIntent();
        comments.addAll(dbHandler.viewComments(intent.getStringExtra("name")));
        commentListAdapter = new CommentListAdapter(comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(commentListAdapter);
    }
}