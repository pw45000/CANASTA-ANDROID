package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class results_screen extends AppCompatActivity {
    Button yes;
    Button no;
    Button exit;

    TextView p1_score_game_text;
    TextView p2_score_game_text;
    TextView p1_score_game;
    TextView p2_score_game;
    TextView p1_score_round;
    TextView p2_score_round;
    TextView play_again;
    TextView winner_text;
    TextView winner_decide;
    TextView log;

    Round model_round;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_screen);
         yes = findViewById(R.id.yes);
         no = findViewById(R.id.no);
         exit = findViewById(R.id.exit);
        p1_score_game_text = findViewById(R.id.p1_score_game_text);
        p2_score_game_text = findViewById(R.id.p2_score_game_text);
        p1_score_game = findViewById(R.id.p1_score_game);
        p2_score_game = findViewById(R.id.p2_score_game);
        p1_score_round = findViewById(R.id.p1_score_round);
        p2_score_round = findViewById(R.id.p2_score_round);
        play_again = findViewById(R.id.play_again);
        winner_text = findViewById(R.id.winner_text);
        winner_decide = findViewById(R.id.decide_winner_text);
        log = findViewById(R.id.log);
        log.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        model_round = (Round) intent.getSerializableExtra("Round");
        String log_text= (String) intent.getStringExtra("log");

        log.setText(log_text);

        int p1_score_r = model_round.tally_score(0);
        int p2_score_r = model_round.tally_score(1);
        System.out.println(p1_score_r);
        System.out.println(p2_score_r);

        String p1_score_r_string = String.valueOf(p1_score_r);
        String p2_score_r_string = String.valueOf(p2_score_r);

        p1_score_round.setText(p1_score_r_string);
        p2_score_round.setText(p2_score_r_string);
        model_round.get_players().get(0).add_to_score(p1_score_r);
        model_round.get_players().get(1).add_to_score(p2_score_r);
        log.setText(log.getText().toString()+"After remelding and calculating score:\n");
        log.setText(log.getText().toString()+model_round.output_round_info());

        int p1_score_g = model_round.get_players().get(0).get_score();
        int p2_score_g = model_round.get_players().get(1).get_score();
        String p1_score_g_string = String.valueOf(p1_score_g);
        String p2_score_g_string = String.valueOf(p2_score_g);



        p1_score_game.setText(p1_score_g_string);
        p2_score_game.setText(p2_score_g_string);
        winner_decide.setText(get_higher_score());

        yes.setOnClickListener(this::play_again);
        no.setOnClickListener(this::end_game);
        exit.setOnClickListener(this::return_to_menu);


    }


    String get_higher_score () {
        if (model_round.get_players().get(0).get_score() > model_round.get_players().get(1).get_score())
          return  "Computer";
        else if (model_round.get_players().get(0).get_score() < model_round.get_players().get(1).get_score())
            return "Human";
        else
           return "Tie";
    }



    void play_again (View view) {
        Intent r_intent = new Intent(this, main_board.class);
        Intent c_intent = new Intent(this, coin_toss.class);
        Human human_plr = (Human) model_round.get_players().get(1);
        Computer computer_plr = (Computer) model_round.get_players().get(0);

        if (!get_higher_score().equals("Tie")) {
            Round new_round = new Round(computer_plr.get_score(), human_plr.get_score(), model_round.get_round_num()+1);
            new_round.initial_draw();
            new_round.sort_players_hands();
            r_intent.putExtra("Round", new_round);
            startActivity(r_intent);
            finish();
        }
        else {
            c_intent.putExtra("Round", model_round);
            startActivity(c_intent);
            finish();
        }


    }

    void return_to_menu(View view) {
        Intent intent = new Intent(this, main_menu.class);
        startActivity(intent);
        finish();
    }

    void end_game (View view) {
        yes.setVisibility(View.INVISIBLE);
        no.setVisibility(View.INVISIBLE);
        exit.setVisibility(View.VISIBLE);
        winner_decide.setVisibility(View.VISIBLE);
        play_again.setVisibility(View.INVISIBLE);
        winner_text.setVisibility(View.VISIBLE);
        p1_score_game_text.setVisibility(View.VISIBLE);
        p2_score_game_text.setVisibility(View.VISIBLE);
        p1_score_game.setVisibility(View.VISIBLE);
        p2_score_game.setVisibility(View.VISIBLE);
    }


}
