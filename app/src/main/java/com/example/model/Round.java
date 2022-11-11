package com.example.model;

import java.util.*;
import java.util.stream.Stream;
import java.io.*;
/* ***********************************************************
 * Name:  Patrick Wierzbicki*
 * Project : Canasta C++ Project 1*
 * Class : CMPS-366-01*
 * Date : 9/28/22*
 *********************************************************** */

/* ***********************************************************
 * Name:  Patrick Wierzbicki*
 * Project : Canasta C++ Project 1*
 * Class : CMPS-366-01*
 * Date : 9/28/22*
 *********************************************************** */

//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Player;
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Computer;
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Human;
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Deck;
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Card;
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Hand;
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Game;







public class Round implements Closeable
{

	/* *********************************************************************
	Function Name: Round
	Purpose: The default constructor for the Round class.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public Round()
	{

		next_player = 0;
		round_number = 0;
	}


	/* *********************************************************************
	Function Name: Round
	Purpose: The non default constructor for the class, including a player list and round number from Game.
	Parameters:
				  players: a vector of Player pointers representing the players in the Game.
				  round_number: an int representing the number of the round.
	Return Value: none.
	Assistance Received: none
	********************************************************************* */
	public Round(ArrayList<Player> players, int round_number)
	{
		this.players = new ArrayList<Player>(players);
		this.round_number = round_number;
		next_player = 0;
	}


	/* *********************************************************************
	Function Name: Round
	Purpose: The copy constructor for the Round class.
	Parameters:
				  other_round, a const reference to a Round class from which to copy from.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public Round(final Round other_round)
	{
		this.next_player = other_round.next_player;
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to be a copy assignment (rather than a reference assignment) - this should be verified and a 'copyFrom' method should be created:
//ORIGINAL LINE: this->stock_and_discard = other_round.stock_and_discard;
		this.stock_and_discard = other_round.stock_and_discard;
		this.players = new ArrayList<Player>(other_round.players);

	}

	/* *********************************************************************
	Function Name: Round
	Purpose: The destructor for the Round class.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void close()
	{
		stock_and_discard.clear_discard();
	}



	/* *********************************************************************
	Function Name: coin_toss
	Purpose: Does a coin toss and asks the second player heads or tails, will decide who will go first depending on the coin_toss.
	Parameters: none
	Algorithm:
				 1. Create a seed and uniform distribution based on the device between 1 and 2, representing heads and tails.
				 2. Ask the player for a validated input of 1 or 2 representing heads or tails.
				 3. Depending on the answer, either player 2 will go first if they guessed correctly or
					 Player 1 if they guessed incorrectly.
	Local variables:
			1. seed1, a chrono based variable that represents a seed based on the system.
			2. dev, a std::random device which decides the device to use.
			3. flipped_coin: a value between 1 or 2 represented by heads or tails.
			4. choice, an int representing if the player chose heads or tails.
	Return Value: int, representing the next player, decided by the coin toss.
	Assistance Received: I read up on random numbers at the following article:
								https://stackoverflow.com/questions/59644856/measuring-time-to-generate-better-random-numbers-in-c
	********************************************************************* */
	public final int coin_toss()
	{
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:


//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		int flipped_coin = (int)(Math.random()*(2-1+1)+1);
		System.out.print("Player 2! Heads or Tails?");
		System.out.print("\n");
		System.out.print("1. Heads");
		System.out.print("\n");
		System.out.print("2. Tails");
		System.out.print("\n");
		int choice = validate_option_based_input(1, 2);

		if (choice == flipped_coin)
		{
			System.out.print("Player 1 goes first! They guessed correctly, the value is ");
			System.out.print(((flipped_coin == 1) ? "Heads" : "Tails"));
			System.out.print("\n");
			return 2;
		}

		else
		{
			System.out.print("Player 2 goes first! Player 1 guessed incorrectly the value is ");
			System.out.print(((flipped_coin == 1) ? "Heads" : "Tails"));
			System.out.print("\n");
			return 1;
		}


	}


	/* *********************************************************************
	Function Name: main_round
	Purpose: To act as the main round gameplay loop.
	Parameters: has_loaded_file, a boolean representing if a file has been loaded to start the round.
	Algorithm:
				 1. See if a file has been loaded or not. If so, start the main gameplay do while loop.
				 2. If not, see if the scores are the same. If they are, do a coin toss.
					 If not, whichever player with a higher score goes first.
				 3. The do while loop will continue until the round is over or the player quits.
				 4. In the do while loop, there is are 4 options a player can take:
					 (this in of itself is a do while loop which is broken on save or the option to continue.
					a) Save (which has the two options to save and continue or to save and quit)
					b) Take the turn (which simply continues onward)
					c) Quit the game, which breaks the loop and returns to main_game.
					d) Ask for advice, which starts another do while loop, where the player can ask for help regarding the following:
						i). Drawing
						ii. Melding
						iii.Discarding
						iv.Exit
					5. If the player continues onward, the round will decide who goes depending on the next player.
					6. In a player's turn, they are called with their play function which passes the enemy melds.
						Each turn, a player's transfer states are cleared, so the player can transfer wild cards once per turn.
					7. This goes on until a player quits, at which point the scores are output and tallied.
					8. At almost each of these steps, information regarding the round is output.
	Local variables:
		1. player1, a Player* that represents player 1.
		2. player2, a Player* that represents player 2.
		3. menu_choice, an int that represents the choice a player gives in a input based menu.
	Return Value: bool that returns if a player quit or not.
	Assistance Received: none
	********************************************************************* */
	public final boolean main_round(boolean has_loaded_file) throws FileNotFoundException
	{
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		Player player1 = players.get(0);
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		Player player2 = players.get(1);
		int menu_choice = 0;
		boolean round_is_over = false;

		if (has_loaded_file == false)
		{
			if (player1.get_score() == player2.get_score())
			{
				//the coin toss decides which player is going next.
				//so, we'll need to set the result for it's return.
				set_next_player(coin_toss());
			}

			else
			{
				if (player1.get_score() > player2.get_score())
				{
					next_player = 1;
				}
				else
				{
					next_player = 2;
				}
			}


			initial_draw();
			sort_players_hands();
		}
		output_round_info();

		do
		{
			do
			{
				menu_choice = pre_turn_menu();

				switch (menu_choice)
				{
					case 1:
					{
						System.out.print("How would you like to save?");
						System.out.print("\n");
						System.out.print("1. save and continue");
						System.out.print("\n");
						System.out.print("2. save and quit");
						System.out.print("\n");
						int save_choice = validate_option_based_input(1, 2);
						sort_players_hands();
						save_round();
						if (save_choice == 2)
						{
							return true;
						}
						break;
					}
					case 2:
						continue;
					case 3:
						return true;
					case 4:
					{
						int player_to_give_advice_pos = get_next_player() - 1;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
						Player player_to_give_advice = players.get(player_to_give_advice_pos);
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
						Player enemy_player = (player_to_give_advice_pos == 0) ? players.get(1) : players.get(0);
						Hand enemy_hand = enemy_player.get_player_hand();
						ArrayList<ArrayList<Card>> enemy_meld = enemy_hand.get_meld();

						player_to_give_advice.strategy(stock_and_discard, enemy_meld);

						break;
					}
				}
				//break if the option is to quit the game or to take the turn.
			} while (menu_choice != 3 && menu_choice != 2);
			output_round_info();

			if (round_is_over == true)
			{
				break;
			}


			output_round_info();
			// note that we subtract one as next_player is stored at starting at 1, not 0.
			//this was originally going to be done to say it's easier to say "player 1 and player 2"
			// but ended up being a terrible idea.
			Player enemy = players.get(next_player % (players.size()));
			Player current_player = players.get((next_player+1) % (players.size()));
			Hand enemy_hand = enemy.get_player_hand();
			ArrayList<ArrayList<Card>> enemy_meld = enemy_hand.get_meld();
			round_is_over = current_player.play(stock_and_discard, enemy_meld);
			player1.clear_transfer_states();
			//we'll need to increment next player to pass the turn.
			next_player = (next_player % (players.size()))+1;

			output_round_info();
		} while (round_is_over == false && menu_choice != 4);



		tally_score();
		output_round_info();

		return false;

	}


	/* *********************************************************************
	Function Name: initial_draw
	Purpose: Drawing the initial amount of cards.
	Parameters: none
	Algorithm:
				1. Draw 15 cards for each player. If the player drew a red three, draw again.
	Local variables:
		1. initial_draw_count: an int for storing how many cards are drawn in the initial draw of a card.
		2. drawn_card: a Card representing the Card drawn from the stock pile.
		3. drawn_string: the string that represents drawn_card in string form.
	Return Value: none.
	Assistance Received: none
	********************************************************************* */
	public final void initial_draw()
	{
		//for each player, draw 15 cards from the stockpile.
		int initial_draw_count = 15;
		for (int card_count = 0; card_count < players.size(); card_count++)
		{
			for (int card = 0; card < initial_draw_count; card++)
			{
				Card drawn_card = stock_and_discard.draw_from_stock();
				String drawn_string = drawn_card.get_card_string();

				if (drawn_string.equals("3H") || drawn_string.equals("3D"))
				{
					System.out.print("Lucky you! Player ");
					System.out.print(card_count + 1);
					System.out.print(", you got a red three on the initial draw!");
					System.out.print("\n");
					players.get(card_count).create_special_meld(new Card(drawn_card));
					card--;
				}
				else
				{
					players.get(card_count).add_to_hand(new Card(drawn_card));
				}
			}
		}
	}

	/* *********************************************************************
	Function Name: get_next_player
	Purpose: Drawing the initial amount of cards.
	Parameters: none
	Return Value: an int which returns the next player in the Round.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: int get_next_player() const
	public final int get_next_player()
	{
		return next_player;
	}


	/* *********************************************************************
	Function Name: set_next_player
	Purpose: Drawing the initial amount of cards.
	Parameters: next_player: an int which represents the next player
	Return Value: an int which returns the next player in the Round.
	Assistance Received: none
	********************************************************************* */
	public final void set_next_player(int next_player)
	{
		if (next_player == 1 || next_player == 2)
		{
			this.next_player = next_player;
		}
	}


	/* *********************************************************************
	Function Name: sort_player_hands
	Purpose: Drawing the initial amount of cards.
	Parameters: next_player: an int which represents the next player
	Return Value: an int which returns the next player in the Round.
	Assistance Received: none
	********************************************************************* */
	public final void sort_players_hands()
	{
		for (int plr_pos = 0; plr_pos < players.size(); plr_pos++)
		{
			players.get(plr_pos).sort_hand();
		}
	}


	/* *********************************************************************
	Function Name: pre_turn_menu
	Purpose: Displays the pre turn menu.
	Parameters: next_player: an int which represents the next player
	Return Value: an int which returns the next player in the Round.
	Algorithm:
				 1. Receive the type of the next player.
				 2. Depending on if the next player is human, a computer, or something else:
					a) If it's human, display a menu with 4 options (advice included) and validate input.
					b) If it's a computer, display a menu with 3 options (advice excluded) and validate input.
					c) If it's neither, return a number representing an error.
	Local variables:
						next_player: an int representing the numeric value of the next player.
						next_player_type: a string representing the next player's type, i.e. "Human" or "Computer", typically.
						error_number: an int representing an error code, -999 in this case.
	Assistance Received: none
	********************************************************************* */
	public final int pre_turn_menu()
	{
		int next_player = get_next_player() - 1;
		String next_player_type = players.get(next_player).get_player_type();
		int error_number = -999;
		if (next_player_type.equals("Human"))
		{

			System.out.print("1. Save the game");
			System.out.print("\n");
			System.out.print("2. Take the turn");
			System.out.print("\n");
			System.out.print("3. Quit the game");
			System.out.print("\n");
			System.out.print("4. Advice (ask for help)");
			System.out.print("\n");
			return validate_option_based_input(1, 4);
		}

		else if (next_player_type.equals("Computer"))
		{
			System.out.print("1. Save the game");
			System.out.print("\n");
			System.out.print("2. Take the turn");
			System.out.print("\n");
			System.out.print("3. Quit the game");
			System.out.print("\n");
			return validate_option_based_input(1, 3);
		}
		else
		{
			System.out.print("Unknown Player type! Can't display pre turn menu!");
			System.out.print("\n");
			return error_number;
		}

	}


	/* *********************************************************************
	Function Name: tally_score
	Purpose: Tallies the score for both players in the round and prints it out.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void tally_score()
	{
		int player_1_pos = 0;
		int player_2_pos = 1;
		boolean player_1_gone_out = players.get(player_1_pos).get_go_out_decision();
		boolean player_2_gone_out = players.get(player_2_pos).get_go_out_decision();

		players.get(0).strategy_meld(players.get(1).get_player_hand().get_meld(), "Remeld");
		players.get(1).strategy_meld(players.get(0).get_player_hand().get_meld(), "Remeld");

		int player_1_score = players.get(player_1_pos).get_player_hand().get_total_score(player_1_gone_out);
		int player_2_score = players.get(player_2_pos).get_player_hand().get_total_score(player_2_gone_out);

		if (player_1_gone_out)
		{
			player_1_score += 100;
		}
		else if (player_2_gone_out)
		{
			player_2_score += 100;
		}

		System.out.print("Player 1 (");
		System.out.print(players.get(player_1_pos).get_player_type());
		System.out.print(") ");
		System.out.print(" round score: ");
		System.out.print(player_1_score);
		System.out.print("\n");
		System.out.print("Player 2 (");
		System.out.print(players.get(player_2_pos).get_player_type());
		System.out.print(") ");
		System.out.print(" round score: ");
		System.out.print(player_2_score);
		System.out.print("\n");

		players.get(player_1_pos).add_to_score(player_1_score);
		players.get(player_2_pos).add_to_score(player_2_score);
	}


	/* *********************************************************************
	Function Name: set_round_number
	Purpose: Sets the round number.
	Parameters: round_number: an int representing what the next round number will be.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void set_round_number(int round_number)
	{
		if (round_number > 0)
		{
			this.round_number = round_number;
		}
	}

	/* *********************************************************************
	Function Name: get_players
	Purpose: To retrieve the players in a round.
	Parameters: none
	Return Value: a vector of player pointers representing each player, particularly a pointer to avoid object slicing.
	Assistance Received: none
	********************************************************************* */
	public final ArrayList<Player> get_players()
	{
		return new ArrayList<Player>(players);
	}


	/* *********************************************************************
	Function Name: output_round_info
	Purpose: Outputting all the round's info, including the round, score, player hand/melds, etc..
	Parameters: none
	Return Value: an int which returns the next player in the Round.
	Algorithm:
				1. Store variable to players, and gets the player hand from said variable.
				2. Prints the Round number, Player 1 type with all player information,
				3. Prints Player 2's information.
				4. Prints the stock pile and discard pile.
				5. Prints the next player.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: void output_round_info() const
	public final void output_round_info()
	{
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		Player player_1 = players.get(0);
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		Hand player_1_hand = player_1.get_player_hand();
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		Player player_2 = players.get(1);
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		Hand player_2_hand = player_2.get_player_hand();

		System.out.print("Round: ");
		System.out.print(round_number);
		System.out.print("\n");
		System.out.print("\n");


		player_1.print_player_type();
		System.out.print(":");
		System.out.print("\n");
		System.out.print("   Score: ");
		System.out.print(player_1.get_score());
		System.out.print("\n");

		System.out.print("   Hand: ");
		player_1_hand.print_hand();
		System.out.print("   Melds: ");
		player_1_hand.print_melds();
		System.out.print("\n");
		System.out.print("\n");


		player_2.print_player_type();
		System.out.print(":");
		System.out.print("\n");
		System.out.print("   Score: ");
		System.out.print(player_2.get_score());
		System.out.print("\n");

		System.out.print("   Hand: ");
		player_2_hand.print_hand();
		System.out.print("   Melds: ");
		player_2_hand.print_melds();
		System.out.print("\n");
		System.out.print("\n");

		System.out.print("Stock: ");
		stock_and_discard.print_stock_pile();
		System.out.print("Discard Pile: ");
		stock_and_discard.print_discard_pile();

		System.out.print("\n");
		System.out.print("Next Player: ");
		if (next_player == 1)
			player_1.print_player_type();
		else
			player_2.print_player_type();
		System.out.print("\n");

		System.out.print("\n");
	}



	/* *********************************************************************
	Function Name: load_game
	Purpose: To load a serialized file into a Round.
	Parameters: none
	Return Value: bool, representing if the file that has been loaded has been loaded successfully or not.
	Algorithm:
				1. Prompt for a file name input and then use a getline for a file name.
					If the user inputs 0, automatically cancel the action and return false, as no file was loaded.
					The same will happen if the program cannot find the file, except for an extra error message.
				2. Using filestreams, extract each line of the file into a vector of strings, and ignore empty lines in the file.
					In each line, using a lambda, the function will remove both extra white spaces on the interior of each
					string, as well as any extra white space on the left of the string. Right strings do not need to be of concern,
					as it'll simply not parse the statement further.
				3. Check if the file has exactly 12 lines, the exact amount needed to further parse the strings.
				4. Using a switch statement, call each function by the position of the line. For instance, if the line is position 1(0),
					load the round number.
				5. Close the file, outputting the round information of the file just loaded. Then, return true or false if all the loadings
					were successful.
	Local variables:
						 input_file_name: A string representing what the user has inputted the file name as.
						 file_to_load: an ifstream which assists in parsing each line of the serialized file.
						 extracted_string: a string extracted from a line loaded by file_to_load.
						 load_success: a bool representing if the loading process has been completed successfully.
						 lines_to_parse: a vector of strings representing all the extracted strings from the file.
	Assistance Received: none
	********************************************************************* */
	public final boolean load_game(String file) throws IOException
	{
		System.out.print("Please enter the file you wish to load. Say 0 to quit.");
		System.out.print("\n");
		String input_file_name;
		input_file_name = new Scanner(System.in).nextLine();

		if (input_file_name.equals("0"))
		{
			return false;
		}

		//std::ifstream file_to_load = new std::ifstream(input_file_name);
		File file_to_read = new File(input_file_name);

		String extracted_string;
		ArrayList<String> lines_to_parse = new ArrayList<String>();
		boolean load_success = true;

		if (file_to_read.exists())
		{
			FileReader fr = new FileReader(file_to_read);
			BufferedReader br = new BufferedReader (fr);

			//while (getline(file_to_load, extracted_string))
			while ((extracted_string = br.readLine())!= null)
			{
				//std::istringstream ss = new std::istringstream(extracted_string);

				if (extracted_string.indexOf(':') != -1)
				{

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
			fr.close();
		}
		else
		{
			System.out.print("File specified does not exists!");
			System.out.print("\n");
			return false;
		}

		if (lines_to_parse.size() != 12)
		{
			System.out.print("The load file has either too little or too much information!");
			System.out.print("\n");
			return false;
		}

		else
		{
			for (int line_pos = 0; line_pos < lines_to_parse.size(); line_pos++)
			{
				String line_to_parse = lines_to_parse.get(line_pos);
				switch (line_pos)
				{
					case 0:
						load_success = load_round_number(line_to_parse);
						break;
					case 1:
						load_success = load_player(line_to_parse);
						break;
					case 2:
						load_success = load_player_score(0, line_to_parse);
						break;
					case 3:
						load_success = load_hand(0, line_to_parse);
						break;
					case 4:
						load_success = load_meld(0, line_to_parse);
						break;
					case 5:
						load_success = load_player(line_to_parse);
						break;
					case 6:
						load_success = load_player_score(1, line_to_parse);
						break;
					case 7:
						load_success = load_hand(1, line_to_parse);
						break;
					case 8:
						load_success = load_meld(1, line_to_parse);
						break;
					case 9:
						load_success = load_stock(line_to_parse);
						break;
					case 10:
						load_success = load_discard(line_to_parse);
						break;
					case 11:
						load_success = load_next_player(line_to_parse);
						break;
				}

			}
		}

		output_round_info();


		return load_success;

		//https://stackoverflow.com/questions/3946558/c-read-from-text-file-and-separate-into-variable




	}

	/* *********************************************************************
	Function Name: load_round_number
	Purpose: To load a round number from a saved file the user inputted.
	Parameters: round_string, representing a string to parse containing the round number.
	Return Value: bool, representing if loading the round number was successful or not.
	Assistance Received: Since there still might be some information prior to the string being extracted, I used
							   this resource to assist in that: https://stackoverflow.com/questions/28163723/c-how-to-get-substring-after-a-character
	********************************************************************* */
	public final boolean load_round_number(String round_string)
	{

		String round_num = round_string.substring(round_string.indexOf(" ") + 1);
		if (round_num.length() == 0)
		{
			System.out.print("File load error: The round number is missing!");
			System.out.print("\n");
			return false;
		}

		if (round_num.matches("[0-9]+"))
		{
			int round_number_converted = Integer.parseInt(round_num);
			set_round_number(round_number_converted);
			return true;
		}

		else
		{
			System.out.print("File load error: the round number isn't numeric!");
			System.out.print("\n");
			return false;
		}



	}

	/* *********************************************************************
	Function Name: load_player
	Purpose: To load the type of player from a saved file the user inputted, by adding to the players vector.
	Parameters: player_string, a string representing the string to parse for the type of player.
	Return Value: bool, representing if loading the player type was successful or not.
	Assistance Received: none
	********************************************************************* */
	public final boolean load_player(String player_string)
	{
		if (player_string.equals("Human:"))
		{
			players.add(new Human());
			return true;
		}
		else if (player_string.equals("Computer:"))
		{
			players.add(new Computer());
			return true;
		}
		else
		{
			System.out.print("Unknown player type!");
			System.out.print("\n");
			return false;
		}

	}


	/* *********************************************************************
	Function Name: load_player_score
	Purpose: To load a player's score from a saved file the user inputted.
	Parameters:
				  score_string, representing a string to parse containing the player's score.
				  player, representing the player's position in players to assign the score to.
	Return Value: bool, representing if loading the player's score was successful or not.
	Assistance Received: Since there still might be some information prior to the string being extracted, I used
								this resource to assist in that: https://stackoverflow.com/questions/28163723/c-how-to-get-substring-after-a-character
	********************************************************************* */
	public final boolean load_player_score(int player, String score_string)
	{
		//https://stackoverflow.com/questions/28163723/c-how-to-get-substring-after-a-character
		String player_score = score_string.substring(score_string.indexOf(":") + 2);

		if (player_score.length() == 0)
		{
			System.out.print("File load error: The player score is missing!");
			System.out.print("\n");
			return false;
		}

		if (player_score.matches("[0-9]+"))
		{
			int player_score_converted = Integer.parseInt(player_score);
			players.get(player).set_player_score(player_score_converted);
			return true;
		}

		else
		{
			System.out.print("File load error: the player score isn't numeric!");
			System.out.print("\n");
			return false;
		}
	}


	/* *********************************************************************
	Function Name: string_is_card
	Purpose: To validate if a passed string is a valid Card that can be loaded.
	Parameters: card_string, a string representing a potential Card that is to be validated as a card.
	Return Value: bool, representing if card_string is a valid Card.
	Algorithm:
				 1. Check if the size of the card_string is 2, as that is how it's displayed in output. If not, return false,
					 as it's not a card.
				 2. Otherwise, break down the card string into rank and suite. Compare against a list of valid ranks and suites,
					 if the strings match up, then the card is valid, so return true. Otherwise, return false.
	Local variables:
				 player_1_pos and player_2_pos, ints which both represent player 1 and player 2's position in the players vector.
				 rank and suite, chars which both represent the rank and suite respectively.
				 rank_list and suite_list, strings which both represent the list of all valid chars for ranks and suits respectively.
	Assistance Received: none
	********************************************************************* */
	public final boolean string_is_card(String card_string)
	{
		if (card_string.length() != 2)
		{
			System.out.print("The selected value is not a valid card!");
			System.out.print("\n");
			return false;
		}

		else
		{
			int rank_pos = 0;
			int suit_pos = 1;
			char rank = card_string.charAt(rank_pos);
			char suite = card_string.charAt(suit_pos);
			String rank_list = "123456789XAJQK";
			String suite_list = "HDCS1234";

			if (rank_list.indexOf(rank) != -1 && suite_list.indexOf(suite) != -1)
			{
				return true;
			}
			else
			{
				System.out.print("The characters of the string do not contain a valid rank and suite!");
				System.out.print("\n");
				return false;
			}
		}
	}

	/* *********************************************************************
	Function Name: load_hand
	Purpose: To load a player's hand from a saved file that has been inputted.
	Parameters:
				  player, representing the player's position in players to assign the score to.
				  hand_string, representing a string to parse the player's hand
	Algorithm:
				  1. Create a substring to get rid of the last whitespace.
				  2. Using an istringstream, extract the contents of the string by using the extraction operator,
					  which will skip every time there's a whitespace.
				  3. Check if each string extracted is a valid card. If so, add to a vector emulating the hand.
					  if that is not the case, return false.
				  4. If all strings in the vector have been loaded, return true.
	Local variables:
				  hand: a string representing the variable hand_string minus an extra whitespace.
				  converted_hand: the vector of Cards that will be the player's hand from the loaded file.
				  hand_extractor: the isstringstream that will parse through the hand_string variable.
				  card_string: a string extracted from the hand_extractor which represents a particular Card.
	Return Value: bool, representing if loading the player's hand was successful or not.
	Assistance Received: Since there still might be some information prior to the string being extracted, I used
								this resource to assist in that: https://stackoverflow.com/questions/28163723/c-how-to-get-substring-after-a-character
	********************************************************************* */
	public final boolean load_hand(int player, String hand_string)
	{
		hand_string = hand_string.substring(hand_string.indexOf(":") + 2);
		ArrayList<Card> converted_hand = new ArrayList<Card>();
		//std::istringstream hand_extractor = new std::istringstream(hand);
		ArrayList<String> hand_extractor = new ArrayList<String>(Arrays.asList(hand_string.split("\\s+", -1)));
		for (String card_string : hand_extractor)
		{
//C++ TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			if (card_string.equals(""))
			{
				break;
			}

			if (string_is_card(card_string))
			{
				converted_hand.add(new Card(card_string));
			}

			else
			{
				return false;
			}

		} //while (hand_extractor != null);
		players.get(player).set_hand(new ArrayList<Card>(converted_hand));
		return true;
	}

	/* *********************************************************************
	Function Name: load_meld
	Purpose: To load a player's melds from a saved file that has been inputted.
	Parameters:
				  player, representing the player's position in players to assign the score to.
				  pre_meld_strings, representing a string to parse the player's hand
	Algorithm:
				  1. Create a substring to get rid of the last whitespace in pre_meld_string.
				  2. Using an instringstream with getline which delimits on the character ']', extract each individual meld.
					  additionally, remove each '[' and ']' character within the extracted string.
				  3. Using an istringstream, extract the contents of the extracted meld string by using the extraction operator,
					  which will skip every time there's a whitespace.
				  3. Check if each string extracted is a valid card. If so, add to a vector of Cards emulating a single meld, which will, after
					  iteration, add to a vector of vector of Cards emulating all the melds the player has.
					  if that is not the case, return false.
				  4. If all strings in the vector of vectors have been loaded successfully, return true.
	Local variables:
				  melds: a string representing the variable pre_meld_string minus an extra whitespace.
				  converted_meld: the vector of Cards that will be a single meld in the player's hand from the loaded file.
				  converted_meld_container: the vector of vector of Cards that stores converted_melds, representing the melded
				  meld_extractor: the isstringstream that will parse through pre_meld_string
				  meld_string: a string extracted from the meld_extractor which represents a particular meld.
				  card_string: a string extracted from the meld_extractor which represents a particular Card.

	Return Value: bool, representing if loading the player's melds was successful or not.
	Assistance Received: Since there still might be some information prior to the string being extracted, I used
								this resource to assist in that: https://stackoverflow.com/questions/28163723/c-how-to-get-substring-after-a-character
	********************************************************************* */
	public final boolean load_meld(int player, String pre_meld_string)
	{
		pre_meld_string = pre_meld_string.substring(pre_meld_string.indexOf(":") + 2);
		ArrayList<Card> converted_meld = new ArrayList<Card>();
		ArrayList<ArrayList<Card>> converted_meld_container = new ArrayList<ArrayList<Card>>();

		ArrayList<String> meld_extractor = new ArrayList<String>(Arrays.asList(pre_meld_string.split("]", -1)));
		//meld_extractor.remove(0);
		//std::istringstream meld_extractor = new std::istringstream(melds);
		//String meld_string;
		//String card_string;
		for  (String meld_string : meld_extractor)
		{

			if (meld_string.equals("") || meld_string.equals(" "))
			{
				break;
			}

			//https://www.tutorialspoint.com/how-to-remove-certain-characters-from-a-string-in-cplusplus
			//meld_string = meld_string.substring(0, std::remove(meld_string.iterator(), meld_string.end(), '[')) + meld_string.substring(std::remove(meld_string.iterator(), meld_string.end(), '[') + meld_string.end());
			//meld_string = meld_string.substring(0, std::remove(meld_string.iterator(), meld_string.end(), ']')) + meld_string.substring(std::remove(meld_string.iterator(), meld_string.end(), ']') + meld_string.end());
			//meld_string = meld_string.replaceAll("/[\\[\\]']+/g", "");
			meld_string = meld_string.substring(1, meld_string.length());
			if (meld_string.substring(0,1).equals("[") || meld_string.substring(0,1).equals(" "))
				meld_string = meld_string.substring(1, meld_string.length());
			//std::istringstream card_extractor = new std::istringstream(meld_string);
			ArrayList<String> card_extractor = new ArrayList<String>(Arrays.asList(meld_string.split(" ", -1)));

//C++ TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			for (String card_string : card_extractor)
			{
				if (card_string.equals(""))
				{
					break;
				}

				if (string_is_card(card_string))
				{
					converted_meld.add(new Card(card_string));
				}

				else
				{
					return false;
				}
			}

			converted_meld_container.add(new ArrayList<Card> (converted_meld));
			converted_meld.clear();
			card_extractor.clear();

		}
		players.get(player).set_meld(new ArrayList<ArrayList<Card>>(converted_meld_container));
		meld_extractor.clear();
		return true;
	}


	/* *********************************************************************
	Function Name: load_stock
	Purpose: To load a Round's stock pile from a saved file that has been inputted.
	Parameters:
				  stock_string, representing a string to parse the Round's stock pile.
	Algorithm:
				  1. Create a substring to get rid of the last whitespace.
				  2. Using an istringstream, extract the contents of the string by using the extraction operator,
					  which will skip every time there's a whitespace.
				  3. Check if each string extracted is a valid card. If so, add to a vector of Cards emulating the stock.
					  if that is not the case, return false.
				  4. If all strings in the vector have been loaded, return true.
	Local variables:
				  stock: a string representing the variable stock_string minus an extra whitespace.
				  converted_stock: the vector of Cards that will be the player's hand from the loaded file.
				  stock_extractor: the isstringstream that will parse through the hand_string variable.
				  card_string: a string extracted from the hand_extractor which represents a particular Card.
	Return Value: bool, representing if loading the Round's stock pile was successful or not.
	Assistance Received: Since there still might be some information prior to the string being extracted, I used
								this resource to assist in that: https://stackoverflow.com/questions/28163723/c-how-to-get-substring-after-a-character
	********************************************************************* */
	public final boolean load_stock(String stock_string)
	{
		stock_string = stock_string.substring(stock_string.indexOf(":") + 2);
		ArrayList<Card> converted_stock = new ArrayList<Card>();
		//std::istringstream hand_extractor = new std::istringstream(hand);
		ArrayList<String> stock_extractor = new ArrayList<String>(Arrays.asList(stock_string.split("\\s+", -1)));
		for (String card_string : stock_extractor)
		{
//C++ TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			if (card_string.equals(""))
			{
				break;
			}

			if (string_is_card(card_string))
			{
				converted_stock.add(new Card(card_string));
			}

			else
			{
				return false;
			}

		} //while (hand_extractor != null);
		stock_and_discard.set_stock_pile(new ArrayList<Card>(converted_stock));
		return true;
	}

	/* *********************************************************************
	Function Name: load_discard
	Purpose: To load a Round's discard pile from a saved file that has been inputted.
	Parameters:
				  discard_string, representing a string to parse the Round's discard pile.
	Algorithm:
				  1. Create a substring to get rid of the last whitespace.
				  2. Using an istringstream, extract the contents of the string by using the extraction operator,
					  which will skip every time there's a whitespace.
				  3. Check if each string extracted is a valid card. If so, add to a vector of Cards emulating the discard pile.
					  if that is not the case, return false.
				  4. If all strings in the vector have been loaded, return true.
	Local variables:
				  discard: a string representing the variable stock_string minus an extra whitespace.
				  converted_discard: the vector of Cards that will be the player's hand from the loaded file.
				  discard_extractor: the isstringstream that will parse through the hand_string variable.
				  card_string: a string extracted from the hand_extractor which represents a particular Card.
	Return Value: bool, representing if loading the Round's discard pile was successful or not.
	Assistance Received: Since there still might be some information prior to the string being extracted, I used
								this resource to assist in that: https://stackoverflow.com/questions/28163723/c-how-to-get-substring-after-a-character
	********************************************************************* */
	public final boolean load_discard(String discard_string)
	{
		discard_string = discard_string.substring(discard_string.indexOf(":") + 2);
		ArrayList<Card> converted_discard = new ArrayList<Card>();
		//std::istringstream hand_extractor = new std::istringstream(hand);
		ArrayList<String> discard_extractor = new ArrayList<String>(Arrays.asList(discard_string.split("\\s+", -1)));
		for (String card_string : discard_extractor)
		{
//C++ TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
			if (card_string.equals(""))
			{
				break;
			}

			if (string_is_card(card_string))
			{
				converted_discard.add(new Card(card_string));
			}

			else
			{
				return false;
			}

		} //while (hand_extractor != null);
		stock_and_discard.set_discard_pile(new ArrayList<Card>(converted_discard));
		return true;
	}

	/* *********************************************************************
	Function Name: load_next_player
	Purpose: To load a Round's next player from a saved file the user inputted.
	Parameters:
				  next_player_str, representing a string to parse containing the next player.
	Algorithm:
				 1. Get rid of the trailing whitespace in front of next_player_str and reassign it to itself.
				 2. If the next player is Human or Computer, set it to be as such by adding the player position plus 1. Then return true.
				 3. If the next player is neither, return false.
	Local variables: none
	Return Value: bool, representing if loading the next player was successful or not.
	Assistance Received: Since there still might be some information prior to the string being extracted, I used
								this resource to assist in that: https://stackoverflow.com/questions/28163723/c-how-to-get-substring-after-a-character
	********************************************************************* */
	public final boolean load_next_player(String next_player_str)
	{

		next_player_str = next_player_str.substring(next_player_str.indexOf(" ") + 1);

		if (next_player_str.equals("Player: Human"))
		{
			for (int player_pos = 0; player_pos < players.size(); player_pos++)
			{
				if (players.get(player_pos).get_player_type().equals("Human"))
				{
					set_next_player(player_pos + 1);
					break;
				}
			}
			return true;
		}
		else if (next_player_str.equals("Player: Computer"))
		{
			for (int player_pos = 0; player_pos < players.size(); player_pos++)
			{
				if (players.get(player_pos).get_player_type().equals("Computer"))
				{
					set_next_player(player_pos + 1);
					break;
				}
			}
			return true;
		}
		else
		{
			System.out.print("File loading error: next player is not Human or Computer!");
			System.out.print("\n");
			return false;
		}
	}

	/* *********************************************************************
	Function Name: set_stock_pile
	Purpose: A setter for the Round's stock pile.
	Parameters:
				  score_pile, representing a vector of Cards for the data member to be set as.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void set_stock_pile(ArrayList<Card> stock_pile)
	{
		stock_and_discard.set_stock_pile(new ArrayList<Card>(stock_pile));
	}

	/* *********************************************************************
	Function Name: set_discard_pile
	Purpose: A setter for the Round's discard pile.
	Parameters:
				  discard_pile, representing a vector of Cards for the data member to be set as.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void set_discard_pile(ArrayList<Card> discard_pile)
	{
		stock_and_discard.set_discard_pile(new ArrayList<Card>(discard_pile));
	}

	/* *********************************************************************
	Function Name: save_round
	Purpose: Serializes the information of the current round and saves it to a file.
	Parameters:
				  discard_pile, representing a vector of Cards for the data member to be set as.
	Return Value: none
	Assistance Received: I primarily looked into how to reroute output buffers to files.
	https://stackoverflow.com/questions/4810516/c-redirecting-stdout
	********************************************************************* */
	public final void save_round() throws FileNotFoundException
	{
		String file_name = new String();
		System.out.print("What would you like to save your file as? Say quit to cancel saving.");
		System.out.print("\n");

		if (file_name.equals("quit"))
		{
			return;
		}

		file_name = new Scanner(System.in).nextLine();

		PrintStream file_output = new PrintStream(new File(file_name));
		PrintStream console = System.out; //save the old buffer

		System.setOut(file_output);//reroute output to a file with a  buffer
		output_round_info();
		System.setOut(console);//reset the buffer to standard output

	}





	//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
	//int validate_option_based_input(int lower_bound, int upper_bound);
	//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
	//int validate_option_based_input(int lower_bound, int upper_bound, boolean special_option);



/* *********************************************************************
Function Name: validate_option_based_input
Purpose: Validating option based input in a lower and upper bound.
Parameters:
			  lower_bound, an int that represents the lower bound of acceptable input.
			  upper_bound, an int that represents the upper bound of acceptable input.
Algorithm:
			 1). Use a do-while loop to simulate continous input which will break when input is valid.
			     Within this input, prior to input,
				  the cin buffer is cleared and all prior input(including newlines) is ignored.
			 2). There are cases in which the input will always be false:
						a) The input is not within upper or lower bounds.
						b) The input is not numeric.
			 3). If the input is none of these cases, then convert the string into an integer and store it, and set the input to be valid.
			 4). Return the valid inputted choice.


Local variables:


Return Value: none
Assistance Received: none
********************************************************************* */


	//we'll need to have this as out of class function so as to avoid dependancy on the Game class
	//when we call this function in the round Class.
	public static int validate_option_based_input(int lower_bound, int upper_bound)
	{
		String input;
		boolean is_valid = false;
		int converted_option = 0;
		do
		{

			Scanner scan = new Scanner(System.in);
			input = scan.nextLine();
			input = input.replaceAll(" ","");


			if (input.matches("[0-9]+"))
			{
				converted_option = Integer.parseInt(input);

				if (converted_option >= lower_bound && converted_option <= upper_bound)
				{
					is_valid = true;
				}
				else
				{
					System.out.print("Input Error: selected number is not an option.");
					System.out.print("\n");
				}
			}
			else
			{
				System.out.print("Input Error: inputted string is not numeric.");
				System.out.print("\n");
			}

		} while (is_valid != true);

		return converted_option;
	}

/* *********************************************************************
Function Name: validate_option_based_input
Purpose: Validating option based input in a lower and upper bound.
Parameters:
			  lower_bound, an int that represents the lower bound of acceptable input.
			  upper_bound, an int that represents the upper bound of acceptable input.
			  special_option, a bool that tells if the function should accept -1 as an acceptable input.
Algorithm:
			 1). Use a do-while loop to simulate continous input which will break when input is valid.
				  Within this input, prior to input,
				  the cin buffer is cleared and all prior input(including newlines) is ignored.
			 2). There are cases in which the input will always be false:
						a) The input is not within upper or lower bounds.
						b) The input is not numeric.
			 3). If the input is none of these cases or is -1,
			 then convert the string into an integer and store it, and set the input to be valid.
			 4). Return the valid inputted choice.


Local variables:


Return Value: none
Assistance Received: Some parts of the input validation came from past projects of mine,
such as the VC8000 computer, https://github.com/pw45000/VC-8000.
********************************************************************* */


	public static int validate_option_based_input(int lower_bound, int upper_bound, boolean special_option)
	{
		String input;
		boolean is_valid = false;
		int converted_option = 0;
		do
		{
			Scanner scan = new Scanner(System.in);
			input = scan.nextLine();
			input = input.replaceAll(" ","");

			if (input.equals("-1"))
			{
				return -1;
			}


			if (input.matches("[0-9]+"))
			{
				converted_option = Integer.parseInt(input);

				if (converted_option >= lower_bound && converted_option <= upper_bound)
				{
					is_valid = true;
				}
				else
				{
					System.out.print("Input Error: selected number is not an option.");
					System.out.print("\n");
				}
			}
			else
			{
				System.out.print("Input Error: inputted string is not numeric.");
				System.out.print("\n");
			}

		} while (is_valid != true);

		return converted_option;
	}



	private Deck stock_and_discard = new Deck();
	private int round_number;
	private int next_player;
	private ArrayList<Player> players = new ArrayList<Player>();
}





