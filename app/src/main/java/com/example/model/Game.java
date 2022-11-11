package com.example.model;

import java.util.*;
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
//class Card;
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Deck;
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Hand;
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Player;
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Computer;
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Round;
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Human;



public class Game implements Closeable
{

	/* *********************************************************************
	Function Name: Game
	Purpose: The default Game constructor. 
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public Game()
	{
		round = 0;
		players.ensureCapacity(2);

	}

	/* *********************************************************************
	Function Name: Game
	Purpose: The Game destructor. Deletes any new players allocated on the heap.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void close()
	{
		if (players.size() > 0)
		{
			players.clear();
		}
	}


	/* *********************************************************************
	Function Name: Game
	Purpose: The Game copy constructor. Made to satisfy the rule of 3.
	Parameters: other_game: a const reference to another Game Class from which to be copied.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public Game(final Game other_game)
	{
		players = new ArrayList<Player>(other_game.players);
		round = other_game.round;
	}


	/* *********************************************************************
	Function Name: main_menu
	Purpose: The function which represents the main menu of the game.
	Parameters: none
	Algorithm: 
				1. Creates a do-while loop to simulate a menu playing until a player quits. 
					In this case, the case to break this loop will be 3. 
				2. For each iteration of this do while loop, display a prompt with 3 options: 
					New Game, Load Game, Exit, and validate a range based input. 
				3. Depending on each choice, either:
					a) make a new game by choose_player_type
					b) Attempt to load a round, and if successful, proceed to the loaded round.
					c) Exit the game.
	Local variables: 
						 choice, an int which represents the input of the user.
						 loading_Round, a Round class which represents a new round from which will be loaded into.
						 load_success, a bool which represents if the new round is successfully loaded.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void main_menu() throws IOException
	{
		int choice = 0;

		//to avoid stopping after the first return to main menu.
		do
		{
			System.out.print("Welcome to Canasta! Please pick an option:");
			System.out.print("\n");
			System.out.print("1. New Game ");
			System.out.print("\n");
			System.out.print("2. Load Game");
			System.out.print("\n");
			System.out.print("3. Exit");
			System.out.print("\n");
			choice = validate_option_based_input(1, 3);
			switch (choice)
			{
			case 1:
				choose_player_type();
				break;
			case 2:
			{
				Round loading_round = new Round();
				boolean load_success = true;
						//loading_round.load_game();
				if (load_success)
				{
					players = new ArrayList<Player>(loading_round.get_players());
					main_game(loading_round);
				}
				else
				{
					System.out.print("Failed to load file or the user inputted 0 to quit!");
					System.out.print("\n");
				}
				break;
			}
			case 3:
			{
				return;
			}
			default:
				System.out.print("Unknown behavior: unknown option.");
				System.out.print("\n");
			}
		} while (choice != 3);

	}


	/* *********************************************************************
	Function Name: choose_player_type
	Purpose: The function which represents choosing the player type after selecting new game.
	Parameters: none
	Algorithm:
				1. Creates a do-while loop to simulate a menu playing until a player quits.
					In this case, the case to break this loop will be 3.
				2. For each iteration of this do while loop, display a prompt with 3 options:
					Player vs Player, Player vs Computer, Exit to Main Menu, and validate a range based input.
				3. Depending on each choice, either:
					a) Make a new game by pushing two new Humans to players and then call main_game to play the game.
					b) Make a new game by pushing one Human and one Computer(AI) to players and then call main_game to play the game.
					c) Exit to main menu.
	Local variables: 
						 choice: an integer which represents the chosen input.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void choose_player_type() throws FileNotFoundException
	{
		String input_string;



		int choice;
		//to avoid stopping after the first return to main menu.
		do
		{
			//because we have 3 options.
			System.out.print("Welcome to Canasta! Please select your preferred game type: ");
			System.out.print("\n");
			System.out.print("1. Player vs Player (PVP) ");
			System.out.print("\n");
			System.out.print("2. Player vs Computer (PVE)");
			System.out.print("\n");
			System.out.print("3. Exit to Main Menu");
			System.out.print("\n");
			choice = validate_option_based_input(1, 3);
			switch (choice)
			{
			case 1:
			{
				//bypass the error of case transfer skipping initialization

				players.add(new Human());
				players.add(new Human());

				main_game();


				return;
			}
			case 2:
				players.add(new Computer());
				players.add(new Human());

				main_game();
				return;
			case 3:
				return;
			default:
				System.out.print("Unknown behavior: unknown option.");
				System.out.print("\n");
				break;
			}
		} while (choice != 3);
	}


	/* *********************************************************************
	Function Name: main_game
	Purpose: The function which acts the main game loop, including taking care of score, players, Rounds, etc...
	Parameters: none
	Algorithm:
				1. Create a do-while loop to enclose input regarding whether to play another round or not.
					this loop will break if the player has quit or the player chose not to player another around.
				2. Create a new Round object, constructing with the Game's players and tracked round number.
				3. Call the round object with false, since a round hasn't been loaded. The round will return if the player has quit.
				4. If the player hasn't quit and the round is over, ask if they want to play another round, and validate the prompted
					inputed choice. Each time, each player's hand is cleared.
				5. Once the player decides to end the round or the round ends, see if they quit. If not, display who won.
				6. Clear the players and delete the memory allocated on the heap representing the players.
	Local variables:
						 choice: an integer which represents the chosen input in the input loop.
						 round_number: an integer representing the current round number.
						 player_1: a Player* representing the first player.
						 player_2: a Player* representing the second player.
						 has_quit: a bool representing if the player quit or not.
	
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void main_game() throws FileNotFoundException
	{
		int choice = 0;
		int round_number = 0;
		Player player_1 = players.get(0);
		Player player_2 = players.get(1);
		boolean has_quit = false;

		do
		{
			round_number++;
			Round game_round = new Round(players, round_number);
			has_quit = game_round.main_round(false);
			if (!has_quit)
			{
				System.out.print("Would you like to another round?");
				System.out.print("\n");
				System.out.print("1. Yes");
				System.out.print("\n");
				System.out.print("2. No");
				System.out.print("\n");
				choice = validate_option_based_input(1, 2);
			}
			player_1.clear_hand_and_meld();
			player_2.clear_hand_and_meld();
		} while (choice != 2 && has_quit == false);

		if (!has_quit)
		{
			decide_winner();
		}

		//players.get(0) = null;
		//players.get(1) = null;
		players.clear();

	}


	/* *********************************************************************
	Function Name: main_game
	Purpose: The function which acts the main game loop, including taking care of score, players, Rounds, etc...
	Parameters: loaded_round, a Round reference to the round currently loaded in the main menu of Game.
	Algorithm:
				1. Create a do-while loop to enclose input regarding whether to play another round or not.
					this loop will break if the player has quit or the player chose not to player another around.
				2. Create a new Round object, constructing with the Game's players and tracked round number.
				3. Either the following will happen based on the loop_count being 0(loaded) or >0 (past loaded round): 
						a) Call the round object with false, since a round hasn't been loaded. The round will return if the player has quit.
						b) Call the round object with true, since a round has been loaded. The round will return if a player has quit.
				4. If the player hasn't quit and the round is over, ask if they want to play another round, and validate the prompted
					inputed choice. Each time, each player's hand is cleared.
				5. Once the player decides to end the round or the round ends, see if they quit. If not, display who won.
				6. Clear the players and delete the memory allocated on the heap representing the players.
	Local variables:
						 choice: an integer which represents the chosen input in the input loop.
						 round_number: an integer representing the current round number.
						 loop_count: an integer representing how many times a round has been played since being loaded.
						 player_1: a Player* representing the first player.
						 player_2: a Player* representing the second player.
						 has_quit: a bool representing if the player quit or not.
	
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void main_game(Round loaded_round) throws FileNotFoundException
	{
		int choice = 0;
		int round_number = 0;
		int loop_count = 0;

		Player player_1 = players.get(0);
		Player player_2 = players.get(1);
		boolean has_quit = false;

		do
		{
			if (loop_count == 0)
			{
				has_quit = loaded_round.main_round(true);
			}
			else
			{
				Round game_round = new Round(players, round_number);
				has_quit = game_round.main_round(false);
			}

			if (!has_quit)
			{
				System.out.print("Would you like to another round?");
				System.out.print("\n");
				System.out.print("1. Yes");
				System.out.print("\n");
				System.out.print("2. No");
				System.out.print("\n");
				choice = validate_option_based_input(1, 2);
				round_number++;
				loop_count++;
			}
			player_1.clear_hand_and_meld();
			player_2.clear_hand_and_meld();

		} while (choice != 2 && has_quit == false);

		if (!has_quit)
		{
			decide_winner();
		}

		//players.get(0) = null;
		//players.get(1) = null;
		players.clear();
	}


	/* *********************************************************************
	Function Name: decide_winner
	Purpose: Gets the total score for each player and then annouces the winner.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */

	public final void decide_winner()
	{
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		Player player_1 = players.get(0);
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		Player player_2 = players.get(1);

		int player_1_score = player_1.get_score();
		int player_2_score = player_2.get_score();

		if (player_1_score > player_2_score)
		{
			System.out.print("Victory for life: Player 1 (");
			System.out.print(player_1.get_player_type());
			System.out.print(") with a score of ");
			System.out.print(player_1_score);
			System.out.print("\n");
			System.out.print("Dejected Loser: Player 2 (");
			System.out.print(player_2.get_player_type());
			System.out.print(") with a score of ");
			System.out.print(player_2_score);
			System.out.print("\n");
		}

		else
		{
			System.out.print("Victory for life: Player 2 (");
			System.out.print(player_2.get_player_type());
			System.out.print(") with a score of ");
			System.out.print(player_2_score);
			System.out.print("\n");
			System.out.print("Dejected Loser: Player 1 (");
			System.out.print(player_1.get_player_type());
			System.out.print(") with a score of ");
			System.out.print(player_1_score);
			System.out.print("\n");
		}
	}

	

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
	
	
	
	
	
	
	private ArrayList<Player> players = new ArrayList<Player>();
	private int round;
}