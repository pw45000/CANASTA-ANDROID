package com.example.model;

import java.util.*;

/* ***********************************************************
* Name:  Patrick Wierzbicki*
* Project : Canasta C++ Project 1*
* Class : CMPS-366-01*
* Date : 9/28/22*
*********************************************************** */
/* ***********************************************************
* Name:  Patrick Wierzbicki*
* Project : Cansta P1*
* Class : class numberand name here*
* Date : 9/13/22*
*********************************************************** */
/* ***********************************************************
* Name:  Patrick Wierzbicki*
* Project : Canasta C++ Project 1*
* Class : CMPS-366-01*
* Date : 9/28/22*
*********************************************************** */

public class Deck
{

	/* *********************************************************************
	Function Name: Deck
	Purpose: The default deck constructor. It creates the stock pile and discard pile, and continues drawing from 
				the stock pile until a red three is not found and adds said drawn card to the discard pile.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public Deck()
	{

		create_stock_pile();

		shuffle_stock();

		Card first_discard = new Card();
		//If the card is a red three, draw another card.
		do
		{
			first_discard.copyFrom(draw_from_stock());
			discard_push_front(new Card(first_discard));
		} while (first_discard.is_red_three() || first_discard.isWild());
	}




	/* *********************************************************************
	Function Name: create_stock_pile()
	Purpose: Creates the stock pile by creating two decks of 52 cards (4 suits of 13 cards), and adds each card to
				the stock pile, manually adding in jokers.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void create_stock_pile()
	{
		for (int decks = 0; decks < 2; decks++)
		{
			for (int suite = 0; suite < 4; suite++)
			{
				for (int face = 2; face < 15; face++)
				{
					stock_pile.add(new Card(suite, face));
				}
			}
		}
		//add jokers to the deck manually, since they're not
		//part of a standard deck.
		stock_pile.add(new Card('J', '1', "J1", 50));
		stock_pile.add(new Card('J', '2', "J2", 50));
		stock_pile.add(new Card('J', '3', "J1", 50));
		stock_pile.add(new Card('J', '4', "J2", 50));

	}

	/* *********************************************************************
	Function Name: shuffle_stock()
	Purpose: Shuffles the stock pile using a seed based on the OS.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void shuffle_stock()
	{
        Collections.shuffle(stock_pile);
        //https://www.geeksforgeeks.org/shuffle-or-randomize-a-list-in-java/
	}



	/* *********************************************************************
	Function Name: draw_from_stock()
	Purpose: Draws a single card from stock.
	Parameters: none
	Return Value: Card representing the card drawn.
	Assistance Received: none
	********************************************************************* */
	public final Card draw_from_stock()
	{
		Card card_drawn;
		//dereference the .begin() iterator.
		//while I could've used the 0 index,
		//this seems to be a lot more intuitive.
		card_drawn = stock_pile.get(0);
//C++ TO JAVA CONVERTER TODO TASK: There is no direct equivalent to the STL vector 'erase' method in Java:
		stock_pile.remove(0);

		return new Card(card_drawn);
	}


	/* *********************************************************************
	Function Name: draw_from_discard()
	Purpose: Draws the entire discard pile and returns it.
	Parameters: none
	Return Value: Vector of Cards representing the picked up discard pile.
	Assistance Received: none
	********************************************************************* */
	public final ArrayList<Card> draw_from_discard()
	{
		ArrayList<Card> copy_of_discard = new ArrayList<Card>(discard_pile);
		discard_pile.clear();

		return new ArrayList<Card>(copy_of_discard);
	}



	/* *********************************************************************
	Function Name: get_top_discard_pile()
	Purpose: Returns if the discard pile is frozen.
	Parameters: none
	Return Value: Card representing the top of the discard pile.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: Card get_top_discard_pile() const
	public final Card get_top_discard_pile()
	{
		if (discard_pile.size() != 0)
		{
			return discard_pile.get(0);
		}
		else
		{
			return new Card();
		}
	}


	/* *********************************************************************
	Function Name: stock_is_empty()
	Purpose: Returns if the stock pile is empty or not.
	Parameters: none
	Return Value: bool representing if the stock pile is empty.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean stock_is_empty() const
	public final boolean stock_is_empty()
	{
		return stock_pile.size() == 0;
	}


	/* *********************************************************************
	Function Name: discard_is_empty()
	Purpose: Returns if the discard pile is empty or not.
	Parameters: none
	Return Value: bool representing if the discard pile is empty.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean discard_is_empty() const
	public final boolean discard_is_empty()
	{
		return discard_pile.size() == 0;
	}


	/* *********************************************************************
	Function Name: both_piles_are_empty()
	Purpose: Returns if both the discard and stock pile is empty.
	Parameters: none
	Return Value: bool representing if both the discard pile and stock pile are empty.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean both_piles_are_empty() const
	public final boolean both_piles_are_empty()
	{
		return (stock_is_empty() && discard_is_empty());
	}


	/* *********************************************************************
	Function Name: get_discard_is_frozen()
	Purpose: Returns if the discard pile is frozen.
	Parameters: none
	Return Value: bool representing if both the discard pile is frozen.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean get_discard_is_frozen() const
	public final boolean get_discard_is_frozen()
	{
		if (discard_pile.size() != 0)
		{
			return discard_is_frozen || get_top_discard_pile().isSpecial();
		}
		else
		{
			return discard_is_frozen;
		}
	}


	/* *********************************************************************
	Function Name: get_size_of_discard
	Purpose: Empties the discard pile.
	Parameters: none
	Return Value: int, representing the size of the discard pile.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: int get_size_of_discard() const
	public final int get_size_of_discard()
	{
		return discard_pile.size();
	}


	/* *********************************************************************
	Function Name: print_stock_pile()
	Purpose: Prints the contents of the stock pile.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: void print_stock_pile() const
	public final void print_stock_pile()
	{
		for (Card card : stock_pile)
		{
			System.out.print(card.get_card_string());
			System.out.print(" ");
		}
		System.out.print("\n");
	}


	/* *********************************************************************
	Function Name: print_top_of_discard_pile()
	Purpose: Prints the top of the discard pile if it isn't empty.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: void print_top_of_discard_pile() const
	public final void print_top_of_discard_pile()
	{
		if (discard_is_empty() == false)
		{
			System.out.print(discard_pile.get(0).get_card_string());
			System.out.print("\n");
		}
		else
		{
			System.out.print("\n");
		}
	}

	/* *********************************************************************
	Function Name: print_discard_pile()
	Purpose: Prints the contents of the entire discard pile.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: void print_discard_pile() const
	public final void print_discard_pile()
	{
		for (Card card : discard_pile)
		{
			System.out.print(card.get_card_string());
			System.out.print(" ");
		}
		System.out.print("\n");
	}


	/* *********************************************************************
	Function Name: print_top_of_stock
	Purpose: Prints the top of stock.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: void print_top_of_stock() const
	public final void print_top_of_stock()
	{
		if (stock_pile.size() > 0)
		{
			System.out.print(stock_pile.get(0).get_card_string());
			System.out.print("\n");
		}
	}



	/* *********************************************************************
	Function Name: discard_push_front()
	Purpose: Pushes a card that has been discarded to the front of the discard pile.
	Parameters: 
				  pushed_card, a Card representing a card added to the discard pile.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void discard_push_front(Card pushed_card)
	{
//C++ TO JAVA CONVERTER TODO TASK: There is no direct equivalent to the STL vector 'insert' method in Java:
		discard_pile.add(0, pushed_card);
	}


	/* *********************************************************************
	Function Name: set_discard_freeze()
	Purpose: Sets if the discard pile is frozen or not. 
	Parameters:
				  is_frozen, a bool representing if the discard pile is frozen.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void set_discard_freeze(boolean is_frozen)
	{
		discard_is_frozen = is_frozen;
	}


	/* *********************************************************************
	Function Name: clear_discard()
	Purpose: Empties the discard pile.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void clear_discard()
	{
		discard_pile.clear();
	}


	/* *********************************************************************
	Function Name: discard()
	Purpose: Draws the entire discard pile and returns it.
	Parameters: 
				  discarded_card, which is a Card that represents the card being
				  sent to the discard pile.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void discard(Card discarded_card)
	{
		if (discarded_card.get_card_string().equals("3S") || discarded_card.get_card_string().equals("3C"))
		{
			discard_is_frozen = true;
		}
		else if (discard_is_frozen && discarded_card.isNatural())
		{
			discard_is_frozen = false;
		}

		discard_push_front(new Card(discarded_card));
	}


	/* *********************************************************************
	Function Name: set_discard_pile
	Purpose: Sets the discard_pile data member to the passed vector of Cards parameter.
	Parameters: 
				  discard_pile, a vector of cards representing the discard pile.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void set_discard_pile(ArrayList<Card> discard_pile)
	{
		this.discard_pile = new ArrayList<Card>(discard_pile);
	}


	/* *********************************************************************
	Function Name: set_stock_pile
	Purpose: Sets the stock_pile data member to the passed vector of Cards parameter.
	Parameters:
				  stock_pile, a vector of cards representing the stock pile.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void set_stock_pile(ArrayList<Card> stock_pile)
	{
		this.stock_pile = new ArrayList<Card>(stock_pile);
	}


	//While it was tempting to use a deque
	//since the datastructure not only sounds like a deck
	//but can remove and add from the front, the lack of
	//contiguity and performance mixed with the fact that 
	//implementing a pop and push at the front is trivial
	//is why I chose a vector. 
	private ArrayList<Card> stock_pile = new ArrayList<Card>();
	private ArrayList<Card> discard_pile = new ArrayList<Card>();
	private boolean discard_is_frozen = false;
}