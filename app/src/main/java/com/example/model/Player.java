package com.example.model;

import android.os.Build;

import java.util.*;
import java.util.stream.Collectors;

/* ***********************************************************
* Name:  Patrick Wierzbicki*
* Project : Canasta C++ Project 1*
* Class : CMPS-366-01*
* Date : 9/28/22*
*********************************************************** */






public abstract class Player
{

	/* ***********************************************************
	* Name:  Patrick Wierzbicki*
	* Project : Canasta C++ Project 1*
	* Class : CMPS-366-01*
	* Date : 9/28/22*
	*********************************************************** */


	/* *********************************************************************
	Function Name: Player
	Purpose: the default constructor for the player class.
	Parameters: none.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public Player()
	{
		score = 0;
		player_hand = new Hand();
		has_decided_to_go_out = false;
	}


	/* *********************************************************************
	Function Name: get_score
	Purpose: Getter for the score data member.
	Parameters: none.
	Return Value: int, representing the player's score data member.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: int get_score() const
	public final int get_score()
	{
		return score;
	}

	/* *********************************************************************
	Function Name: get_player_hand
	Purpose: A getter for the player's hand.
	Parameters: none
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: Hand get_player_hand() const
	public final Hand get_player_hand()
	{
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to contain a copy constructor call - this should be verified and a copy constructor should be created:
//ORIGINAL LINE: return player_hand;
		return new Hand(player_hand);
	}

	/* *********************************************************************
	Function Name: has_canasta
	Purpose: Checks if the player has a canasta.
	Parameters: none.
	Return Value: bool, representing if the player has a canasta or not.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean has_canasta() const
	public final boolean has_canasta()
	{
		return player_hand.has_canasta();
	}


	/* *********************************************************************
	Function Name: has_empty
	Purpose: Checks if the player has an empty hand.
	Parameters: none.
	Return Value: bool, representing if the player has an empty hand or not.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean hand_empty() const
	public final boolean hand_empty()
	{
		return player_hand.hand_empty();
	}


	/* *********************************************************************
	Function Name: can_go_out
	Purpose: To return if the player can go out. 
	Parameters: none
	Returns: bool, saying if the player can go out or not.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean can_go_out() const
	public final boolean can_go_out()
	{
		if (player_hand.hand_empty() == true && has_canasta())
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	/* *********************************************************************
	Function Name: get_go_out_decision
	Purpose: Retrieve the has_decided_to_go_out data member.
	Parameters: none
	Returns: a bool representing the data member has_decided_to_go_out.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean get_go_out_decision() const
	public final boolean get_go_out_decision()
	{
		return has_decided_to_go_out;
	}


	/* *********************************************************************
	Function Name: get_wild_cards_from_vector
	Purpose: Get all wild cards from an arbitrary vector of Cards.
	Parameters: arbitary_card_vector: an arbitrary vector from which wild cards are to be extracted from.
	Returns: a vector of Cards, representing all the wild cards in a vector.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: STLVector<Card> get_wild_cards_from_vector(STLVector<Card> arbitrary_card_vect) const
	public final ArrayList<Card> get_wild_cards_from_vector(ArrayList<Card> arbitrary_card_vect)
	{
		ArrayList<Card> vector_of_wild_cards = new ArrayList<Card>();
		for (int card_pos = 0; card_pos < arbitrary_card_vect.size(); card_pos++)
		{
			if (arbitrary_card_vect.get(card_pos).isWild() && arbitrary_card_vect.get(card_pos).get_has_transferred() == false)
			{
				vector_of_wild_cards.add(arbitrary_card_vect.get(card_pos));
			}
		}
		return new ArrayList<Card>(vector_of_wild_cards);
	}

	/* *********************************************************************
	Function Name: meld_of_card_exists
	Purpose: Check if there's a meld of the card passed that matches the face of said card.
	Parameters: card_to_search, a Card to compare against all the player's melds.
	Returns: a bool, representing if a meld of the card searched already exists.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean meld_of_card_exists(Card card_to_search) const
	public final boolean meld_of_card_exists(Card card_to_search)
	{
		return player_hand.meld_exits_already(new Card(card_to_search));
	}

	/* *********************************************************************
	Function Name: meld_of_card_exists
	Purpose: Get an absolute position in the unsorted vector of melds given a vector that has been found.
	Parameters: arbitrary_meld_vect, a vector of Cards representing the meld to search.
	Returns: a int, representing the absolute position of the passed vector in the current melds.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: int get_absolute_pos_from_relative_meld(STLVector<Card> arbitrary_meld_vect) const
	public final int get_absolute_pos_from_relative_meld(ArrayList<Card> arbitrary_meld_vect)
	{
		int first_element = 0;
		int error_pos = -999;
		Hand player_hand = get_player_hand();
		ArrayList<ArrayList<Card>> meld_container = player_hand.get_meld();
		for (int meld_pos = 0; meld_pos < meld_container.size(); meld_pos++)
		{
			if (arbitrary_meld_vect.get(first_element) == meld_container.get(meld_pos).get(first_element))
			{
				return meld_pos;
			}
		}
		return -error_pos;

	}

	/* *********************************************************************
	Function Name: print_vector
	Purpose: Prints an arbitrary vector, adding brackets inbetween to represent a meld.
	Parameters: vector_to_print: a vector of Cards, representing a meld.
	Returns: none
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: void print_vector(STLVector<Card> vector_to_print) const
	public final void print_vector(ArrayList<Card> vector_to_print)
	{
		System.out.print("[ ");
			for (Card card : vector_to_print)
			{
				System.out.print(card.get_card_string());
				System.out.print(" ");
			}
		System.out.print("] ");
		System.out.print("\n");
	}


	/* *********************************************************************
	Function Name: print_meld
	Purpose: Prints a meld in the player's Hand's meld container.
	Parameters: meld_pos: an int representing position of the meld to print out.
	Returns: none
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: void print_meld(int meld_pos) const
	public final void print_meld(int meld_pos)
	{
		Hand player_hand = get_player_hand();
		ArrayList<ArrayList<Card>> meld_container = player_hand.get_meld();
		print_vector(meld_container.get(meld_pos));
	}

	/* *********************************************************************
	Function Name: temp_print_hand
	Purpose: To print the contents of the hand and melds during a turn, hence the term
				temp, as it's not finalized until the end of the round.
	Parameters: none
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: void temp_print_hand() const
	public final void temp_print_hand()
	{
		System.out.print("\n");
		System.out.print("Your Hand & Melds Information: ");
		System.out.print("\n");
		System.out.print("Hand: ");
		get_player_hand().print_hand();
		System.out.print("      ");
		for (int i = 0; i < get_player_hand().get_size_of_hand(); i++)
		{
			System.out.print((i + 1));
			if (i + 1 <= 9)
			{
				System.out.print("  ");
			}
			else if (i + 1 <= 99)
			{
				System.out.print(" ");
			}
		}
		System.out.print("\n");
		System.out.print("Melds: ");
		get_player_hand().print_melds();
		System.out.print("\n");

	}


	/* *********************************************************************
	Function Name: add_to_hand
	Purpose: To add a card to the hand by interfacing with the Hand function: add_to_hand 
	Parameters: card_to_be_added: a Card, representing which card to add.
	Return Value: none.
	Assistance Received: none
	********************************************************************* */
	public final void add_to_hand(Card card_to_be_added)
	{
		player_hand.add_to_hand(new Card(card_to_be_added));
	}


	/* *********************************************************************
	Function Name: add_to_hand
	Purpose: To add a card to the hand by interfacing with the Hand function: add_to_hand(overloaded for a vector of Cards).
	Parameters: cards_to_be_added: a vector of Cards, representing which cards to add
	Return Value: none.
	Assistance Received: none
	********************************************************************* */
	public final void add_to_hand(ArrayList<Card> cards_to_be_added)
	{
		player_hand.add_to_hand(new ArrayList<Card>(cards_to_be_added));
	}

	/* *********************************************************************
	Function Name: create_special_meld
	Purpose: To create a meld of a red three Card by interfacing with the Hand function: create_meld(overloaded to create a red three meld).
	Parameters: cards_to_be_added: a Card representing a red three.
	Return Value: none.
	Assistance Received: none
	********************************************************************* */
	public final boolean create_special_meld(Card card_to_be_added)
	{
		return player_hand.create_meld(new Card(card_to_be_added));
	}

	/* *********************************************************************
	Function Name: create_meld
	Purpose: To create a meld of three Cards by interfacing with the Hand function: create_meld(overloaded for a meld of three cards).
	Parameters: potential_meld: a vector of Cards representing the potential meld for which to add. 
	Return Value: bool, representing if the create meld operation was successful.
	Assistance Received: none
	********************************************************************* */
	public final boolean create_meld(ArrayList<Card> potential_meld)
	{
		Card meld_card_1 = potential_meld.get(0);
		Card meld_card_2 = potential_meld.get(1);
		Card meld_card_3 = potential_meld.get(2);
		return player_hand.create_meld(new Card(meld_card_1), new Card(meld_card_2), new Card(meld_card_3));

	}

	/* *********************************************************************
	Function Name: create_meld
	Purpose: To create a meld of three Cards by interfacing with the Hand function: create_meld(overloaded for a meld of three cards).
	Parameters: 
				 first, the first Card that is passed.
				 second, the second Card that is passed.
				 third, the third Card that is passed.
	Return Value: bool, representing if the create meld operation was successful.
	Assistance Received: none
	********************************************************************* */
	public final boolean create_meld(Card first, Card second, Card third)
	{
		return player_hand.create_meld(new Card(first), new Card(second), new Card(third));
	}

	/* *********************************************************************
	Function Name: purge_red_threes
	Purpose: To make all red threes remove themselves from the hand and put them into a meld 
				using the Hand function purge_red_threes.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void purge_red_threes()
	{
		player_hand.purge_red_threes();
	}


	/* *********************************************************************
	Function Name: lay_off
	Purpose: To add a card to a pre-existing meld using the Hand function lay_off.
	Parameters: 
				 addition: a Card which is to be added to a pre-existing meld.
				 meld_number: an int representing the meld number from which to add.
	Return Value: bool if the operation was successful or not.
	Assistance Received: none
	********************************************************************* */
	public final boolean lay_off(Card addition, int meld_number)
	{
		return player_hand.lay_off(new Card(addition), meld_number);
	}

	/* *********************************************************************
	Function Name: transfer_card
	Purpose: To transfer wild cards between melds interfacing with the Hand class's function transfer_wild_card.
	Parameters: 
				  wild_card: A Card which represents the wild card to be transferred.
				  wild_origin: an int which represents the position of the meld where the wild card originates from
				  meld_number: an int representing the position of the meld which is the wild card's transfer target.
	Returns: bool, which represents if the transfer operation was successful or not.
	Assistance Received: none
	********************************************************************* */
	public final boolean transfer_card(Card wild_card, int wild_origin, int meld_number)
	{
		return player_hand.transfer_wild_card(new Card(wild_card), wild_origin, meld_number);
	}


	/* *********************************************************************
	Function Name: remove_from_hand
	Purpose: To remove a card from the player's hand. 
	Parameters: discard_card: the Card from which to be discarded. 
	Returns: bool, if the operation was successful.
	Assistance Received: none
	********************************************************************* */
	public final boolean remove_from_hand(Card discard_card)
	{
		return player_hand.remove_from_hand(new Card(discard_card));
	}

	/* *********************************************************************
	Function Name: sort_hand
	Purpose: To sort the player's hand from least to greatest by interfacing
				with the Hand class's function sort().
	Parameters: none
	Assistance Received: none
	********************************************************************* */
	public final void sort_hand()
	{
		player_hand.sort();
	}


	/* *********************************************************************
	Function Name: clear_transfer_states
	Purpose: To set all the Player's wild card's transfer state to false so they can be transferred the next turn.
	Parameters: none
	Returns: none
	Assistance Received: none
	********************************************************************* */
	public final void clear_transfer_states()
	{
		player_hand.clear_transfer_states();
	}


	/* *********************************************************************
	Function Name: clear_hand
	Purpose: To completely empty the hand.
	Parameters: none
	Returns: none
	Assistance Received: none
	********************************************************************* */
	public final void clear_hand()
	{
		player_hand.clear_all_data();
	}

	/* *********************************************************************
	Function Name: add_to_score
	Purpose: To add onto a player's score (data member).
	Parameters: score_addition: an int from which to add the player's score onto.
	Returns: none.
	Assistance Received: none
	********************************************************************* */
	public final void add_to_score(int score_addition)
	{
		score += score_addition;
	}

	/* *********************************************************************
	Function Name: add_to_score
	Purpose: To setter a player's score (data member).
	Parameters: score: an int from which to set the player's score.
	Returns: none.
	Assistance Received: none
	********************************************************************* */
	public final void set_player_score(int score)
	{
		this.score = score;
	}

	/* *********************************************************************
	Function Name: set_meld
	Purpose: To setter a player's melds.
	Parameters: meld_container: a vector of vectors of Cards representing a player's melds to set to.  
	Returns: none.
	Assistance Received: none
	********************************************************************* */
	public final void set_meld(ArrayList<ArrayList<Card>> meld_container)
	{
		player_hand.set_meld(meld_container);
	}

	/* *********************************************************************
	Function Name: set_hand
	Purpose: To setter a player's hand.
	Parameters: hand_container: a vector of Cards representing a player's hand to set to.
	Returns: none.
	Assistance Received: none
	********************************************************************* */
	public final void set_hand(ArrayList<Card> hand_container)
	{
		player_hand.set_hand(hand_container);
	}

	/* *********************************************************************
	Function Name: clear_hand_and_meld
	Purpose: Clears both the player's hands and melds.
	Parameters: none
	Returns: none
	Assistance Received: none
	********************************************************************* */
	public final void clear_hand_and_meld()
	{
		player_hand.clear_all_data();
	}




	/* *********************************************************************
	Function Name: sort_melds
	Purpose: Sorts a player's melds in ascending order.
	Parameters: meld_pos: an int representing position of the meld to print out.
	Returns: none
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: STLVector<STLVector<Card>> sort_melds(STLVector<STLVector<Card>> melds_to_sort) const
	public final ArrayList<ArrayList<Card>> sort_melds(ArrayList<ArrayList<Card>> melds_to_sort)
	{
		int first_card_pos = 0;
//C++ TO JAVA CONVERTER TODO TASK: Only lambda expressions having all locals passed by reference can be converted to Java:
//ORIGINAL LINE: std::sort(melds_to_sort.begin(), melds_to_sort.end(), [first_card_pos](const STLVector<Card>& lhs, const STLVector<Card>& rhs)
//C++ TO JAVA CONVERTER TODO TASK: The 'Compare' parameter of std::sort produces a boolean value, while the Java Comparator parameter produces a tri-state result:
		//Collections.sort(melds_to_sort, (ArrayList<Card> lhs, ArrayList<Card> rhs) ->
	//	{
	//			return lhs.get(first_card_pos) > rhs.get(first_card_pos);
	//	});
		
		Collections.sort(melds_to_sort, new Comparator <ArrayList<Card>>() {
			@Override
			public int compare (ArrayList <Card> lhs, ArrayList<Card> rhs) {
					if (lhs.get(first_card_pos).get_point_value() > rhs.get(first_card_pos).get_point_value())
						return -1;
					else if (lhs.get(first_card_pos).get_point_value() < rhs.get(first_card_pos).get_point_value())
						return 1;
					else if (lhs.get(first_card_pos).get_numeric_value() > rhs.get(first_card_pos).get_numeric_value())
						return -1;
					else if (lhs.get(first_card_pos).get_numeric_value() > rhs.get(first_card_pos).get_numeric_value())
						return 1;
					else 
						return 0;
				}
			});
		
		
		
		
//C++ TO JAVA CONVERTER TODO TASK: The 'Compare' parameter of std::sort produces a boolean value, while the Java Comparator parameter produces a tri-state result:
//ORIGINAL LINE: std::sort(melds_to_sort.begin(), melds_to_sort.end(), [](const STLVector<Card>& a, const STLVector<Card>& b)
	//	Collections.sort(melds_to_sort, (ArrayList<Card> a, ArrayList<Card> b) ->
	//	{
	//		return (a.size() > b.size() && a.size() < 7);
	//	});
		Collections.sort(melds_to_sort, new Comparator <ArrayList<Card>>() {
			@Override
			public int compare (ArrayList <Card> lhs, ArrayList<Card> rhs) {
					if (lhs.size() > rhs.size() && lhs.size() < 7 && lhs.size()>1)
						return -1;
					else if (lhs.size() < rhs.size() || lhs.size() > 7 || lhs.size() < 2 )
						return 1;
					else 
						return 0;
					//return var;
				}
			});	
		
		
		return new ArrayList<ArrayList<Card>>(melds_to_sort);
	}


	public abstract boolean play(Deck draw_decks, ArrayList<ArrayList<Card>> enemy_melds);
	//virtual void strategy();
	public abstract boolean draw(Deck draw_decks);
	public abstract void meld(ArrayList<ArrayList<Card>> enemy_melds);
	public abstract void discard(Deck draw_decks, ArrayList<ArrayList<Card>> enemy_melds);
	public abstract void print_player_type();
	public abstract boolean choose_to_go_out();
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: virtual String get_player_type() const = 0;
	public abstract String get_player_type();
	public abstract void strategy(Deck draw_decks, ArrayList<ArrayList<Card>> enemy_melds);
	/* *********************************************************************
	Function Name: meld
	Purpose: The Computer's strategy regarding the meld phase.
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
	
	Return Value: none.
	Assistance Received: Particulary in regards to unique and adding in an inaxproimate amount of cards, I used the following:
		//https://cplusplus.com/reference/iterator/back_inserter/
		//https://stackoverflow.com/questions/52150939/cant-dereference-value-initialized-iterator
	********************************************************************* */
	public final void strategy_meld(ArrayList<ArrayList<Card>> enemy_melds, String plr_type)
	{
		Hand player_hand = get_player_hand();
		ArrayList<Card> hand_container = player_hand.get_hand_container();
		ArrayList<ArrayList<Card>> meld_container = player_hand.get_meld();
		ArrayList<Card> no_duplicate_cards = new ArrayList<Card>();
		ArrayList<Card> unique_faces = new ArrayList<Card>();
		ArrayList<Card> natural_meld_vector = new ArrayList<Card>();
		ArrayList<Card> can_meld_with_wild = new ArrayList<Card>();
		ArrayList<Card> wild_cards_in_hand = player_hand.get_wild_cards_from_hand();
		//because of user input, the option to transfer to the hand is -2.
		int transfer_to_hand = -2;
		int amount_of_dangerous_cards = get_dangerous_amount_of_cards(new ArrayList<ArrayList<Card>>(enemy_melds));

		if (amount_of_dangerous_cards > 0)
		{
			if (plr_type.equals("Computer"))
			{
				System.out.print("The CPU sees that one of its cards if discarded will give the enemy make a canasta, so it's playing more conservatively...");
				System.out.print("\n");
				System.out.print("The dangerous card(s) in question are: ");
				print_vector(get_dangerous_card_list(new ArrayList<ArrayList<Card>>(enemy_melds)));
				System.out.println("The computer will try it's best not to discard these cards as a result!");

			}
			else if (plr_type.equals("Human"))
			{
				System.out.print("You ought to play more conservatively, as if you discard a certain card, you'll give your opponent a canasta. ");
				System.out.print("\n");
				System.out.print("The dangerous card(s) in question are: ");
				print_vector(get_dangerous_card_list(new ArrayList<ArrayList<Card>>(enemy_melds)));
				System.out.println("You should onto these cards with all your effort as a result!");
			}
		}


		boolean has_done_action = false;



		//first, remove the duplicate cards.
		//https://www.geeksforgeeks.org/how-to-get-unique-values-from-arraylist-using-java-8/
		//https://stackoverflow.com/questions/13134983/liststring-to-arrayliststring-conversion-issue
		//Watch out....
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			no_duplicate_cards = new ArrayList <Card> (hand_container.stream().distinct().collect(Collectors.toList()));
		}

		//std::unique_copy(hand_container.iterator(), hand_container.end(), std::back_inserter(no_duplicate_cards));


		if ((has_canasta() && hand_container.size() < 6))
		{
			for (int meld_pos = 0; meld_pos < meld_container.size(); meld_pos++)
			{
				if ((meld_container.get(meld_pos).size() < 7 && meld_container.get(meld_pos).size() > 3) || meld_container.get(meld_pos).size() > 7)
				{
					ArrayList<Card> wild_transfer_meld = player_hand.get_wild_cards(meld_pos);
					for (int wild_pos = 0; wild_pos < wild_transfer_meld.size(); wild_pos++)
					{
						if (player_hand.is_meldable(wild_transfer_meld.get(wild_pos)))
						{
							if (plr_type.equals("Computer"))
							{
								System.out.print("The CPU decided to transfer the wild card ");
								System.out.print(wild_transfer_meld.get(wild_pos).get_card_string());
								System.out.print(" to the hand from meld ");
								System.out.print(wild_pos);
								System.out.print(": ");
								print_meld(wild_pos);
								System.out.print("since it is quite eager to get rid of the cards out of it's hand to go out.");
								System.out.print("\n");
							}
							else if (plr_type.equals("Human"))
							{
								System.out.print("Transfer the wild card ");
								System.out.print(wild_transfer_meld.get(wild_pos).get_card_string());
								System.out.print(" to the hand from meld ");
								System.out.print(wild_pos);
								System.out.print(": ");
								print_meld(wild_pos);
								System.out.print("since you can use this strategy to get rid of cards out of your hand to go out.");
								System.out.print("\n");
							}
							transfer_card(wild_transfer_meld.get(wild_pos), meld_pos, -2);
							has_done_action = true;
						}
					}
				}
			}
		}
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to be a copy assignment (rather than a reference assignment) - this should be verified and a 'copyFrom' method should be created:
//ORIGINAL LINE: player_hand = get_player_hand();
		player_hand = get_player_hand();
		wild_cards_in_hand = new ArrayList<Card>(player_hand.get_wild_cards_from_hand());





		//then, make sure we only represent each card of one face, no wild cards or special cards.
		for (int card_pos = 0; card_pos < no_duplicate_cards.size(); card_pos++)
		{
			Card extracted_card = no_duplicate_cards.get(card_pos);
			if ((!extracted_card.isWild() && !extracted_card.isSpecial()))
			{
				boolean is_unique = true;
				for (int unique_card_pos = 0; unique_card_pos < unique_faces.size(); unique_card_pos++)
				{
					if (extracted_card.get_card_face() == unique_faces.get(unique_card_pos).get_card_face())
					{
						is_unique = false;
					}
				}
				if (is_unique)
				{
					unique_faces.add(no_duplicate_cards.get(card_pos));
				}
			}
		}

		//check the hand for each of these unique faces. Tally up the total of natural cards
		//with the same rank. If 3 or higher, it'll meld. If more than 4, it'll lay off the rest.
		for (int card_pos = 0; card_pos < unique_faces.size(); card_pos++)
		{
			char face_to_search_for = unique_faces.get(card_pos).get_card_face();
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to be a copy assignment (rather than a reference assignment) - this should be verified and a 'copyFrom' method should be created:
//ORIGINAL LINE: player_hand = get_player_hand();
			player_hand = get_player_hand();
			hand_container = new ArrayList<Card>(player_hand.get_hand_container());
			meld_container = new ArrayList<ArrayList<Card>>(player_hand.get_meld());
			for (int hand_c_pos = 0; hand_c_pos < hand_container.size(); hand_c_pos++)
			{

				Card card_from_hand = hand_container.get(hand_c_pos);
				char extracted_face = card_from_hand.get_card_face();
				if (extracted_face == face_to_search_for && (!card_from_hand.isWild() && !card_from_hand.isSpecial()))
				{
					natural_meld_vector.add(card_from_hand);
				}
			}

			boolean meld_already_exists = false;

			//if the natural meld vector is greater than 0.
			if (natural_meld_vector.size() > 0)
			{
				meld_already_exists = meld_of_card_exists(natural_meld_vector.get(0));
			}

			else
			{
				meld_already_exists = false;
			}
			//prevent an underflow, also 3 is the min needed for a canasta.
			if (natural_meld_vector.size() >= 3 && !meld_already_exists && ((int)hand_container.size() - amount_of_dangerous_cards > 0) && !is_dangerous_card(natural_meld_vector.get(0), new ArrayList<ArrayList<Card>>(enemy_melds)))
			{
				Card first_card = natural_meld_vector.get(0);
				Card second_card = natural_meld_vector.get(1);
				Card third_card = natural_meld_vector.get(2);
				create_meld(new Card(first_card), new Card(second_card), new Card(third_card));
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to be a copy assignment (rather than a reference assignment) - this should be verified and a 'copyFrom' method should be created:
//ORIGINAL LINE: player_hand = get_player_hand();
				player_hand = get_player_hand();

				//to prevent an underflow.
				for (int lay_off_pos = 3; lay_off_pos < (int)natural_meld_vector.size() - amount_of_dangerous_cards; lay_off_pos++)
				{
					lay_off(natural_meld_vector.get(lay_off_pos), player_hand.get_size_of_meld() - 1);
					has_done_action = true;
				}
				if (plr_type.equals("Computer"))
				{
					System.out.print("The CPU decided to meld the following cards: ");
					print_vector(new ArrayList<Card>(natural_meld_vector));
					System.out.print(" as they were an all natural meld, and getting rid of natural cards is generally advantageous.");
					System.out.print("\n");
				}
				else if (plr_type.equals("Human"))
				{
					System.out.print("Meld the following cards: ");
					print_vector(new ArrayList<Card>(natural_meld_vector));
					System.out.print(" as this is an all natural meld, and getting rid of natural cards is generally advantageous.");
					System.out.print("\n");
				}
			}
			else if (natural_meld_vector.size() == 2 && !meld_already_exists)
			{
				Card first_card = natural_meld_vector.get(0);
				Card second_card = natural_meld_vector.get(1);

				can_meld_with_wild.add(first_card);
				can_meld_with_wild.add(second_card);

			}

			else if (meld_already_exists)
			{
				for (int meld_pos = 0; meld_pos < meld_container.size(); meld_pos++)
				{
					if (natural_meld_vector.get(0).get_card_face() == meld_container.get(meld_pos).get(0).get_card_face())
					{

						int loop_size = natural_meld_vector.size();

						for (int lay_off_pos = 0; lay_off_pos < loop_size; lay_off_pos++)
						{
							has_done_action = true;
							lay_off(natural_meld_vector.get(lay_off_pos), meld_pos);

							if (plr_type.equals("Computer"))
							{
								System.out.print("The CPU decided to add to a pre-existing meld using the following card(s): ");
								print_vector(new ArrayList<Card>(natural_meld_vector));
								System.out.print(" as they were all natural card(s), and getting rid of natural card(s) is always advantageous.");
								System.out.print("\n");
							}

							else if (plr_type.equals("Human"))
							{
								System.out.print("Add to a pre-existing meld using the following card(s): ");
								print_vector(new ArrayList<Card>(natural_meld_vector));
								System.out.print(" as they were all natural cards(s) , and getting rid of natural card(s) is always advantageous.");
								System.out.print("\n");
							}
						}
					}
				}
			}


			natural_meld_vector.clear();
		}

//C++ TO JAVA CONVERTER TODO TASK: The 'Compare' parameter of std::sort produces a boolean value, while the Java Comparator parameter produces a tri-state result:
//ORIGINAL LINE: std::sort(can_meld_with_wild.begin(), can_meld_with_wild.end(), [](const Card& lhs, const Card& rhs)->boolean
		Collections.sort(can_meld_with_wild, new Comparator <Card>() {
			public int compare (Card lhs, Card rhs) {
					return (lhs.get_point_value() > rhs.get_point_value() ? 1: 0);
				}
			});
		
		
	//	Collections.sort(can_meld_with_wild, (Card lhs, Card rhs) ->
	//	{
	//		return lhs > rhs;
	//	});


		//to prevent an underflow.
		while (can_meld_with_wild.size() != 0 && (int)wild_cards_in_hand.size() - amount_of_dangerous_cards > 0)
		{
			int card_pos = 0;
			int second_card_pos = 1;
			int wild_pos = 0;

			Card first_nat_card = can_meld_with_wild.get(card_pos);
			Card second_nat_card = can_meld_with_wild.get(second_card_pos);
			Card third_wild_card = wild_cards_in_hand.get(wild_pos);

			boolean meld_already_exists = false;

			//it is inevitable duplicates from the natural meld vector will exist. Therefore, we need to
			//ensure they are not melded.
			if (can_meld_with_wild.size() > 0)
			{
				meld_already_exists = meld_of_card_exists(can_meld_with_wild.get(0));
			}

			else
			{
				meld_already_exists = false;
			}


			if (!meld_already_exists)
			{
				if (plr_type.equals("Computer"))
				{
					System.out.print("The CPU decided to meld: ");
					print_vector(new ArrayList< Card >(Arrays.asList(first_nat_card, second_nat_card, third_wild_card)));
					System.out.print(" as the two natural cards(ordered in highest order of points) can meld with a wild card. ");
					System.out.print("\n");
				}
				else if (plr_type.equals("Human"))
				{
					System.out.print("Meld the following: ");
					print_vector(new ArrayList< Card >(Arrays.asList(first_nat_card, second_nat_card, third_wild_card)));
					System.out.print(" as the two natural cards(ordered in highest order of points) can meld with a wild card. ");
					System.out.print("\n");
				}
				create_meld(new Card(first_nat_card), new Card(second_nat_card), new Card(third_wild_card));
				has_done_action = true;
			}
			//delete the pair at the beginning of the vector
			can_meld_with_wild.remove(card_pos);
			can_meld_with_wild.remove(card_pos);

			//since the cards are in pairs.
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to be a copy assignment (rather than a reference assignment) - this should be verified and a 'copyFrom' method should be created:
//ORIGINAL LINE: player_hand = get_player_hand();
			player_hand = get_player_hand();
			wild_cards_in_hand = new ArrayList<Card>(player_hand.get_wild_cards_from_hand());
		}

//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to be a copy assignment (rather than a reference assignment) - this should be verified and a 'copyFrom' method should be created:
//ORIGINAL LINE: player_hand = get_player_hand();
		player_hand = get_player_hand();
		wild_cards_in_hand = new ArrayList<Card>(player_hand.get_wild_cards_from_hand());
		meld_container = new ArrayList<ArrayList<Card>>(player_hand.get_meld());

		meld_container = new ArrayList<ArrayList<Card>>(sort_melds(new ArrayList<ArrayList<Card>>(meld_container)));
		for (int meld_pos = 0; meld_pos < meld_container.size(); meld_pos++)
		{
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to be a copy assignment (rather than a reference assignment) - this should be verified and a 'copyFrom' method should be created:
//ORIGINAL LINE: player_hand = get_player_hand();
			player_hand = get_player_hand();
			wild_cards_in_hand = new ArrayList<Card>(player_hand.get_wild_cards_from_hand());
			meld_container = new ArrayList<ArrayList<Card>>(player_hand.get_meld());
			meld_container = new ArrayList<ArrayList<Card>>(sort_melds(new ArrayList<ArrayList<Card>>(meld_container)));
			int absolute_meld_pos = get_absolute_pos_from_relative_meld(meld_container.get(meld_pos));
			ArrayList<Card> wild_cards_from_meld = player_hand.get_wild_cards_ignore_transfer(absolute_meld_pos);

			if (meld_container.get(meld_pos).size() >= 3 && wild_cards_from_meld.size() <= 3)
			{

				//prevent an underflow error.
				while ((((int)wild_cards_in_hand.size() - amount_of_dangerous_cards) > 0) && wild_cards_from_meld.size() < 3)
				{

					absolute_meld_pos = get_absolute_pos_from_relative_meld(meld_container.get(meld_pos));
					has_done_action = true;

					if (plr_type.equals("Computer"))
					{
						System.out.print("The CPU chose to lay off the card ");
						System.out.print(wild_cards_in_hand.get(0).get_card_string());
						System.out.print(" because the meld chose, ");
						print_vector(meld_container.get(meld_pos));
						System.out.print(" had the highest size(<7) and less than 3 wild cards, so it prioritized it.");
						System.out.print("\n");
					}

					else if (plr_type.equals("Human"))
					{
						System.out.print("Add this card to a pre-existing meld ");
						System.out.print(wild_cards_in_hand.get(0).get_card_string());
						System.out.print(" because the meld shown, ");
						print_vector(meld_container.get(meld_pos));
						System.out.print(" had the highest size(<7) and less than 3 wild cards, so you ought to prioritize it.");
						System.out.print("\n");
					}

					lay_off(wild_cards_in_hand.get(0), absolute_meld_pos);



//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to be a copy assignment (rather than a reference assignment) - this should be verified and a 'copyFrom' method should be created:
//ORIGINAL LINE: player_hand = get_player_hand();
					player_hand= get_player_hand();
					wild_cards_in_hand = new ArrayList<Card>(player_hand.get_wild_cards_from_hand());
					wild_cards_from_meld = new ArrayList<Card>(player_hand.get_wild_cards_ignore_transfer(absolute_meld_pos));
				}
			}
		}
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to be a copy assignment (rather than a reference assignment) - this should be verified and a 'copyFrom' method should be created:
//ORIGINAL LINE: player_hand = get_player_hand();
		player_hand= get_player_hand();
		meld_container = new ArrayList<ArrayList<Card>>(player_hand.get_meld());

		for (int meld_pos = 0; meld_pos < meld_container.size(); meld_pos++)
		{
			ArrayList<Card> meld = meld_container.get(meld_pos);
			int can_meld_to_canasta_sum = meld.size() + 3 - get_wild_cards_from_vector(new ArrayList<Card>(meld)).size();
			int min_for_canasta = 7;
			boolean has_transferred = false;
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to be a copy assignment (rather than a reference assignment) - this should be verified and a 'copyFrom' method should be created:
//ORIGINAL LINE: player_hand = get_player_hand();
			player_hand= get_player_hand();
			meld_container = new ArrayList<ArrayList<Card>>(player_hand.get_meld());
			if (can_meld_to_canasta_sum >= min_for_canasta && (meld.size() < 6 && meld.size() > 3) && !has_canasta())
			{
				int other_melds_pos = (meld_pos == meld_container.size() - 1) ? 0 : meld_pos + 1;
				while (other_melds_pos != meld_pos)
				{

//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to be a copy assignment (rather than a reference assignment) - this should be verified and a 'copyFrom' method should be created:
//ORIGINAL LINE: player_hand = get_player_hand();
					player_hand= get_player_hand();
					meld_container = new ArrayList<ArrayList<Card>>(player_hand.get_meld());


					ArrayList<Card> meld_to_extract_wilds = meld_container.get(other_melds_pos);
					if (meld_to_extract_wilds.size() > 3)
					{
						ArrayList<Card> wild_to_transfer = player_hand.get_wild_cards(other_melds_pos);
						while (wild_to_transfer.size() != 0 && meld_to_extract_wilds.size() > 3)
						{
							has_done_action = true;
							if (plr_type.equals("Computer"))
							{
								System.out.print("CPU decided to transfer card ");
								System.out.print(wild_to_transfer.get(0).get_card_string());
								System.out.print(" from meld: ");
								print_meld(other_melds_pos);
								System.out.print("As meld: ");
								print_meld(meld_pos);
								System.out.print(" can be made as a Canasta with just a few more wildcards.");
								System.out.print("\n");
							}

							else if (plr_type.equals("Human "))
							{
								System.out.print("Transfer card ");
								System.out.print(wild_to_transfer.get(0).get_card_string());
								System.out.print(" from meld: ");
								print_meld(other_melds_pos);
								System.out.print("As meld: ");
								print_meld(meld_pos);
								System.out.print(" can be made as a Canasta with just a few more wildcards.");
								System.out.print("\n");
							}

							transfer_card(wild_to_transfer.get(0), other_melds_pos, meld_pos);
//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to be a copy assignment (rather than a reference assignment) - this should be verified and a 'copyFrom' method should be created:
//ORIGINAL LINE: player_hand = get_player_hand();
							player_hand = get_player_hand();
							meld_container = new ArrayList<ArrayList<Card>>(player_hand.get_meld());
							meld_to_extract_wilds = new ArrayList<Card>(meld_container.get(other_melds_pos));
//C++ TO JAVA CONVERTER TODO TASK: There is no direct equivalent to the STL vector 'erase' method in Java:
							//Watch out for this one...
							wild_to_transfer.remove(0);
						}
					}

//C++ TO JAVA CONVERTER TODO TASK: The following line was determined to be a copy assignment (rather than a reference assignment) - this should be verified and a 'copyFrom' method should be created:
//ORIGINAL LINE: player_hand = get_player_hand();
					player_hand = get_player_hand();
					meld_container = new ArrayList<ArrayList<Card>>(player_hand.get_meld());

					if (other_melds_pos == meld_container.size() - 1)
					{
						other_melds_pos = 0;
					}
					else
					{
						other_melds_pos++;
					}
				}
			}
		}



		if (!has_done_action && amount_of_dangerous_cards == 0)
		{
			if (plr_type.equals("Computer"))
			{
				System.out.print("The CPU decided to do nothing, since it can't do any actions regarding melding.");
				System.out.print("\n");
			}
			else if (plr_type.equals("Human"))
			{
				System.out.print("End your meld phase. You can't meld anything.");
				System.out.print("\n");
			}
		}

		else if (amount_of_dangerous_cards > 0 && !has_done_action)
		{
			if (plr_type.equals("Computer"))
			{
				System.out.print("The CPU decided to do nothing, since it would rather do nothing than give a free canasta to the enemy.");
				System.out.print("\n");
			}
			else if (plr_type.equals("Human"))
			{
				System.out.print("End your meld phase, otherwise you'd risk giving a free canasta to the enemy.");
				System.out.print("\n");
			}

		}






		sort_hand();



	}


	/* *********************************************************************
	Function Name: draw
	Purpose: The Computer's strategy regarding the draw phase.
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
	Return Value: bool, representing if the game should end immeadiately if the Computer cannot draw.
	Assistance Received: none
	********************************************************************* */
	public final boolean strategy_draw(Deck draw_decks, String plr_type)
	{
		Hand player_hand = get_player_hand();
		Card top_of_discard = draw_decks.get_top_discard_pile();
		boolean can_meld = player_hand.is_meldable(new Card(top_of_discard));
		boolean can_meld_with_melds = player_hand.is_meldable_with_melds(new Card(top_of_discard));
		boolean hand_is_not_small = (((int)player_hand.get_size_of_hand() > 5));

		if (((can_meld || can_meld_with_melds) && ((!has_canasta() || ((has_canasta()) && (hand_is_not_small)))) && (!draw_decks.get_discard_is_frozen())))
		{
			if (plr_type.equals("Computer"))
			{
				System.out.print("CPU: drawing discard pile: can meld with hand or add onto melds, hand isn't that small.");
				System.out.print("\n");
				ArrayList<Card> picked_up_discard = draw_decks.draw_from_discard();
				add_to_hand(new ArrayList<Card>(picked_up_discard));
				purge_red_threes();
			}
			else if (plr_type.equals("Human"))
			{
				System.out.print("I'd suggest drawing from the discard pile: you can meld with hand or add onto melds, and your hand isn't that small.");
				System.out.print("\n");
			}
			return false;
		}

		else if (!draw_decks.stock_is_empty())
		{
			if (plr_type.equals("Computer"))
			{
				System.out.print("CPU: drawing from stock, because: ");
			}
			else if (plr_type.equals("Human"))
			{
				System.out.print("I'd suggest you draw from stock, because: ");
			}

			if (draw_decks.get_discard_is_frozen())
			{
				System.out.print("the discard pile is frozen.");
				System.out.print("\n");
			}
			else if (!can_meld)
			{
				if (plr_type.equals("Computer"))
				{
					System.out.print("there are no cards in the CPU's hand that can meld with the card ");
					System.out.print(top_of_discard.get_card_string());
					System.out.print("\n");
				}
				else if (plr_type.equals("Human"))
				{
					System.out.print("there are no cards in the your hand that can meld with the card ");
					System.out.print(top_of_discard.get_card_string());
					System.out.print("\n");
				}

			}
			else
			{
				if (plr_type.equals("Computer"))
				{
					System.out.print("The bot wants to keep a small hand, so it can go out soon.");
					System.out.print("\n");
				}
				else if (plr_type.equals("Human"))
				{
					System.out.print("You should keep a small hand, so that you can go out soon.");
					System.out.print("\n");
				}

			}


			if (plr_type.equals("Computer"))
			{
				Card drawn_card = new Card();
				do
				{
					drawn_card.copyFrom(draw_decks.draw_from_stock());
					add_to_hand(new Card(drawn_card));
					purge_red_threes();
				} while (drawn_card.is_red_three() && !draw_decks.stock_is_empty());
			}


			return false;
		}

		else
		{
			if (plr_type.equals("Computer"))
			{
				System.out.print("The stock pile is empty and the CPU can't draw from the discard pile. It concedes!");
				System.out.print("\n");
			}
			else if (plr_type.equals("Human"))
			{
				System.out.print("You can't do much but wait and let the game end since the stock pile is empty and the discard pile is empty...");
				System.out.print("\n");
			}
			return true;
		}


	}


	/* *********************************************************************
	Function Name: discard
	Purpose: The Computer's strategy regarding the discard phase.
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
	public final void strategy_discard(Deck draw_decks, ArrayList<ArrayList<Card>> enemy_melds, String plr_type)
	{
		ArrayList<Card> preference_discard = new ArrayList<Card>();
		ArrayList<Card> not_in_melds_but_wilds = new ArrayList<Card>();
		ArrayList<Card> no_wild_discard = new ArrayList<Card>();
		Hand player_hand = get_player_hand();
		//makes comparisons a lot easier.
		player_hand.sort();
		ArrayList<Card> hand_container = player_hand.get_hand_container();
		Card preferred_card = new Card();
		int first_card_pos = 0;

//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		int three_spades_itr = hand_container.indexOf(new Card("3S"));
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		int three_clubs_itr = hand_container.indexOf(new Card("3C"));

		for (int card_pos = 0; card_pos < hand_container.size(); card_pos++)
		{
			Card card_to_search = hand_container.get(card_pos);
			boolean should_add = true;
			for (int meld_pos = 0; meld_pos < enemy_melds.size(); meld_pos++)
			{
				//checks for the first card. Due to the way I set up melding, it'll never
				//be able to have a wild card be the first card in a meld.
				if (card_to_search.get_card_face() == enemy_melds.get(meld_pos).get(0).get_card_face())
				{
					should_add = false;
				}
			}

			if (should_add == true)
			{
				if (!card_to_search.isWild() && !card_to_search.isSpecial())
				{
					preference_discard.add(card_to_search);
				}
				else
				{
					not_in_melds_but_wilds.add(card_to_search);
				}
			}



			if (!card_to_search.isWild())
			{
				no_wild_discard.add(card_to_search);
			}


		}

		Card top_of_discard = draw_decks.get_top_discard_pile();
		boolean has_discarded_three = false;
		for (int meld_pos = 0; meld_pos < enemy_melds.size(); meld_pos++)
		{
			if (top_of_discard.get_card_face() == enemy_melds.get(meld_pos).get(first_card_pos).get_card_face())
			{
				if (three_spades_itr != -1)
				{
					preferred_card = hand_container.get(three_spades_itr);
					draw_decks.discard_push_front(preferred_card);
					remove_from_hand(preferred_card);
					if (plr_type.equals("Computer"))
					{
						System.out.print("The CPU chose to get rid of 3S since it sees the top of the discard, ");
						System.out.print(top_of_discard.get_card_string());
						System.out.print(" is in the enemy meld: ");
						print_meld(meld_pos);
						System.out.print("\n");
					}

					else if (plr_type.equals("Human"))
					{
						System.out.print("You should get rid of 3S since the top of the discard, ");
						System.out.print(top_of_discard.get_card_string());
						System.out.print(" is in the enemy meld: ");
						print_meld(meld_pos);
						System.out.print("\n");
					}
					has_discarded_three = true;
					break;
				}


				else if (three_clubs_itr != -1)
				{
					preferred_card = hand_container.get(three_clubs_itr);
					draw_decks.discard_push_front(preferred_card);
					remove_from_hand(preferred_card);

					if (plr_type.equals("Computer"))
					{
						System.out.print("The CPU chose to get rid of 3C since it sees the top of the discard, ");
						System.out.print(top_of_discard.get_card_string());
						System.out.print(" is in the enemy meld: ");
						print_meld(meld_pos);
						System.out.print("\n");
					}

					else if (plr_type.equals("Human"))
					{
						System.out.print("You should get rid of 3C since the top of the discard, ");
						System.out.print(top_of_discard.get_card_string());
						System.out.print(" is in the enemy meld: ");
						print_meld(meld_pos);
						System.out.print("\n");
					}

					has_discarded_three = true;
					break;
				}
			}
		}



		if (preference_discard.size() != 0 && !has_discarded_three)
		{
			preferred_card.copyFrom(preference_discard.get(0));

			if (plr_type.equals("Computer"))
			{
				System.out.print("The CPU chose to get rid of ");
				System.out.print(preferred_card.get_card_string());
				System.out.print(" since it the lowest value  ");
				System.out.print("In the CPU's hand, @");
				System.out.print(preferred_card.get_point_value());
				System.out.print(" points, and is not in any of the enemy's melds, and wasn't a wild card.");
				System.out.print("\n");
			}
			else if (plr_type.equals("Human"))
			{
				System.out.print("You should get rid of ");
				System.out.print(preferred_card.get_card_string());
				System.out.print(" since it the lowest value  ");
				System.out.print("In the your hand hand, @");
				System.out.print(preferred_card.get_point_value());
				System.out.print(" points, and is not in any of the enemy's melds, and wasn't a wild card.");
				System.out.print("\n");
			}
		}

		else if (not_in_melds_but_wilds.size() != 0 && !has_discarded_three)
		{
			preferred_card.copyFrom(not_in_melds_but_wilds.get(0));
			if (plr_type.equals("Computer"))
			{
				System.out.print("The CPU chose to get rid of ");
				System.out.print(preferred_card.get_card_string());
				System.out.print(" since it the lowest value  ");
				System.out.print("In the CPU's hand, @");
				System.out.print(preferred_card.get_point_value());
				System.out.print(" points, and wasn't in an enemy meld.");
				System.out.print("\n");
			}
			else if (plr_type.equals("Human"))
			{
				System.out.print("You should get rid of ");
				System.out.print(preferred_card.get_card_string());
				System.out.print(" since it the lowest value  ");
				System.out.print("In the your hand, @");
				System.out.print(preferred_card.get_point_value());
				System.out.print(" points, and wasn't in an enemy meld.");
				System.out.print("\n");
			}
		}

		else if (no_wild_discard.size() != 0 && !has_discarded_three)
		{
			preferred_card.copyFrom(no_wild_discard.get(0));

			if (plr_type.equals("Computer"))
			{
				System.out.print("The CPU chose to get rid of ");
				System.out.print(preferred_card.get_card_string());
				System.out.print(" since it the lowest value  ");
				System.out.print("In the CPU's hand, @");
				System.out.print(preferred_card.get_point_value());
				System.out.print(" points, and wasn't a wild card.");
				System.out.print("\n");
			}

			else if (plr_type.equals("Human"))
			{
				System.out.print("You should  rid of ");
				System.out.print(preferred_card.get_card_string());
				System.out.print(" since it the lowest value  ");
				System.out.print("In the your hand, @");
				System.out.print(preferred_card.get_point_value());
				System.out.print(" points, and wasn't a wild card.");
				System.out.print("\n");
			}

			//https://stackoverflow.com/questions/44576857/randomly-pick-from-a-vector-in-c

		}
		else if (hand_container.size() != 0 && !has_discarded_three)
		{
			preferred_card.copyFrom(hand_container.get(0));
			if (plr_type.equals("Computer"))
			{
				System.out.print("The CPU had no choice, so it got rid of it's lowest value card, which ended up being: ");
				System.out.print(preferred_card.get_card_string());
				System.out.print("\n");
			}
			else if (plr_type.equals("Human"))
			{
				System.out.print("You don't have much of a chose, so get rid of the lowest value card, which ended up being: ");
				System.out.print(preferred_card.get_card_string());
				System.out.print("\n");
			}
		}
		else if (!has_discarded_three)
		{
			if (plr_type.equals("Computer"))
			{
				System.out.print("The CPU has nothing to discard, so it didn't.");
				System.out.print("\n");
			}
			else if (plr_type.equals("Human"))
			{
				System.out.print("You have nothing to discard, so you'll skip your turn.");
				System.out.print("\n");
			}
			return;
		}

		if (plr_type.equals("Computer"))
		{
			if (preferred_card.isWild() || preferred_card.isSpecial())
			{
				draw_decks.set_discard_freeze(true);
			}

			else
			{
				draw_decks.set_discard_freeze(false);
			}


			draw_decks.discard_push_front(new Card(preferred_card));
			remove_from_hand(new Card(preferred_card));
		}

	}


	/* *********************************************************************
	Function Name: set_go_out_decision
	Purpose: A setter for has_decided_to_go_out data member.
	Parameters: go_out_decision: a bool representing what to set the data member
					has_decided_to_go_out as.
	Returns: none
	Assistance Received: none
	********************************************************************* */
	public final void set_go_out_decision(boolean go_out_decision)
	{
		has_decided_to_go_out = go_out_decision;
	}

	/* *********************************************************************
	Function Name: go_out
	Purpose: To return if the player has decided to go out.
	Parameters: none
	Returns: bool, representing if the player has decided to go out
	Assistance Received: none
	********************************************************************* */
	public final boolean go_out()
	{
		boolean able_to_go_out = can_go_out();
		boolean did_chose_to_go_out = false;
		if (able_to_go_out)
		{
			did_chose_to_go_out = choose_to_go_out();

			if (did_chose_to_go_out)
			{
				set_go_out_decision(true);
			}
		}
		return did_chose_to_go_out;
	}


	/* *********************************************************************
	Function Name: get_dangerous_amount_of_cards
	Purpose: To gather the amount of how many cards, if discarded, will give the enemy a canasta.
	Parameters: enemy_melds, a vector of vectors of Cards representing the enemy's melds. 
	Returns: int, representing how many cards, if discarded, will give the enemy a canasta.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: int get_dangerous_amount_of_cards(STLVector<STLVector<Card>> enemy_melds) const
	public final int get_dangerous_amount_of_cards(ArrayList<ArrayList<Card>> enemy_melds)
	{
		int first_position = 0;
		Hand player_hand = get_player_hand();
		int amount_of_dangerous_cards = 0;
		ArrayList<Card> hand_container = player_hand.get_hand_container();
		for (int card_pos = 0; card_pos < hand_container.size(); card_pos++)
		{
			for (int meld_pos = 0; meld_pos < enemy_melds.size(); meld_pos++)
			{
				if (hand_container.get(card_pos).get_card_face() == enemy_melds.get(meld_pos).get(first_position).get_card_face() && enemy_melds.get(meld_pos).size() == 6)
				{
					amount_of_dangerous_cards++;
				}
			}
		}
		return amount_of_dangerous_cards;
	}
	
	
	
	public ArrayList<Card> get_dangerous_card_list (ArrayList<ArrayList<Card>> enemy_melds) {
		int first_position = 0;
		Hand player_hand = get_player_hand();
		ArrayList<Card> hand_container = player_hand.get_hand_container();
		ArrayList<Card> dangerous_card_list = new ArrayList<Card>();
		for (int card_pos = 0; card_pos < hand_container.size(); card_pos++)
		{
			for (int meld_pos = 0; meld_pos < enemy_melds.size(); meld_pos++)
			{
				if (hand_container.get(card_pos).get_card_face() == enemy_melds.get(meld_pos).get(first_position).get_card_face() && enemy_melds.get(meld_pos).size() == 6)
				{
					dangerous_card_list.add(new Card(hand_container.get(card_pos)));
				}
			}
		}
		return dangerous_card_list;
	}
	
	

	/* *********************************************************************
	Function Name: is_dangerous_card
	Purpose: To gather the amount of how many cards, if discarded, will give the enemy a canasta.
	Parameters: 
				  potential_danger_card, a Card which is to be checked if it is "dangerous", see above.
				  enemy_melds, a vector of vectors of Cards representing the enemy's melds. 
	Returns: bool, representing if the Card passed if discarded, will give the enemy a canasta.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean is_dangerous_card(Card potential_danger_card, STLVector<STLVector<Card>> enemy_melds) const
	public final boolean is_dangerous_card(Card potential_danger_card, ArrayList<ArrayList<Card>> enemy_melds)
	{
		int first_pos = 0;
		for (int meld_pos = 0; meld_pos < enemy_melds.size(); meld_pos++)
		{
			if (potential_danger_card.get_card_face() == enemy_melds.get(meld_pos).get(first_pos).get_card_face())
			{
				return true;
			}
		}
		return false;
	}

	private int score;
	private Hand player_hand = new Hand();
	private boolean has_decided_to_go_out;
}