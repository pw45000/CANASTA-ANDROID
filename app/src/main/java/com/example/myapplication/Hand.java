package com.example.myapplication;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.*;

/* ***********************************************************
 * Name:  Patrick Wierzbicki*
 * Project : Canasta Project 3 *
 * Class : CMPS-366-01*
 * Date : 11/16/22*
 *********************************************************** */


//To extract a card, the iterator looks verbose. 
//A typedef will hope to fix this.

public class Hand implements Serializable
{

	private ArrayList<ArrayList<Card>> meld_container = new ArrayList<ArrayList<Card>>();
	private ArrayList<Card> hand_container = new ArrayList<Card>();

	/* *********************************************************************
	Function Name: Hand
	Purpose: The default constructor. It reserves 15 cards for the initial draw, as well as 10 for the meld container
				as that's the total amount of possible melds there are. 
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	/**
	 The default constructor of the Hand Class.
	 */
	public Hand()
	{
		hand_container.ensureCapacity(15);
		meld_container.ensureCapacity(10);
	}

	/* *********************************************************************
	Function Name: Hand
	Purpose: A constructor made for debugging. 
	Parameters: debug_hand: a vector of Cards representing what is to be assigned to the hand_container.
	Return Value: none
	Assistance Received: none
	********************************************************************* */

	/**
	 A constructor such that it initializes the hand container for the Hand class.
	 @param debug_hand an Arraylist of Card that represents the hand container to set as.
	 */
	public Hand(ArrayList<Card> debug_hand)
	{
		hand_container = new ArrayList<Card>(debug_hand);
	}

	/**
	 A constructor that initalizes both the player's hand and melds.
	 @param player_hand  A hand that represents the player's hand and melds.
	 */
	public Hand(Hand player_hand) {
		this.hand_container = new ArrayList <Card>(player_hand.get_hand_container());
		this.meld_container = new ArrayList<ArrayList<Card>>(player_hand.get_meld());
	}

	/* *********************************************************************
	Function Name: create_meld
	Purpose: To move cards from the hand to create a new meld. 
	Parameters:
					first: a Card which is the first card passed. 
					second: a Card which is the second card passed. 
					third: a Card which is the third Card passed. 
	Algorithm:
				1. Create a vector of the potential melded cards from the parameters described above. 
				2. Check if any one of them are special, return false if so. 
				3. Check if any of the cards are wild. 
				4. If not, check that that they all have the same face, if so:
					a) check if the meld is a duplicate meld. If so, return false.
					b) If it is not a duplicate meld, remove the cards from the hand and 
						push the potential meld into the meld container. 
				5. If there is a wild card: 
					a) Tally the amount of wild cards in the potential meld. If there's more than one, 
						return false. 
					b) If that is not the case, store the wild cards position and remove it. 
					c) Check if the two cards have the same face. 
					d) If so, add the wild card back into the potential meld, remove the hands from the hand, 
						and add back to the meld container.
				6. Return if the operation was successful. 
	Local variables:
						potential_meld: a vector of Cards which is used to be pushed to the meld and for all comparisons.
						card_rank: a char that is used to compare against other melds to ensure no duplicates.
	Return Value: bool, returning if the operation is successful or not.
	Assistance Received: none
	********************************************************************* */
	/**
	 Creates a meld from the hand, removing three hards from the hand and adding them to melds.
	 @param first a Card which is the first card passed.
	 @param second a Card which is the second card passed.
	 @param third a Card which is the third Card passed.
	 @return String, representing the output printed out during this function.
	 */
	public final String create_meld(Card first, Card second, Card third)
	{
		//while it might be viewed as wasteful to make another meld, it'll come in handy
		//for searching for a wild card.
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream old = System.out;
		System.setOut(ps);



		ArrayList<Card> potential_meld = new ArrayList<Card>(Arrays.asList(first, second, third));

		//to help in ensuring no duplicate melds.
		char card_rank;

		if (first.isSpecial() || second.isSpecial() || third.isSpecial())
		{
			System.out.print("Meld error: you can't make a meld out of special cards!");
			System.out.print("\n");
			//return false;
			System.out.flush();
			System.setOut(old);
			return baos.toString();
		}

		boolean has_wilds = (first.isWild() || second.isWild() || third.isWild());


		if ((first.get_card_face() == second.get_card_face() && second.get_card_face() == third.get_card_face()) && !has_wilds)
		{
			card_rank = first.get_card_face();

			if (is_not_duplicate_meld(card_rank))
			{
				meld_container.add(potential_meld);
				for (int card_pos = 0; card_pos < 3; card_pos++)
				{
					remove_from_hand(potential_meld.get(card_pos));
				}
				System.out.flush();
				System.setOut(old);
				return baos.toString();
				//return true;
			}

			else
			{
				System.out.print("Meld error: Meld of ");
				System.out.print(first.get_card_face());
				System.out.print("already exists!");
				System.out.print("\n");
				System.out.flush();
				System.setOut(old);
				return baos.toString();
				//return false;
			}


		}

		else
		{
			//both variables are declared outside of the loop
			//so as to show they are needed to be used elsewhere as well.
			int card_pos = 0;
			Card wild_Card = new Card();
			int wild_counter = 0;
			int wild_pos = 0;
			//search the potential meld for a wild card.
			for (card_pos = 0; card_pos < 3; card_pos++)
			{
				if (potential_meld.get(card_pos).isWild())
				{
					wild_Card.copyFrom(potential_meld.get(card_pos));
					wild_pos = card_pos;
					wild_counter++;
					//rather than search through the vector again, we can just remove
					//the wild card.

				}
			}

			//need a counter to prevent vector errors.
			if (wild_counter <= 1)
			{
				potential_meld.remove(wild_pos);
			}


			//if the wild card is not removed, then break since it's not a valid meld due to lack
			//of equivalent faces
			if (potential_meld.size() == 3)
			{
				System.out.print("Meld error: one or more faces are different from another! Melds need to be of the same rank or have a wildcard!");
				System.out.print("\n");
				System.out.flush();
				System.setOut(old);
				return baos.toString();
				//return false;
			}
			//If the two remaining cards are of the same rank, it's a valid meld.
			else if (potential_meld.get(0).get_card_face() == potential_meld.get(1).get_card_face())
			{
				if (is_not_duplicate_meld(potential_meld.get(0).get_card_face()))
				{
					wild_Card.set_has_transferred(true);
					potential_meld.add(wild_Card);
					meld_container.add(potential_meld);
					for (int card_pos_itr = 0; card_pos_itr < 3; card_pos_itr++)
					{
						remove_from_hand(potential_meld.get(card_pos_itr));
					}

					System.out.flush();
					System.setOut(old);
					return baos.toString();
					//return true;
				}

				else
				{
					System.out.print("Meld error: Meld of ");
					System.out.print(first.get_card_face());
					System.out.print("already exists!");
					System.out.print("\n");
					System.out.flush();
					System.setOut(old);
					return baos.toString();
					//return false;

				}


			}
			//otherwise, return since it's not a valid meld.
			else
			{
				System.out.print("Meld error: one or more faces are different from another! Melds need to be of the same rank or have a wildcard!");
				System.out.print("\n");
				System.out.flush();
				System.setOut(old);
				return baos.toString();
				//return false;
			}
		}
	}

	/* *********************************************************************
	Function Name: create_meld
	Purpose: Creates a meld of a red three. Verifies the card passed is a red three.
	Parameters: red_three: a Card that represents a red three.
	Return Value: bool that represents if the operation was successful or not.
	Assistance Received: none
	********************************************************************* */
	/**
	 Creates a meld of a red three. Verifies the card passed is a red three.
	 @param red_three a Card that represents a red three.
	 @return bool that represents if the operation was successful or not.
	 */
	public final boolean create_meld(Card red_three)
	{
		if (red_three.isSpecial() && (red_three.get_card_suit() == 'H' || red_three.get_card_suit() == 'D'))
		{
			ArrayList<Card> special_meld = new ArrayList<Card>();
			special_meld.add(red_three);
			meld_container.add(special_meld);
			remove_from_hand(new Card(red_three));
			return true;
		}
		else
		{
			return false;
		}

	}


	/* *********************************************************************
	Function Name: lay_off
	Purpose: To add a card to a pre-existing meld.
	Parameters:
					addition: a Card that is the selected card to be added to a meld.
					meld_number: an int which represents the position of which meld to add onto.
	Algorithm:
				1. Check if the existing meld is special, i.e. a meld of a red three. Also, check if the addition is a red three.
				2. If the card to be added is wild, check that the amount of wild cards, making sure that there are no more than three.
					If there are no more than three, transfer the wild card from the hand to the meld. 
				3. If the card to be added is not wild, check that the card to added face matches the meld's face. 
				4. Return true if the operation was successful, false if the criteria above isn't met. 
	Local variables:
						first_pos: an integer which represents the first position in a vector.
						min_meld_pos: the minimum size for a vector.
	Return Value: bool, returning if the operation is successful or not.
	Assistance Received: none
	********************************************************************* */
	/**
	 To add a card to a pre-existing meld.
	 @param addition: a Card that is the selected card to be added to a meld.
	 @param meld_number: an int which represents the position of which meld to add onto.
	 @return bool, returning if the operation is successful or not.
	 */
	public final String lay_off(Card addition, int meld_number)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream old = System.out;
		System.setOut(ps);



		ArrayList<Card> existing_meld = meld_container.get(meld_number);
		//if the card is wild, ignore meld checking.

		int first_pos = 0;
		int min_meld_pos = 3;

		if (existing_meld.get(first_pos).isSpecial())
		{
			System.out.print("Lay off error: You cannot add onto a red three's meld!");
			System.out.print("\n");
			System.out.flush();
			System.setOut(old);
			return baos.toString();

		}

		if (addition.isSpecial())
		{
			System.out.print("Lay off error: You cannot add a special card to a meld!");
			System.out.print("\n");
			//return false;
			System.out.flush();
			System.setOut(old);
			return baos.toString();
		}

		if (addition.isWild())
		{
			int wild_counter = 0;
			//count the amount of wild cards there is, since
			//there cannot be more than 3.
			for (int card_pos = 0; card_pos < existing_meld.size(); card_pos++)
			{
				if (existing_meld.get(card_pos).isWild())
				{
					wild_counter++;
				}
			}
			//However, do check how many wild cards there are.
			if (wild_counter < min_meld_pos)
			{
				addition.set_has_transferred(true);
				meld_container.get(meld_number).add(addition);
				remove_from_hand(new Card(addition));
				System.out.flush();
				System.setOut(old);
				return baos.toString();
				//return true;
			}
			else
			{
				System.out.println("Lay off error: either the meld has too many wild cards! At most there can be 3 in a meld!");
				System.out.flush();
				System.setOut(old);
				return baos.toString();
				//System.out.print("\n");
				//return false;
			}
		}
		//If it's not, ensure that it's going to the correct meld.
		//This is done by checking the first element, as all wild cards will be at the end
		//of an initial meld.
		else
		{
			if (addition.get_card_face() == existing_meld.get(first_pos).get_card_face())
			{
				meld_container.get(meld_number).add(addition);
				remove_from_hand(new Card(addition));
				//return true;
			}
			else
			{
				System.out.println("Lay off error: the card faces do not match the meld!");
				System.out.flush();
				System.setOut(old);
				return baos.toString();
				//System.out.print("\n");
				//return false;
			}
		}

		System.out.flush();
		System.setOut(old);
		return baos.toString();
	}


	/* *********************************************************************
	Function Name: transfer_wild_card 
	Purpose: A function made to transfer wild cards between melds. 
	Parameters:
				   transfer: a Card that is the selected card to be transferred.
					wild_origin: an int representing the position of which meld to be transferred from.
					meld_target: an int representing the position of which meld to transfer to.
	Algorithm: 
				1. See if the Card selected to be transferred is wild (if not, send an error message) 
					and extract the meld from which it originates, if the meld has a size greater than 3, the minimum size of a meld.
				2. Check if it has been transferred already, if so, then output an error message.
				3. Check if the meld target is -2, which is the option to transfer it to the hand. If so:
					a) Check if it could meld with the hand. If so, transfer the card from the meld and add to the hand.
					b) If it is not, display an error message.
				4. If it is not, check that the target meld has no more than 3 wild cards, and that the card is not a meld of a red three.
				5. If all the criteria are met, transfer the card over from the original meld to the target meld.
				6. Return true or false if the operation was successful. 
	Local variables: 
						wild_origin_vector: a vector representing the meld from which the wild card originates.
						min_meld_size: an int representing the minimum size of a meld. 
						transfer_to_hand_option: an int representing the option to transfer a wild card to the hand.
						wild_itr: a card iterator which represents if the wild card is found in it's originating meld.
	Return Value: bool, returning if the operation is successful or not.
	Assistance Received: none
	********************************************************************* */
	/**
	 To transfer a card to a prexisting meld.
	 @transfer: a Card that is the selected card to be transferred.
	 @wild_origin: an int representing the position of which meld to be transferred from.
	 @meld_target: an int representing the position of which meld to transfer to.
	 @return String, representing the output produced by this function.
	 */
	public final String transfer_wild_card(Card transfer, int wild_origin, int meld_target)
	{
		//ArrayList<Card> wild_origin_vector = meld_container.get(wild_origin);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream old = System.out;
		System.setOut(ps);


		int min_meld_size = 3;
		int transfer_to_hand_option = -2;


		if (transfer.isWild())
		{
			if (meld_container.get(wild_origin).size() <= min_meld_size)
			{
				System.out.print("The selected meld to trasnfer from is too small! Please transfer with a meld with a size >3...");
				System.out.print("\n");
				System.out.flush();
				System.setOut(old);
				return baos.toString();
				//return false;
			}

			if (transfer.get_has_transferred() == true)
			{
				System.out.print("The current card has been transfered once! Please only transfer wild cards once per turn.");
				System.out.print("\n");
				System.out.flush();
				System.setOut(old);
				return baos.toString();
				//return false;
			}


			if (meld_target == transfer_to_hand_option)
			{

				//checks if wild_card exists within it's designated meld.
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
				//var wild_itr = meld_container.find(transfer);
				//std::find(meld_container.get(wild_origin).iterator(), meld_container.get(wild_origin).end(), transfer);
				//if the wild card is found and the meld isn't the minimum of a meld, transfer the wild card over.
				if (is_meldable(transfer) && meld_container.get(wild_origin).contains(transfer))
				{
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
					int wild_itr = meld_container.get(wild_origin).indexOf(transfer);
					//std::find(meld_container.get(wild_origin).iterator(), meld_container.get(wild_origin).end(), transfer);
//C++ TO JAVA CONVERTER TODO TASK: There is no direct equivalent to the STL vector 'erase' method in Java:
					meld_container.get(wild_origin).remove(wild_itr);
					hand_container.add(transfer);
					System.out.flush();
					System.setOut(old);
					return baos.toString();
					//return true;
				}
				else
				{
					System.out.print("Can't transfer wild card: you can't make any melds with the hand!");
					System.out.print("\n");
					System.out.flush();
					System.setOut(old);
					return baos.toString();
					//return false;
				}

			}


			//checks if wild_card exists within it's designated meld.
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
			//auto wild_itr = std::find(meld_container.get(wild_origin).iterator(), meld_container.get(wild_origin).end(), transfer);
			//if the wild card is found and the meld isn't the minimum of a meld, transfer the wild card over.
			if (meld_container.get(wild_origin).contains(transfer) && meld_container.get(meld_target).size() >= min_meld_size)
			{
//C++ TO JAVA CONVERTER TODO TASK: There is no direct equivalent to the STL vector 'erase' method in Java:
				meld_container.get(wild_origin).remove(meld_container.get(wild_origin).indexOf(transfer));
				transfer.set_has_transferred(true);
				meld_container.get(meld_target).add(transfer);

				//return true;
				System.out.flush();
				System.setOut(old);
				return baos.toString();
			}
			else
			{
				System.out.print("Transfer wild card error: cannot transfer wildcard of meld size 3 or less, or wild card not found...");
				System.out.print("\n");
			}
			//return false;
			System.out.flush();
			System.setOut(old);
			return baos.toString();

		}
		else
		{
			System.out.print("The selected card is not a wild card!");
			System.out.print("\n");
			System.out.flush();
			System.setOut(old);
			return baos.toString();
			//return false;
		}
	}



	/* *********************************************************************
	Function Name: remove_from_hand
	Purpose: Removes a card from the hand.
	Parameters: discarded_card: a Card that represents the card to be discarded
	Return Value: bool that represents if the card was found in the hand and erased or not.
	Assistance Received: none
	********************************************************************* */
	/**
	 Removes a card from the hand.
	 @param discarded_card a Card that represents the card to be discarded
	 @return bool that represents if the card was found in the hand and erased or not.
	 */
	public final boolean remove_from_hand(Card discarded_card)
	{
		int discard_itr = hand_container.indexOf(discarded_card);

//C++ TO JAVA CONVERTER TODO TASK: Iterators are only converted within the context of 'while' and 'for' loops:
		if (discard_itr != -1)
		{
//C++ TO JAVA CONVERTER TODO TASK: There is no direct equivalent to the STL vector 'erase' method in Java:
			hand_container.remove(discard_itr);
			return true;
		}

		else
		{
			return false;
		}

	}

	/* *********************************************************************
	Function Name: is_canasta
	Purpose: Checks if a meld is a canasta.
	Parameters: meld_number: an int representing the position of a meld.
	Return Value: bool that represents if the meld at position meld_number is a canasta.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean is_canasta(int meld_number) const
	/**
	 Checks if a meld is a canasta.
	 @param meld_number an int representing the position of a meld.
	 @return  bool that represents if the meld at position meld_number is a canasta.
	 */
	public final boolean is_canasta(int meld_number)
	{
		if (meld_container.size() < meld_number)
		{
			return false;
		}

		if (meld_container.get(meld_number).size() >= 7)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/* *********************************************************************
	Function Name: is_not_duplicate_meld
	Purpose: Checks if there already is a meld of a rank/face.
	Parameters: rank: a character representing the face of a card to be checked.
	Return Value: bool that represents if the rank is a duplicate meld or not.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean is_not_duplicate_meld(char rank) const
	/**
	 Checks if there already is a meld of a rank/face.
	 @param rank a character representing the face of a card to be checked.
	 @return bool that represents if the rank is a duplicate meld or not.
	 */
	public final boolean is_not_duplicate_meld(char rank)
	{
		int first_pos = 0;
		for (ArrayList<Card> meld : meld_container)
		{
			Card first_element = meld.get(first_pos);
			char first_element_rank = first_element.get_card_face();
			if (rank == first_element_rank)
			{
				return false;
			}
		}
		return true;
	}

	/* *********************************************************************
	Function Name: hand_empty
	Purpose: Checks if the hand is empty.
	Parameters: none
	Return Value: bool that represents if hand is empty or not.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean hand_empty() const
	/**
	 Checks if the hand is empty.
	 @return bool that represents if hand is empty or not.
	 */
	public final boolean hand_empty()
	{
		boolean hand_is_empty = (hand_container.size() == 0) ? true : false;
		return hand_is_empty;
	}


	/* *********************************************************************
	Function Name: has_canasta
	Purpose: Checks if in the hand, there is a canasta.
	Parameters: none
	Return Value: bool that represents if hand has a canasta or not.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean has_canasta() const
	/**
	 Checks if in the hand, there is a canasta.
	 @return bool that represents if hand has a canasta or not.
	 */
	public final boolean has_canasta()
	{
		for (int meld_pos = 0; meld_pos < meld_container.size(); meld_pos++)
		{
			if (is_canasta(meld_pos))
			{
				return true;
			}
		}
		return false;
	}


	/* *********************************************************************
	Function Name: meld_exists_already
	Purpose: Check if a meld of the given card's face exists already.
	Parameters: card_to_search, a Card from which face will be compared against all other melds to see
					if there are duplicates.
	Return Value: bool saying that a meld exists already or doesn't.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean meld_exits_already(Card card_to_search) const
	/**
	 Check if a meld of the given card's face exists already.
	 @param card_to_search, a Card from which face will be compared against all other melds to see if there are duplicates.
	 @return  bool saying that a meld exists already or doesn't.
	 */
	public final boolean meld_exits_already(Card card_to_search)
	{
		for (int meld_pos = 0; meld_pos < meld_container.size(); meld_pos++)
		{
			for (int card_pos = 0; card_pos < meld_container.get(meld_pos).size(); card_pos++)
			{
				if (card_to_search.get_card_face() == meld_container.get(meld_pos).get(card_pos).get_card_face())
				{
					return true;
				}
			}
		}
		return false;
	}


	/* *********************************************************************
	Function Name: is_meldable_with_melds
	Purpose: Checks if the top of the discard is meldable with the current Player's melds.
	Parameters: discard_head: a Card that represents the top of the discard.
	Return Value: bool that represents if the top of the discard is meldable with other melds.
	Algorithm: 
				 1. If the passed card is a special, check if it's a red three. If so, return true, otherwise, return false.
				 2. If the passed card is wild, check if there's a meld with less than three wild cards.
				 3. If the passed card is natural, check that there is a meld that exists which first Card's face matches it's face.
				 4. Depending on if the criteria are met above, return true or false.
	Local variables: 
					    can_meld: a bool that represents if the card can meld. 
						 first_pos: represents the first position in a vector.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean is_meldable_with_melds(Card passed_card) const
	/**
	 Checks if the top of the discard is meldable with the current Player's melds.
	 @param passed_card: a Card that represents the Card to check
	 @return bool that represents if the top of the discard is meldable with other melds.
	 */
	public final boolean is_meldable_with_melds(Card passed_card)
	{
		//boolean can_meld = false;
		int first_pos = 0;

		if (passed_card.isSpecial())
		{
			if (passed_card.get_card_suit() == 'H' || passed_card.get_card_suit() == 'D')
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		else if (passed_card.isWild())
		{
			for (int meld_pos = 0; meld_pos < meld_container.size(); meld_pos++)
			{
				if (!meld_container.get(meld_pos).get(first_pos).isSpecial() && get_wild_cards(meld_pos).size() < 3)
				{
					return true;
				}
			}
		}

		else if (passed_card.isNatural())
		{
			for (int meld_pos = 0; meld_pos < meld_container.size(); meld_pos++)
			{
				if (passed_card.get_card_face() == meld_container.get(meld_pos).get(first_pos).get_card_face())
				{
					return true;
				}
			}
		}
		return false;
	}


	/* *********************************************************************
	Function Name: is_meldable
	Purpose: Checks if the top of the discard is meldable with the player's hand.
	Parameters: passed_card: a Card that is to be checked if it can be melded.
	Return Value: bool that represents if the top of the discard can be melded with the hand.
	Algorithm: 
				1. check if the passed card has transferred. If so, then it cannot be melded.
				2. If the passed card is special and is a red three, return true. Else, return false. 
				3. If the passed card is wild, and there are two natural cards which have the same face, return true. Otherwise, return false.
				4. Otherwise, since the passed card is natural, check if there's two other cards with the same face in the hand,
					or if there's a wild card in the hand and another card with the same face in the hand. 
					Return true or false based on the criteria above.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean is_meldable(Card passed_card) const
	/**
	 Checks if the top of the discard is meldable with the player's hand.
	 @param passed_card: a Card that is to be checked if it can be melded.
	 @return bool that represents if the top of the discard can be melded with the hand.
	 */
	public final boolean is_meldable(Card passed_card)
	{
		int compatible_cards = 0;
		int wild_cards = 0;

		if (passed_card.get_has_transferred())
		{
			return false;
		}

		if (passed_card.isSpecial())
		{
			if (passed_card.get_card_suit() == 'H' || passed_card.get_card_suit() == 'D')
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		else if (passed_card.isWild())
		{
			for (int card_pos = 0; card_pos < hand_container.size(); card_pos++)
			{
				for (int smlr_card_pos = 0; smlr_card_pos < hand_container.size(); smlr_card_pos++)
				{
					if (hand_container.get(smlr_card_pos).get_card_face() == hand_container.get(card_pos).get_card_face())
					{
						compatible_cards++;
					}
					if (compatible_cards >= 2)
					{
						return true;
					}
				}
			}
			return false;
		}
		else
		{
			for (int card = 0; card < hand_container.size(); card++)
			{
				if (hand_container.get(card).isWild())
				{
					wild_cards = wild_cards + 1;
				}
				if (hand_container.get(card).get_card_face() == passed_card.get_card_face())
				{
					compatible_cards++;
				}
			}
			//the first case sees if we can make a meld with 2 naturals and 1 wild,
			//the second sees if we can make a meld with all natural cards.
			if ((compatible_cards >= 1 && wild_cards >= 1) || (compatible_cards >= 2))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}


	/* *********************************************************************
	Function Name: calculate_meld_points
	Purpose: Calculates the total points in each meld.
	Parameters: meld_number: an int which represents the position of the meld to calculate points from.
	Return Value: int that represents the total score in the given meld..
	Assistance Received: none
	********************************************************************* */
	/**
	 Calculates the total points in each meld.
	 @param  meld_number: an int which represents the position of the meld to calculate points from.
	 @return int that represents the total score in the given meld..
	 */
	public final int calculate_meld_points(int meld_number)
	{
		int meld_points = 0;
		ArrayList<Card> meld_target = meld_container.get(meld_number);

		for (Card card : meld_target)
		{
			meld_points += card.get_point_value();
		}
		return meld_points;
	}

	/* *********************************************************************
	Function Name: print_hand
	Purpose: Prints the contents of the hand.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: void print_hand() const
	/**
		Prints the hand container.
	 */
	public final void print_hand()
	{
		for (Card hand_card : hand_container)
		{
			System.out.print(hand_card.get_card_string());
			System.out.print(" ");
		}
		System.out.print("\n");
	}


	/* *********************************************************************
	Function Name: print_melds
	Purpose: Prints the contents of the meld container.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: void print_melds() const
	/**
	 Prints the meld container.
	 */
	public final void print_melds()
	{
		for (int meld = 0; meld < meld_container.size(); meld++)
		{
			System.out.print(" [");
			for (int card = 0; card < meld_container.get(meld).size(); card++)
			{
				Card card_to_print = meld_container.get(meld).get(card);
				System.out.print(card_to_print.get_card_string());
				System.out.print(" ");
			}
			System.out.print("] ");
		}
		System.out.print("\n");

	}

	/* *********************************************************************
	Function Name: add_hand
	Purpose: Adds a card to the hand.
	Parameters: card_to_add: A card which will be added to hand.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	/**
	 Adds a card to the hand.
	 @paramcard_to_add: A card which will be added to hand.
	 */
	public final void add_to_hand(Card card_to_add)
	{
		hand_container.add(card_to_add);
	}

	/* *********************************************************************
	Function Name: add_hand
	Purpose: Adds an overloaded version of a function which adds several cards to the hand.
	Parameters: cards_to_add: a vector of Cards to be added to the hand.
	Return Value: none
	Assistance Received: none
	********************************************************************* */

	/**
	 Adds an overloaded version of a function which adds several cards to the hand.
	 @param cards_to_add: a vector of Cards to be added to the hand.
	 */
	public final void add_to_hand(ArrayList<Card> cards_to_add)
	{
		for (int card_pos = 0; card_pos < cards_to_add.size();card_pos++)
		{
			hand_container.add(cards_to_add.get(card_pos));
		}
	}


	/* *********************************************************************
	Function Name: purge_red_threes
	Purpose: Removes all red threes from the hand and melds them.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	/**
	 Removes all red three from the hand and melds them.
	 */
	public final void purge_red_threes()
	{
		for (int card_pos = 0; card_pos < hand_container.size(); card_pos++)
		{
			String card_string = hand_container.get(card_pos).get_card_string();
			if (card_string.equals("3H") || card_string.equals("3D"))
			{
				Card red_three = hand_container.get(card_pos);
				create_meld(new Card(red_three));
			}
		}
	}


	/* *********************************************************************
	Function Name: get_size_of_hand
	Purpose: Retrieves the size of the hand container.
	Parameters: none
	Return Value: int representing the size of the hand container.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: int get_size_of_hand() const
	/**
	 Retrieves the size of the hand container.
	 @return int representing the size of the hand container.
	 */
	public final int get_size_of_hand()
	{
		return hand_container.size();
	}


	/* *********************************************************************
	Function Name: get_size_of_meld
	Purpose: Retrieves the size of the meld container.
	Parameters: none
	Return Value: int representing the size of the meld container.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: int get_size_of_meld() const
	/**
	 Retrieves the size of the meld container.
	 @return int representing the size of the meld container.
	 */
	public final int get_size_of_meld()
	{
		return meld_container.size();
	}

	/* *********************************************************************
	Function Name: get_score_from_meld
	Purpose: Get the score from a single meld.
	Parameters: meld_pos, representing the meld at which position to total the score.
	Return Value: int representing the total score of a meld.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: int get_score_from_meld(int meld_pos) const
	/**
	 Get the score from a single meld.
	 @param meld_pos, representing the meld at which position to total the score.
	 @return int representing the total score of a meld.
	 */
	public final int get_score_from_meld(int meld_pos)
	{
		int score = 0;
		for (int card_pos = 0; card_pos < meld_container.get(meld_pos).size(); card_pos++)
		{
			score += meld_container.get(meld_pos).get(card_pos).get_point_value();
		}
		return score;
	}

	/* *********************************************************************
	Function Name: get_total_score
	Purpose: Retrieves the total score for hands and melds. 
	Parameters: has_gone_out, a bool representing if the current player has gone out. 
	Return Value: an int representing the total score.
	Algorithm: 
				1. For each card in the hand, sum up all of its point values. 
				2. For each card in each meld, sum up all of it's point values. 
					If the meld is greater than 7, add a bonus of 500 or 300 depending on if it's an
					all natural meld or a mixed meld (i.e. naturals and wilds). 
				3. Return the difference of the sum of the melds and sum of the hands. 
	Local variables: 
						 hand_score_subtraction, an int representing the summation of all of the point value scores in the hand.
						 meld_score_addition, an int representing the summation of all of the point value scores in the meld container.
						 is_natural_meld, a bool representing if a meld is full of all natural cards or not. 
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: int get_total_score(boolean has_gone_out) const

	/**
	 Retrieves the total score for hands and melds.
	 @param  has_gone_out, a bool representing if the current player has gone out.
	 @return an int representing the total score.
	 */
	public final int get_total_score(boolean has_gone_out)
	{
		int hand_score_subtraction = 0;
		int meld_score_addition = 0;
		boolean is_natural_meld = true;

		for (int card_pos = 0; card_pos < hand_container.size(); card_pos++)
		{
			hand_score_subtraction += hand_container.get(card_pos).get_point_value();
		}

		for (int meld_pos = 0; meld_pos < meld_container.size(); meld_pos++)
		{
			for (int meld_card_pos = 0; meld_card_pos < meld_container.get(meld_pos).size(); meld_card_pos++)
			{
				if (meld_container.get(meld_pos).get(meld_card_pos).isWild())
				{
					is_natural_meld = false;
				}
				meld_score_addition += meld_container.get(meld_pos).get(meld_card_pos).get_point_value();
			}
			if (is_natural_meld == true && meld_container.get(meld_pos).size() >= 7 && has_gone_out)
			{
				meld_score_addition += 500;
			}
			else if (is_natural_meld == false && meld_container.get(meld_pos).size() >= 7 && has_gone_out)
			{
				meld_score_addition += 300;
			}
		}

		int total_score = meld_score_addition - hand_score_subtraction;

		return (total_score);
	}


	/* *********************************************************************
	Function Name: size_of_non_spec_melds
	Purpose: Retrieves the size of the non special melds in the meld container.
	Parameters: none
	Return Value: int representing the size of the non special melds in the meld container.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: int size_of_non_spec_melds() const

	/**
	 size_of_non_spec_melds
	 @return int representing the size of the non special melds in the meld container.
	 */
	public final int size_of_non_spec_melds()
	{
		int non_spec_meld_counter = 0;
		for (int meld_pos = 0; meld_pos < meld_container.size(); meld_pos++)
		{

			if (!meld_container.get(meld_pos).get(0).isSpecial())
			{
			non_spec_meld_counter++;
			}
		}
		return non_spec_meld_counter;
	}


	/* *********************************************************************
	Function Name: get_wilds_cards
	Purpose: Retrieves all wild cards from a meld.
	Parameters: meld_pos: an integer representing the position of the meld from which to be extracted.
	Return Value: vector of Cards representing all the wild cards in a given meld.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: STLVector<Card> get_wild_cards(int meld_pos) const
	/**
	 Retrieves all wild cards from a meld.
	 @param meld_pos  an integer representing the position of the meld from which to be extracted.
	 @return  ArrayList of Cards representing all the wild cards in a given meld.
	 */
	public final ArrayList<Card> get_wild_cards(int meld_pos)
	{
		ArrayList<Card> extraction_meld = meld_container.get(meld_pos);
		ArrayList<Card> wild_meld = new ArrayList<Card>();
		for (int meld_pos_itr = 0; meld_pos_itr < extraction_meld.size(); meld_pos_itr++)
		{
			if (extraction_meld.get(meld_pos_itr).isWild() && !(extraction_meld.get(meld_pos_itr).get_has_transferred()))
			{
				wild_meld.add(extraction_meld.get(meld_pos_itr));
			}
		}
		return new ArrayList<Card>(wild_meld);
	}


	/* *********************************************************************
	Function Name: get_wilds_cards
	Purpose: Retrieves all wild cards from a meld, except it ignores if the wild cards have been transferred.
	Parameters: meld_pos: an integer representing the position of the meld from which to be extracted.
	Return Value: vector of Cards representing all the wild cards in a given meld.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: STLVector<Card> get_wild_cards_ignore_transfer(int meld_pos) const
	/**
	 Retrieves all wild cards from a meld, except it ignores if the wild cards have been transferred.
	 @param meld_pos: an integer representing the position of the meld from which to be extracted.
	 @return ArrayList of Cards representing all the wild cards in a given meld.
	 */
	public final ArrayList<Card> get_wild_cards_ignore_transfer(int meld_pos)
	{
		ArrayList<Card> extraction_meld = meld_container.get(meld_pos);
		ArrayList<Card> wild_meld = new ArrayList<Card>();
		for (int meld_pos_itr = 0; meld_pos_itr < extraction_meld.size(); meld_pos_itr++)
		{
			if (extraction_meld.get(meld_pos_itr).isWild())
			{
				wild_meld.add(extraction_meld.get(meld_pos_itr));
			}
		}
		return new ArrayList<Card>(wild_meld);
	}

	public final ArrayList<Card> get_wild_cards_ignore_transfer_unsafe(int meld_pos)
	{
		ArrayList<Card> extraction_meld = meld_container.get(meld_pos);
		ArrayList<Card> wild_meld = new ArrayList<Card>();
		for (int meld_pos_itr = 0; meld_pos_itr < extraction_meld.size(); meld_pos_itr++)
		{
			if (extraction_meld.get(meld_pos_itr).isWild())
			{
				wild_meld.add(extraction_meld.get(meld_pos_itr));
			}
		}
		return wild_meld;
	}
	/* *********************************************************************
	Function Name: get_wild_cards_from_hand
	Purpose: Retrieves all wild cards from a the hand.
	Parameters: none 
	Return Value: vector of Cards representing all the wild cards in the hand container.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: STLVector<Card> get_wild_cards_from_hand() const
	/**
	 Retrieves all wild cards from a the hand.
	 vector of Cards representing all the wild cards in the hand container.
	 */
	public final ArrayList<Card> get_wild_cards_from_hand()
	{
		ArrayList<Card> wild_vector = new ArrayList<Card>();

		for (int card_pos = 0; card_pos < hand_container.size(); card_pos++)
		{
			if (hand_container.get(card_pos).isWild() && hand_container.get(card_pos).get_has_transferred() == false)
			{
				wild_vector.add(hand_container.get(card_pos));
			}
		}
		return new ArrayList<Card>(wild_vector);

	}



	/* *********************************************************************
	Function Name: get_meld
	Purpose: Retrieves the meld container.
	Parameters: none
	Return Value: vector of vectors of cards, representing the meld container.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: STLVector<STLVector<Card>> get_meld() const
	/**
	 Retrieves Retrieves the meld container.
	 @return Arraylist of Arraylist of cards, representing the meld container.
	 */
	public final ArrayList<ArrayList<Card>> get_meld()
	{
		ArrayList<ArrayList<Card>> tmp_meld_container = new ArrayList<ArrayList<Card>>();
		for (int i = 0; i < meld_container.size();i++) {
			tmp_meld_container.add(new ArrayList<Card> (meld_container.get(i)));
		}
		
		return new ArrayList<ArrayList<Card>>(tmp_meld_container);
	}

	public final ArrayList<ArrayList<Card>> get_meld_unsafe() {
		return meld_container;
	}







	/* *********************************************************************
	Function Name: get_hand_container
	Purpose: Retrieves the hand container.
	Parameters: none
	Return Value: a vector of Cards representing the hand container.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: STLVector<Card> get_hand_container() const
	public final ArrayList<Card> get_hand_container()
	{
		//ArrayList<Card >tmp = new ArrayList<Card>();
		return new ArrayList<Card>(hand_container);
		
		//return new ArrayList<Card>(hand_container);
	}


	/* *********************************************************************
	Function Name: get_card_from_hand
	Purpose: Retrieves a card from the hand.
	Parameters: pos: an integer representing the position of the card from which to be extracted.
	Return Value: int representing the size of the meld container.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: Card get_card_from_hand(int pos) const
	public final Card get_card_from_hand(int pos)
	{
		return new Card(hand_container.get(pos));
	}

	/* *********************************************************************
	Function Name: get_card_from_meld
	Purpose: A card from a meld given a position.
	Parameters: 
				  meld_pos, an int representing the meld from which to extract.
				  card_pos, an int representing the position of the card in the meld.
	Return Value: A Card representing the card in the meld.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: Card get_card_from_meld(int meld_pos, int card_pos) const
	public final Card get_card_from_meld(int meld_pos, int card_pos)
	{
		return new Card(meld_container.get(meld_pos).get(card_pos));
	}



	/* *********************************************************************
	Function Name: print_all_wilds_of_meld
	Purpose: Retrieves a card from the hand.
	Parameters: meld_pos: an integer representing the position of the meld from which to be extracted.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: void print_all_wilds_of_meld(int meld_pos) const
	public final void print_all_wilds_of_meld(int meld_pos)
	{
		ArrayList<Card> wild_meld = meld_container.get(meld_pos);

		int wild_counter = 0;

		for (int meld_pos_itr = 0; meld_pos_itr < wild_meld.size(); meld_pos_itr++)
		{
			if (wild_meld.get(meld_pos_itr).isWild() && wild_meld.get(meld_pos_itr).get_has_transferred() == false)
			{
				wild_counter++;
				System.out.print(wild_counter);
				System.out.print(". ");
				System.out.print(wild_meld.get(meld_pos_itr).get_card_string());
				System.out.print("\n");
			}
		}
	}





	/* *********************************************************************
	Function Name: clear_transfer_states
	Purpose: Sets the transferred state to false for all cards in a meld.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void clear_transfer_states()
	{
		for (int meld_pos = 0; meld_pos < meld_container.size(); meld_pos++)
		{
			ArrayList<Card> meld_to_change = meld_container.get(meld_pos);
			for (int card = 0; card < meld_to_change.size(); card++)
			{
				meld_container.get(meld_pos).get(card).set_has_transferred(false);
			}
		}
	}

	/* *********************************************************************
	Function Name: clear_all_data
	Purpose: Clears both the hand and meld container.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void clear_all_data()
	{
		hand_container.clear();
		meld_container.clear();
	}


	/* *********************************************************************
	Function Name: set_meld
	Purpose: Sets the meld container to another vector of vectors of Cards representing the meld container.
	Parameters: meld_container, a vector of vectors of cards representing a meld container.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void set_meld(ArrayList<ArrayList<Card>> meld_container)
	{
		this.meld_container = new ArrayList<ArrayList<Card>>(meld_container);
	}


	/* *********************************************************************
	Function Name: set_hand
	Purpose: Sets the meld container to another vector of vectors of Cards representing the meld container.
	Parameters: hand_container, a vector of cards representing a hand container.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void set_hand(ArrayList<Card> hand_container)
	{
		this.hand_container = new ArrayList<Card>(hand_container);
	}


	/* *********************************************************************
	Function Name: sort
	Purpose: sorts the hand container.
	Parameters: none.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void sort()
	{
		//Collections.sort(hand_container);
		Collections.sort(hand_container, new Comparator <Card>() {
			public int compare (Card lhs, Card rhs) {
				if (lhs.get_point_value() < rhs.get_point_value()) {
					return -1;
				}
				else if (lhs.get_point_value() > rhs.get_point_value()) {
					return 1;
				}
				else {
					return 0;
				}
			}
		});
	}



}