package com.example.myapplication;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class coin_toss extends AppCompatActivity {
    Button heads_button;
    Button tails_button;
    Button start_game_button;
    TextView results_text;
    TextView coin_flip_result;
    ImageView image;
    boolean successful_player;
    Round new_round;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin_toss_view);
        heads_button = (Button) findViewById(R.id.heads_button);
        tails_button = (Button) findViewById(R.id.tails_button);
        start_game_button = (Button) findViewById(R.id.start_game_button);
        results_text = (TextView)  findViewById(R.id.results_text);
        coin_flip_result = (TextView)  findViewById(R.id.coin_flip_result);
        TextView coin_toss_prompt_text = (TextView) findViewById(R.id.coin_toss_prompt_text);
        image = (ImageView) findViewById(R.id.image_fumo);

        Intent intent = getIntent();

        if (intent.hasExtra("Round")) {
            Round model_round = (Round)intent.getSerializableExtra("Round");
            ArrayList<Player> players = new ArrayList<Player>();
            players.add(new Computer());
            players.add(new Human());
            players.get(0).set_player_score(model_round.get_players().get(0).get_score());
            players.get(1).set_player_score(model_round.get_players().get(1).get_score());
            new_round = new Round(players, model_round.get_round_num()+1);
            new_round.initial_draw();
            new_round.sort_players_hands();
        } else {
            ArrayList<Player> players = new ArrayList<Player>();
            players.add(new Computer());
            players.add(new Human());
            new_round = new Round(players, 1);
            new_round.initial_draw();
            new_round.sort_players_hands();
        }
       // new_round.output_round_info();


    }

    public void execute_coin_toss(View view) {
        //heads =1, tails = 2.

        //Player player_1 = new Computer();
        //Player player_2 = new Human();
        //ArrayList<Player> players = new ArrayList<Player>();
        //players.add(player_1);
        //players.add(player_2);


        //coin_flip_result.bringToFront();

        //Game new_game = new Game(player_1, player_2);
        //Round test_round = new Round();

       int choice = (Integer)view.getTag();


        boolean coin_result =  new_round.coin_toss(choice);

        if (coin_result) {
            results_text.setText("Player 2 Guessed Correctly! They go first!");
            coin_flip_result.setText(choice == 1 ? "Heads" : "Tails");
            successful_player = true;
            //new_round = test_round;
        }

        else {
            results_text.setText("Player 2 Guessed Incorrectly... Player 1 goes first!");
            coin_flip_result.setText(choice == 1 ? "Tails" : "Heads");
            successful_player = false;
            //new_round = test_round;
        }


        heads_button.setVisibility(View.INVISIBLE);
        tails_button.setVisibility(View.INVISIBLE);
        start_game_button.setVisibility(View.VISIBLE);
        results_text.setVisibility(View.VISIBLE);
        coin_flip_result.setVisibility(View.VISIBLE);

    }

    public void guess_heads(View view) {
        view.setTag(new Integer(1));
        execute_coin_toss(view);
    }

    public void guess_tails(View view) {
        view.setTag(new Integer (2));
        execute_coin_toss(view);
    }
    public void start_game(View view) {
        Intent intent = new Intent(this, main_board.class);
        //intent.putExtra("successful_player", successful_player);
        if (successful_player) {
            new_round.set_next_player(2);
        }

        else {
            new_round.set_next_player(1);
        }

        new_round.output_round_info();
        //Gson gson = new Gson();
        intent.putExtra("Round", new_round);
        startActivity(intent);
        finish();
    }
}
