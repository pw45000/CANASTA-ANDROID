package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.app.Activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;



public class main_menu extends AppCompatActivity{


    Button load_file_button;
    Button new_game_button;
    Button cancel_load_button;
    Button load_game_button;
    //Button load_file_button;
    TextView file_load_failure_text;
    TextView enter_file_prompt;
    TextView file_input_field;
    TextView missing_file_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        //the variables for manipulating
        load_file_button = (Button) findViewById(R.id.load_file_button);
        new_game_button = (Button) findViewById(R.id.new_game_button);
        cancel_load_button = (Button) findViewById(R.id.cancel_load_button);
        load_game_button = (Button) findViewById(R.id.load_game_button);
        //Button load_file_button = (Button) findViewById(R.id.load_file_button);
        file_load_failure_text =  (TextView) findViewById(R.id.file_load_failure_text);
        enter_file_prompt =  (TextView) findViewById(R.id.enter_file_prompt);
        file_input_field =  (EditText) findViewById(R.id.file_input_field);
        missing_file_text =  (TextView) findViewById(R.id.file_load_failure_text);
    }


    public void load_coin_toss(View view){
        Intent intent = new Intent(this, coin_toss.class);
        startActivity(intent);
        finish();
    }

    public void load_preloaded_game(View view, Round parsed_file_contents){
        Intent intent = new Intent(this, main_board.class);
        //intent.putExtra("Round", gson.toJson(parsed_file_contents));
        intent.putExtra("Round", parsed_file_contents);
        startActivity(intent);
        finish();
    }

    public void load_file_menu(View view) {
        load_game_button.setVisibility(View.INVISIBLE);
        new_game_button.setVisibility(View.INVISIBLE);

        load_file_button.setVisibility(View.VISIBLE);
        cancel_load_button.setVisibility(View.VISIBLE);
        enter_file_prompt.setVisibility(View.VISIBLE);
        file_input_field.setVisibility(View.VISIBLE);
    }

    public boolean loaded_correctly(String file_string ) throws IOException, FileNotFoundException  {
        File testing_file;
        try {
            //https://stackoverflow.com/questions/45908648/how-to-access-a-file-from-asset-raw-directory
            https://stackoverflow.com/questions/5200187/convert-inputstream-to-bufferedreader
            //new InputStreamReader (am.open(file_string));
            testing_file = new File(getFilesDir()+File.separator+file_string);
            if (testing_file.exists())
                return true;
            else
                return false;
        }
        catch (Exception e) {
            return false;
            //file_load_failure_text.setText("Loading failed: File doesn't exist!");
            //file_load_failure_text.setVisibility(View.VISIBLE);
        }
    }


    public void attempt_to_load(View view) throws IOException, FileNotFoundException {
        Round new_round = new Round();
        String file_string =  file_input_field.getText().toString();
        AssetManager am = getAssets();




        if (loaded_correctly( file_string)) {
            //Reader is = new InputStreamReader(getApplicationContext().openFileInput(file_string));
            //BufferedReader br = new BufferedReader(is);
            BufferedReader br = new BufferedReader(new FileReader(new
                    File(getFilesDir()+File.separator+file_string)));

            String extracted_string;
            ArrayList<String> lines_to_parse = new ArrayList<String>();
            while ((extracted_string = br.readLine()) != null) {
                //std::istringstream ss = new std::istringstream(extracted_string);

                if (extracted_string.indexOf(':') != -1) {

                    //removes extra spaces inside the string.
                    //https://stackoverflow.com/questions/35301432/remove-extra-white-spaces-in-c
                    //https://stackoverflow.com/questions/1041620/whats-the-most-efficient-way-to-erase-duplicates-and-sort-a-vector
                    //https://howtodoinjava.com/java/string/remove-extra-whitespaces-between-words/
                    extracted_string = extracted_string.replaceAll("\\s+", " ");

                    //https://stackoverflow.com/questions/216823/how-to-trim-an-stdstring
                    //C++ TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
                    //ORIGINAL LINE: extracted_string.erase(extracted_string.begin(), std::find_if(extracted_string.begin(), extracted_string.end(), [](unsigned char ch)
                    //extracted_string = extracted_string.substring(0, extracted_string.iterator()) + extracted_string.substring(extracted_string.iterator() + std::find_if(extracted_string.iterator(), extracted_string.end(), (byte ch) ->
                    //{
                    //	return !Character.isWhitespace(ch);
                    //}));
                    lines_to_parse.add(extracted_string);
                }
            }
            boolean load_file_success = new_round.load_game(lines_to_parse);
            if (load_file_success) {
                new_round.output_round_info();
                System.out.println(lines_to_parse.get(4));
                load_preloaded_game(view, new_round );
            }
            else {
                file_load_failure_text.setText("Loading failed: File doesn't have the right format!");
                file_load_failure_text.setVisibility(View.VISIBLE);
            }
           // load_preloaded_game(view, lines_to_parse);
        }
        else {
            file_load_failure_text.setVisibility(View.VISIBLE);
        }

    }

    public void cancel_load(View view) {
        load_game_button.setVisibility(View.VISIBLE);
        new_game_button.setVisibility(View.VISIBLE);

        load_file_button.setVisibility(View.INVISIBLE);
        cancel_load_button.setVisibility(View.INVISIBLE);
        enter_file_prompt.setVisibility(View.INVISIBLE);
        file_input_field.setVisibility(View.INVISIBLE);
        file_load_failure_text.setVisibility(View.INVISIBLE);
    }



}
