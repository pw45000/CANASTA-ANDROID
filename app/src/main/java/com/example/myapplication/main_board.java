package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;


public class main_board extends AppCompatActivity {
        TextView score_text_computer_num;
        TextView score_text_human_num;
        TextView round_number_text;
        TextView instruction_text;
        TextView player_turn_text;

        Button advice_button;
        Button log_button;

        Button continue_button;
        Button save_button;
        Button quit_button;

        Button create_meld_button;
        Button add_to_meld_button;
        Button transfer_cards_button;
        Button cancel_operation_button;
        Button end_meld_phase;

        HorizontalScrollView human_melds;
        HorizontalScrollView computer_melds;
        HorizontalScrollView human_hand;
        HorizontalScrollView computer_hand;

        LinearLayout human_melds_lin;
        LinearLayout computer_melds_lin;
        LinearLayout human_hand_lin;
        LinearLayout computer_hand_lin;

        ImageView discard_pile_image;
        ImageView stock_pile_image;

        ConstraintLayout parent_board;
        ConstraintLayout pop_up;
        ConstraintLayout main_game_board;

        TextView pop_up_text;
        TextView pop_up_advice;
        TextView pop_up_error;
        TextView pop_up_title;
        TextView pop_up_log;
        TextView pop_up_info;
        Button pop_up_dismiss;
        Button go_out_button;

        ConstraintLayout save_game_display;
        Button save_game_file_button;
        EditText save_game_input;

        EditText save_file_input;
        TextView save_file_prompt;


        String phase ="none";
        Round model_round;
        ArrayList<Card> potential_meld = new ArrayList<Card>();
        ArrayList<Integer> potential_meld_pos = new ArrayList<Integer>();
        String operation = "none";
        int transfer_donor_meld_pos;
        int transfer_receiver_meld_pos;
        int add_onto_pos;



    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.canasta_board);

            score_text_computer_num = findViewById(R.id.score_text_computer_num);
            score_text_human_num = findViewById(R.id.score_text_human_num);
            round_number_text = findViewById(R.id.round_number_text);
            instruction_text = findViewById(R.id.instruction_text);
            player_turn_text = findViewById(R.id.player_turn_text);

            advice_button = findViewById(R.id.advice_button);
            log_button = findViewById(R.id.log_button);

            continue_button = findViewById(R.id.continue_button);
            save_button = findViewById(R.id.save_button);
            quit_button = findViewById(R.id.quit_button);

            create_meld_button = findViewById(R.id.create_meld_button);
            add_to_meld_button = findViewById(R.id.add_to_meld_button);
            transfer_cards_button = findViewById(R.id.transfer_cards_button);
            cancel_operation_button = findViewById(R.id.cancel_operation_button);
            end_meld_phase = findViewById(R.id.end_phase_button);

            human_melds = findViewById(R.id.human_melds);
            computer_melds = findViewById(R.id.computer_melds);
            human_hand = findViewById(R.id.human_hand);
            computer_hand = findViewById(R.id.computer_hand);

            human_melds_lin = (LinearLayout) human_melds.findViewById(R.id.human_melds_lin);
            computer_melds_lin = (LinearLayout) computer_melds.findViewById(R.id.computer_meld_lin);
            human_hand_lin = (LinearLayout) human_hand.findViewById(R.id.human_hand_lin);
            computer_hand_lin = (LinearLayout) computer_hand.findViewById(R.id.computer_hand_lin);
            //https://stackoverflow.com/questions/15655274/horizontalscrollview-get-visible-children
            //https://stackoverflow.com/questions/8452677/why-does-findviewbyid-return-null


            discard_pile_image = findViewById(R.id.discard_pile_image);
            stock_pile_image = findViewById(R.id.stock_pile_image);

            parent_board = findViewById(R.id.parent_layout);
            main_game_board = parent_board.findViewById(R.id.main_game_board);
            pop_up = parent_board.findViewById(R.id.pop_up_layout);

            save_game_display = parent_board.findViewById(R.id.save_game_display);
            save_game_input = findViewById(R.id.save_file_input);
            save_game_file_button = findViewById(R.id.save_game_file_button);

            pop_up_advice = findViewById(R.id.pop_up_advice);
            pop_up_advice.setMovementMethod(new ScrollingMovementMethod());
            pop_up_log = findViewById(R.id.pop_up_log);
            pop_up_log.setMovementMethod(new ScrollingMovementMethod());
            pop_up_error = findViewById(R.id.pop_up_error_message);
            pop_up_error.setMovementMethod(new ScrollingMovementMethod());
            pop_up_title = findViewById(R.id.pop_up_title);
            pop_up_dismiss= findViewById(R.id.pop_up_dismiss);
            pop_up_info = findViewById(R.id.pop_up_info);
            pop_up_info.setMovementMethod(new ScrollingMovementMethod());
            go_out_button = findViewById(R.id.go_out_button);
            go_out_button.setOnClickListener(this::player_go_out);
            save_file_input = findViewById(R.id.save_file_input);
            save_file_prompt = findViewById(R.id.save_file_prompt);


            //Gson gson = new GsonBuilder().registerTypeAdapter(Player.class, new person_serialization_helper()).create();
            //String strObj = getIntent().getStringExtra("Round");
            //model_round =  gson.fromJson(strObj, Round.class);
            model_round = (Round)getIntent().getSerializableExtra("Round");
            //model_round.output_round_info();
            set_pre_turn_menu_visibility(true);
            phase = model_round.get_next_player()==2 ? "human intermission" : "computer";
            update_views();
            update_log(model_round.output_round_info());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                        .penaltyLog()
                .build());


    }
    void update_views() {
        Deck deck_model = model_round.get_deck();
        boolean stock_is_empty = deck_model.stock_is_empty();
        boolean discard_is_empty = deck_model.discard_is_empty();

        int round_num = model_round.get_round_num();

        ArrayList<Player> Players = model_round.get_players();
        Player Player1 = (Players.get(0));
        Player Player2 = Players.get(1);

        Hand computer_hand = Player1.get_player_hand();
        ArrayList<Card> computer_hand_container = computer_hand.get_hand_container();
        ArrayList<ArrayList<Card>> computer_meld_container = computer_hand.get_meld();
        int computer_score = Player1.get_score();

        Hand human_hand = Player2.get_player_hand();
        ArrayList<Card> human_hand_container = human_hand.get_hand_container();
        ArrayList<ArrayList<Card>> human_meld_container = human_hand.get_meld();
        int human_score = Player2.get_score();


        round_number_text.setText(String.valueOf(round_num));
        score_text_human_num.setText(String.valueOf(human_score));
        score_text_computer_num.setText(String.valueOf(computer_score));
        player_turn_text.setText(model_round.get_next_player()==2 ? "Human's" : "Computer's" );

        //phase = (model_round.get_next_player()==2) ? "human intermission" : "computer";
        set_instruction_text();
        // System.out.println(deck_model.get_top_discard_pile().get_card_string());



        if (stock_is_empty) {
            stock_pile_image.setVisibility(View.INVISIBLE);
        }
        else {
            stock_pile_image.setVisibility(View.VISIBLE);
        }


        if (!discard_is_empty) {
            add_card_to_scroll_container("discard_pile", 0, deck_model.get_top_discard_pile());
            discard_pile_image.setVisibility(View.VISIBLE);
        }

        else {
            discard_pile_image.setVisibility(View.INVISIBLE);
        }

        //Player1.print_vector(human_hand_container);
        //Player1.print_vector(computer_hand_container);
        //Player2.print_meld(0);
        System.out.println(model_round.output_round_info());
        //Player2.print_meld(1);
        //Player2.print_meld(2);
        //Player2.print_meld(3);
        //Player2.print_meld(4);
        //Player2.print_meld(5);

        for (int hand_size = 0; hand_size < human_hand_container.size(); hand_size++) {
            add_card_to_scroll_container("human_hand", hand_size+1,  human_hand_container.get(hand_size));
        }

        for (int hand_itr = 0; hand_itr < computer_hand_container.size(); hand_itr++) {
            add_card_to_scroll_container("computer_hand", hand_itr+1,  computer_hand_container.get(hand_itr));
        }

        if (human_meld_container.size()>0)
            for (int meld_itr = 0; meld_itr < human_meld_container.size(); meld_itr++) {
                add_card_to_scroll_container("human_melds", meld_itr+1,  human_meld_container.get(meld_itr).get(0));
            }
        if (computer_meld_container.size()>0)
            for (int meld_itr = 0; meld_itr < computer_meld_container.size(); meld_itr++) {
                add_card_to_scroll_container("computer_melds", meld_itr+1,  computer_meld_container.get(meld_itr).get(0));
            }

    }

    void set_pre_turn_menu_visibility(boolean option) {
        if (option) {
            save_button.setVisibility(View.VISIBLE);
            quit_button.setVisibility(View.VISIBLE);
            continue_button.setVisibility(View.VISIBLE);
            continue_button.setOnClickListener(this::choose_continue_type);
        }
        else {
            save_button.setVisibility(View.INVISIBLE);
            quit_button.setVisibility(View.INVISIBLE);
            continue_button.setVisibility(View.INVISIBLE);
        }
    }

    void choose_continue_type(View view) {
        if (model_round.get_next_player()==2)
            human_play(view);
        else {
            computer_play(view);
        }
    }

    void see_meld_internals(View view) {
        ImageView card_view = (ImageView) view;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
            int meld_pos = get_pos_from_melds(card_view);
            System.out.print("Contents of human meld at position "+meld_pos+": ");
            model_round.get_players().get(1).print_meld(meld_pos);


            //int meld_pos = card_view.getId();
            //meld_pos = meld_pos % 2000;
            //System.out.print("Contents of computer meld at position "+meld_pos+": ");
            //model_round.get_players().get(1).print_meld(meld_pos);



        System.out.flush();
        System.setOut(old);
        String internals = baos.toString();
        pop_up_title.setText("Meld Info");
        pop_up.setVisibility(View.VISIBLE);
        pop_up_info.setVisibility(View.VISIBLE);
        pop_up_info.setText(internals);
        pop_up.bringToFront();





    }

    void see_meld_internals_computer(View view) {
        ImageView card_view = (ImageView) view;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);



        int meld_pos = card_view.getId();
        meld_pos = meld_pos % 2000;
        meld_pos -=1;
        System.out.print("Contents of computer meld at position "+meld_pos+": ");
        model_round.get_players().get(0).print_meld(meld_pos);



        System.out.flush();
        System.setOut(old);
        String internals = baos.toString();

        pop_up.setVisibility(View.VISIBLE);
        pop_up_title.setText("Meld Info");
        pop_up_info.setVisibility(View.VISIBLE);
        pop_up_info.setText(internals);
        pop_up.bringToFront();





    }






    void prompt_go_out(View view) {
        main_game_board.setVisibility(View.INVISIBLE);
        pop_up.setVisibility(View.VISIBLE);
        pop_up_info.setText("You are able to go out since you have a canasta and no cards in your hand. Would you like to go out?");
        pop_up_info.setVisibility(View.VISIBLE);
        pop_up_log.setVisibility(View.GONE);
        save_game_file_button.setVisibility(View.GONE);
        save_file_input.setVisibility(View.GONE);
        save_file_prompt.setVisibility(View.GONE);
        pop_up_error.setVisibility(View.GONE);
        pop_up_advice.setVisibility(View.GONE);
        pop_up_title.setText("Go out?");
        go_out_button.setVisibility(View.VISIBLE);
    }

    void end_game(View view) {
        ArrayList<Player> players = model_round.get_players();
        Computer comp_plr = (Computer) players.get(0);
        Human human_plr = (Human)players.get(1);
        update_log("Last game log before remeld:\n");
        update_log(model_round.output_round_info());
        System.out.println(model_round.output_round_info());

        //empty arraylist since no need to be cautious.
        System.out.println(human_plr.strategy_meld(comp_plr.get_player_hand().get_meld(), "Computer"));
        System.out.println(comp_plr.strategy_meld(human_plr.get_player_hand().get_meld(), "Human"));

        Intent intent = new Intent(this, results_screen.class);
        intent.putExtra("Round",model_round);
        intent.putExtra("log", pop_up_log.getText().toString());
        startActivity(intent);
    }

    void player_go_out(View view) {
        update_log("Player 2 has decided to end the game by going out.");
        model_round.get_players().get(1).set_go_out_decision(true);
        model_round.get_players().get(0).set_go_out_decision(false);
        end_game(view);
    }


    void remove_all_card_views() {
        human_melds_lin.removeAllViews();
        computer_melds_lin.removeAllViews();
        human_hand_lin.removeAllViews();
        computer_hand_lin.removeAllViews();

    }

    void set_instruction_text() {
        if (phase.equals("draw") && operation.equals("none"))
            instruction_text.setText("Draw a card from stock or discard. (Tap either <->)");
        else if (phase.equals("meld") && operation.equals("none"))
            instruction_text.setText("Perform meld operations. (Tap an operation below)");
        else if (phase.equals("discard") && operation.equals("none"))
            instruction_text.setText("Discard a card. (Tap a card in your hand).");
        else if (phase.equals("computer") && operation.equals("none"))
            instruction_text.setText("Let the Computer take it's turn. (Press continue!).");
        else if (operation.equals("create meld")) {
            instruction_text.setText("Create a meld by selecting three cards.");
        }
        else if (operation.equals("create meld")) {
            instruction_text.setText("Create a meld by selecting three cards.");
        }
        else if (operation.equals("transfer card choose donor")) {
            instruction_text.setText("Choose a meld that will be the donor.");
        }
        else if (operation.equals("transfer card choose receiver")) {
            instruction_text.setText("Choose a meld that will be the receiver.");
        }
        else if (operation.equals("transfer card choose receiver")) {
            instruction_text.setText("Choose a meld that will be the receiver.");
        }
        else if (operation.equals("add to meld get meld")) {
            instruction_text.setText("Choose a meld that will receive the selected card.");
        }
        else if (operation.equals("add to meld get card")) {
            instruction_text.setText("Choose a card to add onto a meld.");
        }
        else
            instruction_text.setText("Human's turn: Press continue to play!");
    }


    void add_card_to_scroll_container(String container_name, int pos, Card card) {
        //human_hand_lin = findViewById(R.id.human_melds_lin);
        //computer_melds_lin = findViewById(R.id.computer_meld_lin);
        //human_hand_lin = findViewById(R.id.human_hand_lin);
        //computer_hand_lin = findViewById(R.id.computer_hand_lin);

        String translated_str;

        if (container_name.equals("computer_hand")) {
            translated_str = "card_back";
        }

        else{
            String original_str = card.get_card_string().toLowerCase(Locale.ROOT);
            translated_str = 'r' + original_str;
        }
        //System.out.println(translated_str);
        int id = this.getResources().getIdentifier(translated_str, "drawable", this.getPackageName());
        //int id = getResources().getIdentifier("com.example.myapplication:drawable:/" +translated_str, null, null);
        ImageView card_image = new ImageView(this);
        //card_image.setBackgroundResource(R.drawable.redight);
        //System.out.println(id);
        Drawable res = getResources().getDrawable(id);
        card_image.setImageDrawable(res);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(25, 0, 25,0);
        //card_image.setScaleType(ImageView.ScaleType.MATRIX);
        card_image.setLayoutParams(lp);
        card_image.setAdjustViewBounds(true);
        card_image.setContentDescription("not selected");
        //card_image.setScaleType(ImageView.ScaleType.CENTER);
        //card_image.setPadding(25, 0 , 25, 0);


        //lp.set
        //card_image.setImageResource();


        //if (id==0) {
        //    System.out.println("bad id: "+translated_str+" caller: "+container_name+" Card:"+card.get_card_string());
       // }
        //System.out.println(id);

        if (container_name.equals("human_melds")) {
           // System.out.println("Human hamd");
            card_image.setId(3000+pos);
            //card_image.setPadding(0, 0, 1, 0);
            //System.out.println(human_melds_lin.getClass());
            //card_image.setTranslationX(0*pos);

            //card_image.setPadding(-5, -5, -5,-5);
            //card_image.setAdjustViewBounds(true);
            card_image.setOnClickListener(this::human_meld_card_delegator);
            human_melds_lin.addView(card_image);
            //card_image.setPadding(-100, 0 , -100, 0);


            //LinearLayout.LayoutParams param = new HorizontalScrollView.LayoutParams
             //       (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            //human_melds_lin.setLayoutParams(param);
            //card_image.setX(0+pos*10);
        }
        else if (container_name.equals("computer_melds")) {
            card_image.setId(2000+pos);
            card_image.setOnClickListener(this::see_meld_internals_computer);
            //card_image.setTranslationX(10*pos);
            computer_melds_lin.addView(card_image);
            float x_cord = card_image.getX();
            float y_cord = card_image.getY();

        }
        else if (container_name.equals("human_hand")) {
            card_image.setId(1000+pos);
            //card_image.setTranslationX(10*pos);
            //card_image.setPadding(-100, 0 , -100, 0);
            card_image.setOnClickListener(this::human_hand_card_delegator);
            human_hand_lin.addView(card_image);

        }
        else if (container_name.equals("computer_hand")) {
            card_image.setId(4000+pos);
            //card_image.setTranslationX(10*pos);
            computer_hand_lin.addView(card_image);
        }
        else if (container_name.equals("discard_pile")) {
            discard_pile_image.setImageDrawable(res);
        }

        //https://stackoverflow.com/questions/6583843/how-to-access-resource-with-dynamic-name-in-my-case
    }

    public void quit_to_menu (View view) {
        Intent intent = new Intent(this, main_menu.class);
        startActivity(intent);
    }
    public void advice(View view) {
        if (pop_up==null) {
            System.out.println("NULL");
        }
        pop_up.setVisibility(View.VISIBLE);
        pop_up_error.setVisibility(View.GONE);
        pop_up_log.setVisibility(View.GONE);
        save_file_input.setVisibility(View.GONE);
        save_file_prompt.setVisibility(View.GONE);
        save_game_file_button.setVisibility(View.GONE);
        pop_up_advice.setVisibility(View.VISIBLE);
        pop_up_title.setText("Advice");
        pop_up.bringToFront();
        //main_game_board.setVisibility(View.INVISIBLE);
        ArrayList<Player> players = new ArrayList<Player>(model_round.get_players());
        Human temp_human = new Human((Human) players.get(1));
        ArrayList<ArrayList<Card>> temp_enemy_melds = model_round.get_players().get(0).get_player_hand().get_meld();
        Deck temp_deck = new Deck(model_round.get_deck());

        if (phase == "draw")
            pop_up_advice.setText(temp_human.strategy_draw(temp_deck, "Human"));
        else if (phase == "meld")
            pop_up_advice.setText(temp_human.strategy_meld(temp_enemy_melds, "Human"));
        else if (phase == "discard")
            pop_up_advice.setText(temp_human.strategy_discard(temp_deck, temp_enemy_melds, "Human"));
        else if (phase =="human intermission")
            pop_up_advice.setText("Why do you need advice? Press start to play!");
        else
            pop_up_advice.setText("Don't ask the Computer for advice or it'll destroy your GPA...");
    }


    public void show_log(View view) {
        if (pop_up==null) {
            System.out.println("NULL");
        }
        pop_up.setVisibility(View.VISIBLE);
        pop_up_log.setVisibility(View.VISIBLE);
        save_game_file_button.setVisibility(View.GONE);
        save_file_input.setVisibility(View.GONE);
        save_file_prompt.setVisibility(View.GONE);
        pop_up_error.setVisibility(View.GONE);
        pop_up_advice.setVisibility(View.GONE);
        pop_up_title.setText("Game Log");
        pop_up.bringToFront();
        //main_game_board.setVisibility(View.INVISIBLE);
    }

    public void show_error(View view) {
        pop_up.setVisibility(View.VISIBLE);
        pop_up_log.setVisibility(View.GONE);
        save_game_file_button.setVisibility(View.GONE);
        save_file_input.setVisibility(View.GONE);
        save_file_prompt.setVisibility(View.GONE);
        pop_up_error.setVisibility(View.VISIBLE);
        pop_up_advice.setVisibility(View.GONE);
        pop_up_title.setText("ERROR");
        pop_up.bringToFront();
    }


    public void set_meld_operations_visibility(boolean set) {
        if (set){
            end_meld_phase.setVisibility(View.VISIBLE);
            create_meld_button.setVisibility(View.VISIBLE);
            add_to_meld_button.setVisibility(View.VISIBLE);
            transfer_cards_button.setVisibility(View.VISIBLE);
        }
        else {
            end_meld_phase.setVisibility(View.INVISIBLE);
            create_meld_button.setVisibility(View.INVISIBLE);
            add_to_meld_button.setVisibility(View.INVISIBLE);
            transfer_cards_button.setVisibility(View.INVISIBLE);
        }
    }


    public void dismiss_pop_up(View view) {
        main_game_board.setVisibility(View.VISIBLE);
        pop_up.setVisibility(View.GONE);
        pop_up.setVisibility(View.GONE);
        pop_up_info.setVisibility(View.GONE);
        pop_up_log.setVisibility(View.GONE);
        save_game_file_button.setVisibility(View.GONE);
        save_file_input.setVisibility(View.GONE);
        save_file_prompt.setVisibility(View.GONE);
        pop_up_error.setVisibility(View.GONE);
        pop_up_advice.setVisibility(View.GONE);
        go_out_button.setVisibility(View.GONE);
    }

    public void show_save(View view ) {
        pop_up.setVisibility(View.VISIBLE);
        pop_up_title.setText("Save menu");
        save_file_input.setVisibility(View.VISIBLE);
        save_file_prompt.setVisibility(View.VISIBLE);
        save_game_file_button.setVisibility(View.VISIBLE);
        pop_up_error.setVisibility(View.GONE);
        pop_up_advice.setVisibility(View.GONE);
        pop_up_log.setVisibility(View.GONE);
        pop_up.bringToFront();
    }




    public void save_game_file(View view) {
        String file_name = save_game_input.getText().toString();
        File path = getApplicationContext().getFilesDir();

        try {
            FileOutputStream writer = new FileOutputStream(new File(path, file_name));
            writer.write(model_round.output_round_info().getBytes());
            writer.close();
            //Toast.makeText(getApplicationContext(), "Wrote to file: "+ file_name, Toast.LENGTH_SHORT).show();
            //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(view_context.openFileOutput(file_name, Context.MODE_PRIVATE));
            //outputStreamWriter.write(model_round.output_round_info().getBytes());
            //outputStreamWriter.close();
        }
        catch (IOException e) {
            //Toast.makeText(getApplicationContext(), "Failed to write file!", Toast.LENGTH_SHORT).show();
        }

        quit_to_menu(view);
    }


    public void update_log(String update_string) {
        String old_string = pop_up_log.getText().toString();
        pop_up_log.setText(old_string+update_string);
    }

    public void computer_play(View view) {
        set_pre_turn_menu_visibility(false);
        Computer comp = (Computer) model_round.get_players().get(0);
        //Computer dummy_comp = new Computer(comp);
        Human temp_human = (Human) model_round.get_players().get(1);
        ArrayList<ArrayList<Card>> temp_enemy_melds = temp_human.get_player_hand().get_meld();
        Deck temp_deck = model_round.get_deck();
        String string_result = comp.strategy_draw(temp_deck, "Computer");
        update_log(string_result);
        if (string_result.equals("The stock pile is empty and the CPU can't draw from the discard pile. It concedes!\n")) {
            //System.out.println("TODO: implement endgame");
            update_log("The computer decided to go out since it likes 100 point bonuses.");
            end_game(view);
        }
        if (comp.go_out()) {
            //System.out.println("TODO: implement endgame");
            update_log("The computer decided to go out since it likes 100 point bonuses.");
            end_game(view);
        }


        string_result = model_round.get_players().get(0).strategy_meld(temp_enemy_melds, "Computer");
        update_log(string_result);
        if (comp.go_out()) {
            //System.out.println("TODO: implement endgame");
            update_log("The computer decided to go out since it likes 100 point bonuses.");
            end_game(view);

        }
        model_round.get_players().get(0).clear_transfer_states();
        //System.out.println("Hit");


        string_result = comp.strategy_discard(temp_deck, temp_enemy_melds, "Computer");
        if (!temp_deck.get_top_discard_pile().isNatural()) {
            model_round.get_deck().set_discard_freeze(true);
        }
        else {
            model_round.get_deck().set_discard_freeze(false);
        }

        update_log(string_result);
        if (comp.go_out()) {
            update_log("The computer decided to go out since it likes 100 point bonuses.");
            //System.out.println("TODO: implement endgame");
            end_game(view);
        }
        update_log(model_round.output_round_info());
        remove_all_card_views();
        model_round.set_next_player(2);
        phase ="human intermission";
        update_views();
        set_pre_turn_menu_visibility(true);




    }

    void human_play(View view) {
        //System.out.println("HI");
        phase = "draw";
        set_pre_turn_menu_visibility(false);
        set_instruction_text();
       Deck dummy_deck = new Deck(model_round.get_deck());
       Deck real_deck = model_round.get_deck();
       ArrayList<ArrayList<Card>> enemy_melds = model_round.get_players().get(0).get_player_hand().get_meld();
       stock_pile_image.setOnClickListener(this::draw_stock_factory);
       discard_pile_image.setOnClickListener(this::draw_discard_factory);


        Human temp_human_ptr = new Human((Human) model_round.get_players().get(1));
        String draw_result = temp_human_ptr.strategy_draw(dummy_deck, "Human");

        if (draw_result.equals("You can't do much but wait and let the game end since the stock pile is empty and you can't draw from the discard pile...\n" )) {
            update_log("stock pile is empty and discard pile can't be drawn from, ending game...");
            end_game(view);
        }


        //System.out.println(model_round.output_round_info());
        temp_human_ptr = null;
       //main_game_board.setVisibility(View.GONE);
    }


    void draw_from_stock (View view) {
        Card drawn_card = new Card();
        Deck draw_decks = model_round.get_deck();
        do {
            drawn_card = draw_decks.draw_from_stock();
            System.out.println(drawn_card.get_card_string());
            //human player
            model_round.get_players().get(1).add_to_hand(drawn_card);
            model_round.get_players().get(1).purge_red_threes();
        } while (drawn_card.is_red_three() && !draw_decks.stock_is_empty());
        update_log(model_round.output_round_info());
        remove_all_card_views();
        update_views();
        if (model_round.get_players().get(1).can_go_out()) {
            prompt_go_out(view);
        }
        human_meld(view);
    }

    void draw_from_discard(View view) {
        Deck draw_decks = model_round.get_deck();
        Human human_plr = (Human) model_round.get_players().get(1);
        Hand hand_for_comparisons = human_plr.get_player_hand();

        if (draw_decks.get_discard_is_frozen()) {
            pop_up_error.setText("The Discard pile is frozen! Try drawing from stock instead.");
            show_error(view);
        }

        else {
            Card top_of_discard = draw_decks.get_top_discard_pile();
            boolean should_discard = (hand_for_comparisons.is_meldable(top_of_discard) || hand_for_comparisons.is_meldable_with_melds(top_of_discard));
            if (should_discard) {
                ArrayList<Card> picked_up_discard = draw_decks.draw_from_discard();
                human_plr.add_to_hand(picked_up_discard);
                human_plr.purge_red_threes();
                update_log(model_round.output_round_info());
                human_meld(view);
                phase="meld";
                //set_instruction_text();
                remove_all_card_views();
                update_views();
                if (model_round.get_players().get(1).can_go_out()) {
                    prompt_go_out(view);
                }
            }
            else {
                pop_up_error.setText("No cards in your hand can meld or lay off with the top of the discard pile: "+
                        top_of_discard.get_card_string());
                show_error(view);
            }
        }

    }

    void human_meld(View view) {
        phase="meld";
        set_instruction_text();
        set_meld_operations_visibility(true);
        create_meld_button.setOnClickListener(this::create_meld_operation);
        add_to_meld_button.setOnClickListener(this::add_to_meld_operation);
        transfer_cards_button.setOnClickListener(this::transfer_card_operation);
        cancel_operation_button.setOnClickListener(this::cancel_all_meld_operations);
        end_meld_phase.setOnClickListener(this::end_meld_phase);
    }

    void create_meld_operation(View view) {
        operation = "create meld";
        set_instruction_text();
        set_meld_operations_visibility(false);
        cancel_operation_button.setVisibility(View.VISIBLE);
    }

    void add_to_meld_operation(View view) {
        operation = "add to meld get card";
        set_instruction_text();
        set_meld_operations_visibility(false);
        cancel_operation_button.setVisibility(View.VISIBLE);
    }
    void transfer_card_operation(View view) {
        operation = "transfer card choose donor";
        set_instruction_text();
        set_meld_operations_visibility(false);
        cancel_operation_button.setVisibility(View.VISIBLE);
    }

    void end_meld_phase (View view) {
        cancel_all_meld_operations(view);
        set_meld_operations_visibility(false);
        remove_all_card_views();
        update_views();
        if (model_round.get_players().get(1).can_go_out()) {
            prompt_go_out(view);
        }
        human_discard(view);
        operation = "none";
    }

    void human_discard(View view) {
        model_round.get_players().get(1).clear_transfer_states();
        phase = "discard";
        set_instruction_text();

        if (model_round.get_players().get(1).get_player_hand().get_hand_container().size()==0) {
            model_round.set_next_player(1);
            remove_all_card_views();
            update_views();
            set_pre_turn_menu_visibility(true);
            phase = "computer";
        }
    }

    void select_discard(View view) {
        ImageView card_view = (ImageView) view;
        card_view.setBackgroundColor(Color.parseColor("#4CBB17"));
        card_view.setContentDescription("selected");

        Card discard_card = get_card_from_hand(card_view);
        if (!discard_card.isNatural()) {
            model_round.get_deck().set_discard_freeze(true);
        }

        else {
            model_round.get_deck().set_discard_freeze(false);
        }

        Deck stock_and_discard = model_round.get_deck();
        Hand player_hand = model_round.get_players().get(1).get_player_hand();
        stock_and_discard.discard_push_front(discard_card);
        player_hand.remove_from_hand(discard_card);


        model_round.set_next_player(1);
        remove_all_card_views();
        phase = "computer";
        update_views();
        if (model_round.get_players().get(1).can_go_out()) {
            prompt_go_out(view);
        }
        set_pre_turn_menu_visibility(true);
    }


    void do_nothing(View view ) {
        return;
    }

    void draw_stock_factory(View view) {
        if (phase.equals( "draw"))
            draw_from_stock(view);
    }

    void draw_discard_factory(View view) {
        if (phase.equals("draw"))
            draw_from_discard(view);
    }


    void human_hand_card_delegator(View view) {
        ImageView card_view = (ImageView) view;
        String content_selected_desc = card_view.getContentDescription().toString();

        if (operation.equals("create meld") && !content_selected_desc.equals("selected")) {
            select_card(view);
        }
        else if (operation.equals("create meld") && content_selected_desc.equals("selected")) {
            unselect_card(view);
        }

        else if (operation=="add to meld get card") {
            select_card_add(view);
        }

        else if (operation.equals("transfer card choose receiver")) {
            select_meld_receiver(view);
        }

        else if (operation=="none" && phase=="discard") {
            select_discard(view);
        }

    }

    void human_meld_card_delegator(View view) {
        if (operation.equals("transfer card choose donor")) {
            select_meld_donor(view);
        }
        else if (operation.equals("transfer card choose receiver")) {
            select_meld_receiver(view);
        }

        else if (operation.equals("add to meld get meld")) {
            select_meld_add(view);
        }
        else {
            see_meld_internals(view);
        }
    }

    void cancel_all_meld_operations(View view) {
        potential_meld.clear();
        potential_meld_pos.clear();
        add_onto_pos = -1;
        transfer_donor_meld_pos = -1;
        transfer_receiver_meld_pos = -1;
        operation = "none";
        phase = "meld";
        set_instruction_text();
        cancel_operation_button.setVisibility(View.INVISIBLE);
        set_meld_operations_visibility(true);
        remove_all_card_views();
        update_views();
    }

    Card get_card_from_hand(View view) {
        int hand_id_offset = 1000;
        ImageView card_view = (ImageView) view;
        int pos = (card_view.getId() % hand_id_offset)-1;
        ArrayList<Card> temp_hand = model_round.get_players().get(1).get_player_hand().get_hand_container();
        System.out.println(pos);
        return temp_hand.get(pos);
    }

    int get_card_pos_from_hand(View view) {
        int hand_id_offset = 1000;
        ImageView card_view = (ImageView) view;
        return (card_view.getId() % hand_id_offset)-1;
    }


    int get_pos_from_melds(View view ) {
        int meld_id_offset = 3000;
        ImageView card_view = (ImageView) view;
        return (card_view.getId() % meld_id_offset)-1;
    }


    void select_card_add(View view) {
        ImageView card_view = (ImageView) view;
        card_view.setBackgroundColor(Color.parseColor("#4CBB17"));
        card_view.setContentDescription("selected");

        add_onto_pos = (get_card_pos_from_hand(card_view));

        operation = "add to meld get meld";
        set_instruction_text();
    }

    void select_meld_add(View view) {
        ImageView card_view = (ImageView) view;
        card_view.setBackgroundColor(Color.parseColor("#4CBB17"));
        card_view.setContentDescription("selected");

        transfer_receiver_meld_pos = get_card_pos_from_hand(view);


        Hand human_hand_and_melds = model_round.get_players().get(1).get_player_hand();
        Card transfer = human_hand_and_melds.get_card_from_hand(add_onto_pos);
        String add_onto_results = human_hand_and_melds.lay_off(transfer, transfer_receiver_meld_pos);


        if (add_onto_results.equals("")) {
            remove_all_card_views();
            update_views();
            cancel_all_meld_operations(view);
            operation = "none";
            phase = "meld";
            set_instruction_text();
            update_log(model_round.output_round_info());
        } else {
            remove_all_card_views();
            update_views();
            pop_up_error.setText(add_onto_results);
            show_error(view);
            cancel_all_meld_operations(view);
            operation = "none";
            phase = "meld";
            set_instruction_text();
        }
    }





    void select_meld_donor(View view) {
        ImageView card_view = (ImageView) view;
        card_view.setBackgroundColor(Color.parseColor("#4CBB17"));
        card_view.setContentDescription("selected");

        transfer_donor_meld_pos = (get_card_pos_from_hand(card_view));

        Hand human_hand_and_melds = model_round.get_players().get(1).get_player_hand();
        ArrayList<Card> wild_card_list = human_hand_and_melds.get_wild_cards(transfer_donor_meld_pos);

        if (wild_card_list.size()==0) {
            remove_all_card_views();
            update_views();
            pop_up_error.setText("The selected transfer donor has no wild cards to transfer!");
            show_error(view);
            cancel_all_meld_operations(view);
            operation = "none";
            phase = "meld";
            set_instruction_text();
        }
        else {
            operation = "transfer card choose receiver";
            set_instruction_text();
        }
    }

    void select_meld_receiver(View view) {
        ImageView card_view = (ImageView) view;
        card_view.setBackgroundColor(Color.parseColor("#4CBB17"));
        card_view.setContentDescription("selected");
        LinearLayout card_view_parent = (LinearLayout) card_view.getParent();
        if (card_view_parent.getId() == R.id.human_hand_lin) {
            transfer_receiver_meld_pos = -2;
        }
        else {
            transfer_receiver_meld_pos = get_pos_from_melds(view);
        }

        Hand human_hand_and_melds = model_round.get_players().get(1).get_player_hand();
        ArrayList<Card> wild_card_list = human_hand_and_melds.get_wild_cards(transfer_donor_meld_pos);


        Card transfer_card = wild_card_list.get(0);
        String transfer_results =
                human_hand_and_melds.transfer_wild_card(transfer_card, transfer_donor_meld_pos, transfer_receiver_meld_pos);
        if (transfer_results.equals("")) {
            remove_all_card_views();
            update_views();
            cancel_all_meld_operations(view);
            operation = "none";
            phase = "meld";
            set_instruction_text();
            update_log(model_round.output_round_info());
        } else {
            remove_all_card_views();
            update_views();
            pop_up_error.setText(transfer_results);
            show_error(view);
            cancel_all_meld_operations(view);
            operation = "none";
            phase = "meld";
            set_instruction_text();
        }


        //Card transfer = human_hand_and_melds.get_card_from_hand(add_onto_pos);

    }


    void select_card(View view) {
        ImageView card_view = (ImageView) view;
        card_view.setBackgroundColor(Color.parseColor("#4CBB17"));
        card_view.setContentDescription("selected");

        Card potential_card = get_card_from_hand(view);
        potential_meld.add(potential_card);
        potential_meld_pos.add(get_card_pos_from_hand(view));

        if (potential_meld.size()==3) {
            Hand human_hand_and_melds = model_round.get_players().get(1).get_player_hand();
            String create_meld_results =
                    human_hand_and_melds.create_meld(potential_meld.get(0), potential_meld.get(1), potential_meld.get(2));
            if (create_meld_results.equals("")) {
                remove_all_card_views();
                update_views();
                cancel_all_meld_operations(view);
                operation="none";
                phase = "meld";
                set_instruction_text();
                update_log(model_round.output_round_info());
            }
            else {
                remove_all_card_views();
                update_views();
                pop_up_error.setText(create_meld_results);
                show_error(view);
                cancel_all_meld_operations(view);
                operation="none";
                phase = "meld";
                set_instruction_text();
            }
        }


    }
    void unselect_card(View view) {
        ImageView card_view = (ImageView) view;
        card_view.setBackgroundColor(0x00000000);
        card_view.setContentDescription("not selected");

        int card_pos = get_card_pos_from_hand(view);

        for (int pontential_meld_itr = 0; pontential_meld_itr < potential_meld.size(); pontential_meld_itr++ ) {
            if (card_pos== potential_meld_pos.get((pontential_meld_itr))) {
                potential_meld.remove(pontential_meld_itr);
                potential_meld_pos.remove(pontential_meld_itr);
            }

        }

    }



}
