package com.example.myapplication;

import android.os.Build;

import java.util.*;
import java.util.stream.Collectors;

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
//class Human;
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Player;



public class Human extends Player
{
	public Human(Human human) {
		this.set_player_score(human.get_score());
		this.set_hand(new ArrayList<Card> (human.get_player_hand().get_hand_container()));
		this.set_meld(new ArrayList<ArrayList<Card>>(human.get_player_hand().get_meld()));
	}
	
	public Human(int score, ArrayList<Card> hand,  ArrayList<ArrayList<Card>> melds) {
		this.set_player_score(score);
		this.set_hand(hand);
		this.set_meld(melds);
	}

	public Human() {
		super();
	}


	/* *********************************************************************
	Function Name: play
	Purpose: The main gameplay loop for each player. 
	Parameters: 
				 draw_decks: a Deck reference which represents the discard and stock pile from the Round.
				 enemy_melds: a vector of vectors of Card which represents the enemy's melds.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	@Override
	public boolean play(Deck draw_decks, ArrayList<ArrayList<Card>> enemy_meld)
	{
		boolean immeadiate_break;

		immeadiate_break = go_out();
		if (immeadiate_break)
		{
			return true;
		}

		immeadiate_break = draw(draw_decks);
		if (immeadiate_break)
		{
			return true;
		}

		meld(new ArrayList<ArrayList<Card>>(enemy_meld));
		immeadiate_break = go_out();
		if (immeadiate_break)
		{
			return true;
		}

		discard(draw_decks, new ArrayList<ArrayList<Card>>(enemy_meld));
		immeadiate_break = go_out();
		if (immeadiate_break)
		{
			return true;
		}

		return false;
	}


	/* *********************************************************************
	Function Name: draw
	Purpose: To simulate a draw phase for a Human, input validation included.
	Parameters: 
				  draw_decks, a Deck reference representing the discard and stock pile from Round.
	Algorithm:
				 1. Print out the discard and relevant Human hand information so the player can make an informed decision. 
				 2. If the stock pile is empty and the discard pile is frozen, return true.
				 3. Use a do while loop to validate input.   
				 4. If the player can draw from the stock pile and chose to do so, 
					 then keep drawing until the player draws a card that is not a red three and adds to the Hand.
					 Set has_completed_draw to true.
				 5. If the player can draw from the discard pile, then pick up the whole discard pile,
				    then pick up the discard pile and add to Hand. Set has_completed_draw to true.
				 6. If none of the criteria are met above, continue and print an error message.
				 7. If the player cannot draw from both piles, end the game. 
				 8. Continue until has_completed_draw is set to true.
				 9. If successful, it'll return false, meaning that the game shouldn't end.
	Local variables:
						has_completed_draw: a bool representing if the draw phase has been completed.
						hand_for_comparisons: a Hand that is used to gather information regarding the player's hand, but not to add to. 
						draw_card: a Card that is drawn from the stock pile.
						picked_up_discard: a vector of Cards representing the entire discard pile.
						top_of_discard: a Card representing the top Card on the discard pile, to see if the discard pile is drawable.
						should_discard: a bool representing if the Human is able to pick up the discard pile.
	Return Value: bool, representing if the game should end immeadiately due to an inability to draw.
	Assistance Received: none
	********************************************************************* */

	@Override
	public boolean draw(Deck draw_decks)
	{


		boolean has_completed_draw = false;
		Hand hand_for_comparisons = get_player_hand();
		Card drawn_card = new Card();


		if (draw_decks.stock_is_empty() && draw_decks.get_discard_is_frozen())
		{
			System.out.print("Can't draw, the stock pile is empty and the discard pile is frozen! The round is over!");
			System.out.print("\n");
			return true;
		}

		do
		{
			System.out.print("Discard Pile: ");
			System.out.print("\n");
			draw_decks.print_discard_pile();

			temp_print_hand();
			System.out.print("DRAW PHASE: Pick a Deck to draw from. ");
			System.out.print("\n");
			System.out.print("1. Stock");
			System.out.print("\n");
			System.out.print("2. Discard ");
			System.out.print("\n");
			System.out.print("3. Advice");
			System.out.print("\n");

			int choice = validate_option_based_input(1, 3);


			if (choice == 1)
			{
				if (!draw_decks.stock_is_empty())
				{
					//keeps drawing a card until the card isn't a red three.
					do
					{
						drawn_card.copyFrom(draw_decks.draw_from_stock());
						add_to_hand(new Card(drawn_card));
						purge_red_threes();
					} while (drawn_card.is_red_three() && !draw_decks.stock_is_empty());
					has_completed_draw = true;
				}
				else
				{
					System.out.print("The stock pile is empty! Try drawing from the Discard pile.");
					System.out.print("\n");
				}
			}

			else if (choice == 3)
			{
				advice_draw(draw_decks);
			}

			else
			{
				if (draw_decks.get_discard_is_frozen())
				{
					System.out.print("The discard pile is frozen! Try drawing from the Stock pile.");
					System.out.print("\n");
				}
				else
				{
					Card top_of_discard = draw_decks.get_top_discard_pile();
					boolean should_discard = (hand_for_comparisons.is_meldable(new Card(top_of_discard)) || hand_for_comparisons.is_meldable_with_melds(new Card(top_of_discard)));
					if (should_discard)
					{
						ArrayList<Card> picked_up_discard = draw_decks.draw_from_discard();
						add_to_hand(new ArrayList<Card>(picked_up_discard));
						purge_red_threes();
						has_completed_draw = true;
					}
					else
					{
						System.out.print("No cards in your hand can meld or lay off with the top of the discard pile ");
						System.out.print(top_of_discard.get_card_string());
						System.out.print("\n");
					}
				}
			}
		} while (!(draw_decks.both_piles_are_empty()) && !(has_completed_draw) || (!draw_decks.stock_is_empty() && !draw_decks.get_discard_is_frozen() && !(has_completed_draw)));

		if (has_completed_draw == true)
		{
			return false;
		}
		else
		{
			System.out.print("Either both piles are empty, or the discard is frozen and the stock pile is empty!");
			System.out.print("\n");
			System.out.print("Because of this, the Round has ended!");
			System.out.print("\n");
			return true;
		}

	}


	/* *********************************************************************
	Function Name: meld
	Purpose: To simulate the meld phase for the Human player, input validation included.
	Parameters:
				 draw_decks: a Deck reference representing the discard and stock pile from Round.
				 enemy_melds: a vector of vectors of Cards representing the enemy's melds.
	Algorithm:
				1. Print out relevant information, such as the hand, and the enemy's melds, to ensure the player can make an informed decision.
				2. Use a do while loop to validate player input. Additionally, there is a do while loop in each subphase to 
				   ensure the user doesn't get locked out of a phase just because they made a mistake. 
				3. Based on the options chosen, do: 
					a) Create a meld by getting 3 comma seperated positions(input validated) and call the create_meld function using player_hand.
					b) To add onto a pre-existing meld, validate an option first by getting the size of the hand, and providing options bounded
						by the size of the hand, as well as providing 0 to quit. Then, do a similar process for melds, then, if all is well, 
						call lay_off using player_hand.
					c) To transfer cards, choose a meld bounded by a process similar to the above but by bounding the input by first with the size
						of the meld container, ensuring the meld chosen isn't too small (size<=3). 
						Then, gather a list of all transferable wild cards. If there are none, redo the loop. Then, get the user 
						to input another meld, the other to transfer to and ensure there are no more than 3 wild cards.
					d) Exit the menu, ending the meld phase by setting still_melding to false.
	Local variables:
				option: an int representing what the player chose in the menu.
				operation_success: a bool which represents if a subphase was run successfully (i.e. creating a meld).
				still_melding: a bool which represents if the meld phase is still 
				card_pos: an int representing the position of a chosen card in the hand.
				wild_pos: an int representing the position of a chosen wild card in a meld or hand.
				meld_pos: an int representing the meld to transfer to when transferring a wild card.
				card_to_lay_off: a Card representing which card is to be added to another meld.
				card_to_transfer: a Card representing which card is to be transferred from one meld to another.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	@Override
	public void meld(ArrayList<ArrayList<Card>> enemy_melds)
	{
		int option = 0;
		boolean operation_success = false;
		boolean still_melding = true;
		do
		{
			print_enemy_meld(new ArrayList<ArrayList<Card>>(enemy_melds));
			temp_print_hand();
			System.out.print("MELD PHASE: Pick an option.");
			System.out.print("\n");
			System.out.print("1. Create a Meld");
			System.out.print("\n");
			System.out.print("2. Add to a pre-existing Meld ");
			System.out.print("\n");
			System.out.print("3. Transfer wildcards from one meld to another");
			System.out.print("\n");
			System.out.print("4. End Meld Phase");
			System.out.print("\n");
			System.out.print("5. Advise");
			System.out.print("\n");
			option = validate_option_based_input(1, 5);

			switch (option)
			{
			case 1:
			{
				temp_print_hand();
				operation_success = false;
				ArrayList<Card> potentenial_meld = validate_comma_input();
				if (potentenial_meld.size() == 0)
				{
					break;
				}
				else
				{
					//operation_success = create_meld(new ArrayList<Card>(potentenial_meld));
					operation_success =true;

					temp_print_hand();
				}
				if (!operation_success)
				{
					System.out.print("The meld operation was unsuccessful! Please see the above output^");
					System.out.print("\n");
				}
				break;
			}
			case 2:
			{
				do
				{
					temp_print_hand();
					operation_success = false;
					int card_pos = 0;
					int meld_pos = 0;
					Card card_to_lay_off = new Card();
					Hand player_hand = get_player_hand();

					if (player_hand.get_size_of_meld() != 0)
					{

						System.out.print("ADD TO EXISTING MELD: Pick a card position from 1 to ");
						System.out.print(player_hand.get_size_of_hand());
						System.out.print(" to add to a pre-existing meld. Say 0 to cancel the operation");
						System.out.print("\n");
						card_pos = validate_option_based_input(0, player_hand.get_size_of_hand()) - 1;

						//this is if the user enters 0.
						if (card_pos == -1)
						{
							break;
						}

						card_to_lay_off.copyFrom(player_hand.get_card_from_hand(card_pos));

						System.out.print("Great, now pick a meld position from 1 to ");
						System.out.print(player_hand.get_size_of_meld());
						System.out.print(" to add onto. Enter 0 cancel the operation.");
						System.out.print("\n");
						meld_pos = validate_option_based_input(0, player_hand.get_size_of_meld()) - 1;

						//this is if the user enters 0.
						if (meld_pos == -1)
						{
							break;
						}

						//operation_success = lay_off(new Card(card_to_lay_off), meld_pos);
						operation_success = true;
						if (!operation_success)
						{
							System.out.print("The add onto operation was unsuccessful! Please see the above output^");
							System.out.print("\n");
						}
					}
					else
					{
						System.out.print("Meld Error: you have no melds to add onto!");
						System.out.print("\n");
						break;
					}
				} while (operation_success != true);
				temp_print_hand();
				break;
			}
			case 3:
			{
				temp_print_hand();
				operation_success = false;
				int wild_pos = 0;
				int wild_card_pos;
				int meld_pos = 0;
				Card card_to_transfer = new Card();
				Hand player_hand = get_player_hand();
				do
				{
					if (player_hand.get_size_of_meld() != 0)
					{

						System.out.print("TRANSFER WILD CARDS: Pick a meld from 1 to ");
						System.out.print(player_hand.get_size_of_meld());
						System.out.print(" to extract the wild card's position. Say 0 to cancel the operation");
						System.out.print("\n");
						wild_pos = validate_option_based_input(0, player_hand.get_size_of_meld()) - 1;

						//this is if the user enters 0.
						if (wild_pos == -1)
						{
							break;
						}


						ArrayList<Card> wild_meld = player_hand.get_wild_cards(wild_pos);

						if (wild_meld.size() == 0)
						{
							System.out.print("The meld you chose has no wild cards that can be transfered!");
							System.out.print("\n");
							break;
						}

						else
						{
							System.out.print("Great, now pick a card from the following list to transfer Say 0 to cancel");
							System.out.print("\n");
							//card_to_transfer = player_hand.get_card_from_hand(meld_pos);
							player_hand.print_all_wilds_of_meld(wild_pos);
							wild_card_pos = validate_option_based_input(0, wild_meld.size()) - 1;
							if (wild_card_pos == -1)
							{
								break;
							}
							//extracts the specific card we want from the list of wildcards.
							card_to_transfer = wild_meld.get(wild_card_pos);
						}

						System.out.print("Great, now pick a meld position from 1 to ");
						System.out.print(player_hand.get_size_of_meld());
						System.out.print(" to add onto. Enter 0 cancel the operation.");
						System.out.print("\n");
						System.out.print("Also, input -1");
						System.out.print(" if you want to transfer the wild card back to the hand. You can only do this ");
						System.out.print("if you can create a meld with the cards in the hand.");
						System.out.print("\n");
						meld_pos = validate_option_based_input(-1, player_hand.get_size_of_meld(), true) - 1;




						//this is if the user enters 0.
						if (meld_pos == -1)
						{
							break;
						}
						if (meld_pos == wild_pos)
						{
							System.out.print("You don't need to transfer a wild card to the same meld...");
							System.out.print("\n");
							break;
						}


						operation_success = transfer_card(new Card(card_to_transfer), wild_pos, meld_pos);
						if (!operation_success)
						{
							System.out.print("The  was unsuccessful! Please see the above output^");
							System.out.print("\n");
						}
					}
					else
					{
						System.out.print("Meld Error: you have no melds to add onto!");
						System.out.print("\n");
						break;
					}
				} while (operation_success != true);

				break;
			}
			case 4:
				still_melding = false;
				break;
			case 5:
				advice_meld(new ArrayList<ArrayList<Card>>(enemy_melds));
			}

		} while (still_melding);


	}

	/* *********************************************************************
	Function Name: discard
	Purpose: To simulate the discard phase for the Human player, input validation included. 
	Parameters: 
				draw_decks: a Deck reference representing the discard and stock pile from Round.
				enemy_melds: a vector of vectors of Cards representing the enemy's melds.
	Algorithm: 
				1. Print out relevant information, such as the hand, and the enemy's melds, to ensure the player can make an informed decision.
				2. If the player hand is empty, then skip the discard phase.
				3. Prompt the user to enter a position of a Card they'd like to discard and validate it. 
				4. If the user discards a wild card or a card of 3, then freeze the discard pile. 
				5. If the user discards a natural card, unfreeze the discard pile.
	Local variables: 
				player_hand: a Hand that represent's the current Human's hand, but only a copy.
				size_of_hand: an int that represents the size of the Human's hand.
				choice: an int which represents the player's choice from validated output, so in this case,
						  the position of the card to discard.
				card_to_discard: the Card which the player chose to discard, to be stored to see if it is wild or natural.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	@Override
	public void discard(Deck draw_decks, ArrayList<ArrayList<Card>> enemy_melds)
	{
		temp_print_hand();
		print_enemy_meld(new ArrayList<ArrayList<Card>>(enemy_melds));
		Hand player_hand = get_player_hand();
		int size_of_hand = player_hand.get_size_of_hand();

		if (hand_empty())
		{
			System.out.print("You don't have anything to discard, so you skip your discard turn.");
			System.out.print("\n");
			return;
		}


		System.out.print("DISCARD Phase: What card would you like to discard? Select a position from 1 to ");
		System.out.print(size_of_hand);
		System.out.print("\n");
		System.out.print("OR input 0 to get advice.");
		System.out.print("\n");

		int choice = 0;
		choice = validate_option_based_input(0, size_of_hand) - 1;
		while (choice == -1)
		{
			advice_discard(draw_decks, new ArrayList<ArrayList<Card>>(enemy_melds));
			System.out.print("DISCARD Phase: What card would you like to discard? Select a position from 1 to ");
			System.out.print(size_of_hand);
			System.out.print("\n");
			System.out.print("OR input 0 to get advise.");
			System.out.print("\n");
			choice = validate_option_based_input(0, size_of_hand) - 1;
		}


		Card card_to_discard = player_hand.get_card_from_hand(choice);
		if (card_to_discard.isWild() || card_to_discard.isSpecial())
		{
			draw_decks.discard_push_front(new Card(card_to_discard));
			remove_from_hand(new Card(card_to_discard));
			draw_decks.set_discard_freeze(true);
		}
		else
		{
			draw_decks.discard_push_front(new Card(card_to_discard));
			remove_from_hand(new Card(card_to_discard));
			draw_decks.set_discard_freeze(false);
		}


	}

	/* *********************************************************************
	Function Name: print_player_type
	Purpose: To print out the player's type.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	@Override
	public void print_player_type()
	{
		System.out.print("Human");
	}

	/* *********************************************************************
	Function Name: choose_to_go_out
	Purpose: To prompt the user if they want to go out or not.
	Parameters: none
	Return Value: bool, representing if the user chose to go out.
	Assistance Received: none
	********************************************************************* */
	@Override
	public boolean choose_to_go_out()
	{
		System.out.print("You can go out, you have a Canasta and have melded all other cards. Do you want to go out?");
		System.out.print("\n");
		System.out.print("This action will end the round, and you'll get a bonus 100 points for ending it. ");
		System.out.print("\n");
		System.out.print("1. Yes");
		System.out.print("\n");
		System.out.print("2. No");
		System.out.print("\n");

		int choice = validate_option_based_input(1, 2);
		if (choice == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/* *********************************************************************
	Function Name: get_player_type
	Purpose: To output a Human's player type.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: virtual String get_player_type() const override
	@Override
	public String get_player_type()
	{
		return "Human";
	}

	/* *********************************************************************
	Function Name: print_enemy_melds
	Purpose: To output the opponent's melds.
	Parameters: enemy_meld, a vector of vectors of Cards that represents the enemy's melds.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: void print_enemy_meld(STLVector<STLVector<Card>> enemy_meld) const
	public final void print_enemy_meld(ArrayList<ArrayList<Card>> enemy_meld)
	{
		System.out.print("Opponent's melds: ");
		System.out.print("\n");
		for (int meld_pos = 0; meld_pos < enemy_meld.size(); meld_pos++)
		{
			System.out.print("[ ");
			for (int card_pos = 0; card_pos < enemy_meld.get(meld_pos).size(); card_pos++)
			{
				System.out.print(enemy_meld.get(meld_pos).get(card_pos).get_card_string());
				System.out.print(" ");
			}
			System.out.print("] ");
		}
		System.out.print("\n");
	}

	/* *********************************************************************
	Function Name: strategy
	Purpose: To give an advice menu to the Human and as well as give advice.
	Parameters: 
				  draw_decks: a Deck reference representing the Round's stock and discard pile.
				  enemy_meld: a vector of vectors of Cards that represents the enemy's melds.
	Algorithm: 
				1. Use a do while loop to continually give and validate input, within this do while loop, also, 
					print all relevant player's hand information, as well as the enemy's melds.
				2. Depending on which option the player picks: 
					a) Give advice regarding drawing.
					b) Give advice regarding melding.
					c) Give advice regarding discarding.
					d) exit the menu, and return from the function.
	Local variables: 
						choice, an int which represents the player's inputted option.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	@Override
	public void strategy(Deck draw_decks, ArrayList<ArrayList<Card>> enemy_melds)
	{
		int choice = 0;


		do
		{
			temp_print_hand();
			print_enemy_meld(new ArrayList<ArrayList<Card>>(enemy_melds));
			System.out.print("Discard pile: ");
			System.out.print("\n");
			draw_decks.print_discard_pile();
			System.out.print("What would you like advice regarding?");
			System.out.print("\n");
			System.out.print("1. Drawing");
			System.out.print("\n");
			System.out.print("2. Meld");
			System.out.print("\n");
			System.out.print("3. Discard");
			System.out.print("\n");
			System.out.print("4. Go out of menu");
			System.out.print("\n");
			choice = validate_option_based_input(1, 4);
			switch (choice)
			{
			case 1:
				advice_draw(draw_decks);
				break;
			case 2:
				advice_meld(new ArrayList<ArrayList<Card>>(enemy_melds));
				break;
			case 3:
				advice_discard(draw_decks, new ArrayList<ArrayList<Card>>(enemy_melds));
				break;
			case 4:
				return;
			}
		} while (choice != 4);


		return;

	}

	/* *********************************************************************
	Function Name: strategy_draw
	Purpose: The Computer's strategy regarding the draw phase reapplied to give advise to the Human, and is nondestructive.
				Note: while it says CPU, this is only regarding recommendations, the CPU doesn't play for the player.
	Parameters:
				 draw_decks: a Deck reference which represents the discard and stock pile from the Round.
				 enemy_melds: a vector of vectors of Card which represents the enemy's melds.
	Algorithm:
				1. If the CPU sees that the first card of the discard pile is meldable,
				as well as the discard pile isn�t frozen, and that the CPU has a hand size greater than 5, it�ll draw from the discard pile.
				2. If none of the above are true and the stock pile is empty, it�ll draw from the stock pile.
				3. Otherwise, it�ll concede, saying it can�t draw from either piles, hence the game is over.
	Local variables:
				player_hand: a Hand representing the player's hand for comparisons.
				top_of_discard: A Card representing the top of the discard pile for which the Computer to see if it can draw from there.
				can_meld: a bool which represents if the top of the discard pile is meldable with the hand.
				can_meld_with_melds: a bool which represents if the top of the discard pile is meldable with the Computer's melds.
				hand_is_not_small: a bool which represents if the hand is greater than 5 or not.
				picked_up_discard: a vector of Cards which represents the discard pile being picked up after being drawn.
	Return Value: none.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: void advice_draw(Deck& draw_decks) const
	public final void advice_draw(Deck draw_decks)
	{
		Human temp_human_ptr = new Human(this);
		temp_human_ptr.strategy_draw(draw_decks, get_player_type());
		temp_human_ptr = null;
		return;
	}

	/* *********************************************************************
	Function Name: strategy_meld
	Purpose: The Computer's strategy repurposed to be able to give advice to the Human player and is non destructive.
				Additionally, due to this nature, it will instead run on emulated sizes of container for loops dependent on that matter.
	Parameters:
				 draw_decks: a Deck reference which represents the discard and stock pile from the Round.
				 enemy_melds: a vector of vectors of Card which represents the enemy's melds.
	Algorithm:
				1. The computer will consider first if any of the cards in its hand can be made as a
					canasta when discarded to the enemy (i.e. enemy has 6 4s then it�ll hold onto a 4).
					If it sees this, it�ll play more conservatively, holding onto cards in its hand
					by restricting the total number of cards laid off per meld by the size of dangerous cards.
					So if the CPU has a hand of 5, it�ll always keep 2 cards, except for natural melds.
				2. The computer will first make a copy of unique faces in the hand.
					Then, it�ll see how many cards are there that make up that unique face(if they are natural).
					If the face is already in a pre-existing meld, it�ll search and find the meld, and add onto it.
					If not, if the cards found are greater than 3, it�ll create a meld. If there�s two natural cards, save for later,
					as they can be potentially melded with a wild card. If there�s just one, see if it can be laid off
					(this might be removed for redundancy purposes).
				3. Then, the cards that have a potential to be melded with wild cards are sorted in ascending order.
					Each pair is melded with a vector of wild cards, while both aren�t empty. When choosing to meld with wild cards,
					it�ll choose the meld with the highest score.
				4. Afterward, the CPU will sort a temporary vector of vectors of the meld container by size,
					and decide by the order of size which melds are best to lay off wild cards.
				5. Afterward, if the CPU sees that a meld can be made as a canasta through transferring wildcards,
					it�ll do so from extracting wild cards from other melds greater than the size of 3, but less than a size of 7.
				6. If the CPU did none of the aforementioned strategies above,
					it�ll say it did nothing, also stating if this decision was due to seeing a
					dangerous card(a card that if discarded will give the enemy a canasta) in it�s hand.
	
	
	Local variables:
				player_hand: a Hand representing the player's hand and melds.
				hand_container: a vector of Cards representing the player's hand.
				meld_container: a vector of Cards representing the player's melds.
				no_duplicate_card: a vector of Cards representing all unique cards in the player's hand.
				unique_faces: a vector of Cards representing all unique faces derived from no_duplicate_card.
				natural_meld_vector: a vector of Cards which represents all natural cards in the Hand.
				can_meld_with_wild: a vector of Cards which represents a pair of two Cards with the same face.
				wild_cards_in_hand: a vector of Cards which represents all the wild cards in a player's hand.
				transfer_to_hand: an int representing the option to transfer a card to a player's hand.
				amount_of_dangerous_cards: an int representing how many cards there are in the Computer's hand, if discarded,
				will give the enemy a free canasta.
				has_done_action: a bool representing if the Computer has melded, added to a meld, or transferred wild cards.
				first_card, second_card, third_card: Cards representing the 1st, 2nd, and 3rd card in a meld.
				first_nat_card... third_nat_card: Cards representing the 1st, 2nd, and 3rd natural cards in a meld.
				can_meld_to_canasta_sum: the sum needed to get a canasta, gained by gathering the size of the meld plus 3 minus the wild cards
					in the meld, so to see if it can be transferred.
				min_for_canasta: an int representing the minimum size a meld has to be to be a canasta (7).
				meld_already_exists: a bool representing if a meld of a particular Card's face already exists.
				meld_to_extract_wilds: a vector of Cards representing the meld in particular, to extract wild cards from to transfer cards to another meld.
				absolute_meld_pos: the absolute value of the position of a particular meld with respect to the unsorted meld container in the Player's Hand.
				wild_to_transfer: The meld at which the wild cards should be transferred from.
				meld: a vector of Cards representing a single meld within an iteration over the meld_container.
				extracted_face: the face extracted from a Card to see if there is a meld that already exists in meld_container.
				card_pos: an int which represents the position of a first Card in a potential meld.
				second_card_pos: an int which represents the position of a second Card in a potential meld.
				wild_pos: an int which represents the position of a wild card in a given meld or hand.
				emulated_size: an int which represents the emulated size of a particular container.
	
	Return Value: none.
	Assistance Received: Particulary in regards to unique and adding in an inaxproimate amount of cards, I used the following:
		//https://cplusplus.com/reference/iterator/back_inserter/
		//https://stackoverflow.com/questions/52150939/cant-dereference-value-initialized-iterator
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: void advice_meld(STLVector<STLVector<Card>> enemy_melds) const
	public final void advice_meld(ArrayList<ArrayList<Card>> enemy_melds)
	{
		Human temp_human_ptr = Human.Clone(new Human(this));
		//temp_human_ptr.set_player_score(get_score());
		//temp_human_ptr.set_hand(get_player_hand().get_hand_container());
		//temp_human_ptr.set_meld(get_player_hand().get_meld());
		
		temp_human_ptr.strategy_meld(new ArrayList<ArrayList<Card>>(enemy_melds), get_player_type());
		temp_human_ptr = null;
		return;
	}

	/* *********************************************************************
	Function Name: strategy_discard
	Purpose: The Computer's strategy regarding the discard phase repurposed to give advise to the Human player.
	Parameters:
				 draw_decks: a Deck reference which represents the discard and stock pile from the Round.
				 enemy_melds: a vector of vectors of Card which represents the enemy's melds.
	Algorithm:
				1. First, the CPU will create a vector of all cards that aren�t in the enemy�s melds,
					as well as a list of all cards that aren�t wild cards, based on a sorted hand from least to greatest points.
				2. Then, it�ll see if the top of the discard is in the enemy�s melds.
					If so, it�ll check if it has a 3 of spades or clubs, and discard that.
				3. Otherwise, it�ll attempt to discard from the vector of all cards that aren�t in enemy�s melds.
				4. Otherwise, it�ll attempt to discard cards that aren�t wild cards.
				5. Otherwise, it�ll attempt to discard a card at the lowest point value in the hand.
				6. Finally, if there�s nothing to discard, it�ll skip the discard turn.
	
	Local variables:
				preference_discard: a vector of Cards representing what the Computer has the top preference to discard,
				i.e. the cards that are not in the enemy's melds.
				not_in_melds_but_wilds: a vector of Cards representing what the Computer has in terms of wild cards.
				no_wild_discard; a vector of Cards that is all natural cards, regardless of if they're in the enemy's melds.
				player_hand: a Hand representing the player's hand for comparisons.
				hand_container: a vector of Cards' representing the Computer's hand.
				card_to_search: the Card to be searched for if it's in the enemy's melds.
				preferred_card: the Card that is the most preferred to be discarded.
				first_card_pos: the first position in all vectors, particularly ones containing cards.
				has_discarded_three: a bool representing if the Computer already chose to discard a three.
				three_spades_itr: an iterator representing if the Computer found a three of spades in it's hand.
				three_clubs_itr: an iterator representing if the Computer found a three of clubs in it's hand.
	
	Return Value: none.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: void advice_discard(Deck& draw_decks, STLVector<STLVector<Card>> enemy_melds) const
	public final void advice_discard(Deck draw_decks, ArrayList<ArrayList<Card>> enemy_melds)
	{
		Human temp_human_ptr = new Human(this);
		temp_human_ptr.strategy_discard(draw_decks, new ArrayList<ArrayList<Card>>(enemy_melds), get_player_type());
		temp_human_ptr = null;
	}


	/* *********************************************************************
	Function Name: validate_comma_input
	Purpose: To extract and validate 3 comma seperated inputs in order to create a potential meld..
	Parameters: none
	Algorithm:
				 1. Use a do while loop to extract and validate comma seperated input based on card positions, and add to a vector.
				 2. Remove any extra spaces within this input. If the player inputs 0, then break immeadiately. 
				 3. Check if the vector is of size 3, if not, then are too many or too little positions. Repeat the input loop. 
				 4. Get only unique positions in the comma based input. If the size isn't 3, then there are duplicates. Repeat the input loop.
				 5. Check that all inputs are numeric and within the bounds of the hand. If not, repeat the loop. 
				 6. If the cards pass all the critera, return the vector of Cards. 
				 7. Note that all positions are index 1 rather than 0, so some addition/subtraction is involved.
	Local variables:
						has_valid_input: a bool, which represents if an input is valid.
						input: a string which represents the Human's input. 
						player_hand: a Hand representing the Human's hand. 
						card_pos_range: an int representing the maximum size of that can be inputted.
						potential_card_pos: a string representing all the possible positions that the user has inputted.
						pos_to_compare: an int representing all the positions from which to compare to ensure there are no duplicates.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final ArrayList<Card> validate_comma_input()
	{
		boolean has_valid_input = false;
		String input;
		Hand player_hand = get_player_hand();
		//we need parenthesis due to order of operations.
		int card_pos_range = player_hand.get_size_of_hand();
		ArrayList<Card> cards_to_meld = new ArrayList<Card>();
		do
		{
			System.out.print("CREATE MELD: Please input the position of the cards you want to meld. Please put EXACTLY 3 comma seperated unique positions.");
			System.out.print("\n");
			System.out.print("Card position starts at 1 and ends at ");
			System.out.print(card_pos_range);
			System.out.print("\n");
			System.out.print("That or say 0 to cancel this phase.");
			System.out.print("\n");


			input = new Scanner(System.in).nextLine();


			if (input.equals("0"))
			{
				break;
			}
			//https://www.javatpoint.com/how-to-split-a-string-in-java-with-delimiter
			//https://www.geeksforgeeks.org/conversion-of-array-to-arraylist-in-java/
			//strips the string of all whitespace.
			input = input.trim();
			
			//input.substring(0, std::remove_if(input.iterator(), input.end(), isspace)) + input.substring(std::remove_if(input.iterator(), input.end(), isspace) + input.end());
			//std::stringstream ss = new std::stringstream(input);
			//String str;


			//while (getline(ss, str, ','))
			//{
			//	results.add(str);
			//}
			ArrayList<String> results = new ArrayList<String> (Arrays.asList(input.split(",")));
			if (results.size() != 3)
			{
				System.out.print("You either too many or too little positions. Please put only 3 positions");
				System.out.print("\n");
			}

//C++ TO JAVA CONVERTER TODO TASK: There is no direct equivalent to the STL vector 'erase' method in Java:
			//results.erase(std::unique(results.iterator(), results.end()),results.end());
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				results = new ArrayList <String> (results.stream().distinct().collect(Collectors.toList()));
			}

			if (results.size() != 3)
			{
				System.out.print("No duplicates can exist! Please enter unique positions.");
				System.out.print("\n");
			}

			for (int input_pos = 0; input_pos < results.size(); input_pos++)
			{
				String potential_card_pos = results.get(input_pos);
				//checks if all the positions are alphanumeric.
				
				if (input.matches("[a-zA-Z]+\\.?"))
				{
					System.out.print("Position is not numeric. Please input a numeric position!");
					System.out.print("\n");
				}


				else
				{
					//converts from string to int and then ensures it's within the card range.
					//note that it's not at index 0, since we don't know if the user is technically apt.
					int pos_to_compare = Integer.parseInt(potential_card_pos);
					if (pos_to_compare >= 1 && pos_to_compare <= card_pos_range)
					{
						cards_to_meld.add(player_hand.get_card_from_hand(pos_to_compare-1));
					}
					else
					{
						System.out.print("Position is not within range 1 to ");
						System.out.print(card_pos_range);
						System.out.print("!");
						System.out.print("\n");
						break;
					}
				}
				//ss.str("");
			}

			if (cards_to_meld.size() == 3)
			{
				has_valid_input = true;
			}
			else
			{
				cards_to_meld.clear();
			}


		} while (has_valid_input == false);
		return new ArrayList<Card>(cards_to_meld);
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


//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
	//int validate_option_based_input(int lower_bound, int upper_bound);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
	//int validate_option_based_input(int lower_bound, int upper_bound, boolean special_option);

	public static Human Clone(Human original) {
		Human clone = new Human(original.get_score(), original.get_player_hand().get_hand_container(), original.get_player_hand().get_meld());
		return clone;
	}

}













