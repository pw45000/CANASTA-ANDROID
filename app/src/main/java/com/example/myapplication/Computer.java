package com.example.myapplication;

import java.io.Serializable;
import java.util.*;

/* ***********************************************************
 * Name:  Patrick Wierzbicki*
 * Project : Canasta Project 3 *
 * Class : CMPS-366-01*
 * Date : 11/16/22*
 *********************************************************** */
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Player;

public class Computer extends Player implements Serializable
{
	/**
	 Default constructor the Computer class.
	 */
	public Computer () {
		super();
	}

	/**
	 The copy constructor for the Computer class.
	 @param comp, a which will be copied from.
	 */
	public Computer(Computer comp) {
		this.set_player_score(comp.get_score());
		this.set_hand(new ArrayList<Card> (comp.get_player_hand().get_hand_container()));
		this.set_meld(new ArrayList<ArrayList<Card>>(comp.get_player_hand().get_meld()));
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
	/**
	 Simulation of the Computer playing. Deprecated.
	 @param draw_decks, a Deck  which represents the discard and stock pile from the Round.
	 @param enemy_melds: an Arraylist of Arraylist of Card which represents the enemy's melds.
	 @return boolean representing if the player should signal to end the round.
	 */
	public boolean play(Deck draw_decks, ArrayList<ArrayList<Card>> enemy_melds)
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

		meld(new ArrayList<ArrayList<Card>>(enemy_melds));
		immeadiate_break = go_out();
		if (immeadiate_break)
		{
			return true;
		}

		discard(draw_decks, new ArrayList<ArrayList<Card>>(enemy_melds));
		immeadiate_break = go_out();
		if (immeadiate_break)
		{
			return true;
		}

		return false;
	}

	@Override
	/**
	 Simulation of the Computer discarding a card. Deprecated.
	 @param draw_decks, a Deck  which represents the discard and stock pile from the Round.
	 @param enemy_melds: an Arraylist of Arraylist of Card which represents the enemy's melds.
	 */
	public void discard(Deck draw_decks, ArrayList<ArrayList<Card>> enemy_melds)
	{
		strategy_discard(draw_decks, new ArrayList<ArrayList<Card>>(enemy_melds), get_player_type());

	}

	@Override
	/**
	 Simulation of the Computer melding. Deprecated.
	 @param enemy_melds: an Arraylist of Arraylist of Card which represents the enemy's melds.
	 */
	public void meld(ArrayList<ArrayList<Card>> enemy_melds)
	{
		strategy_meld(new ArrayList<ArrayList<Card>>(enemy_melds), get_player_type());
	}

	@Override
	/**
	 Simulation of the Computer drawing. Deprecated.
	 @param draw_decks, a Deck  which represents the discard and stock pile from the Round.@return boolean representing if the player should signal to end the round.
	 */
	public boolean draw(Deck draw_decks)
	{
		//return strategy_draw(draw_decks, get_player_type());
		return true;
	}


	/* *********************************************************************
	Function Name: strategy
	Purpose: Overrides strategy for the Computer, and prints out a message stating it doesn't need it's own strategy.
	Parameters: 
				 draw_decks: a Deck reference representing the discard and stock pile from Round.
				 enemy_melds: a vector of vectors of Cards representing the enemy's melds.
	Return Value: none.
	Assistance Received: none
	********************************************************************* */
	/**
	 Simulation of the Computer giving advice. Deprecated.
	 */
	@Override
	public void strategy(Deck draw_decks, ArrayList<ArrayList<Card>> enemy_melds)
	{
		System.out.print("The computer has it's own strategy, so it doesn't need advice...");
		System.out.print("\n");
	}


	/* *********************************************************************
	Function Name: print_player_type
	Purpose: Prints the Computer's player type.
	Parameters: none.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	/**
	 Prints a player's type.
	 */
	@Override
	public void print_player_type()
	{
		System.out.print("Computer");
	}

	/* *********************************************************************
	Function Name: choose_to_go_out
	Purpose: Give a decision as to if the Computer wants to go out.
	Parameters: none.
	Return Value: bool representing if the Computer chose to go out.
	Assistance Received: none
	********************************************************************* */
	@Override
	/**
	 Simulation of the Computer going out. Deprecated.
	 @return boolean representing if the computer went out.
	 */
	public boolean choose_to_go_out()
	{
		System.out.print("The computer chose to go out since it likes 100 point bonuses.");
		System.out.print("\n");
		return true;
	}


	/* *********************************************************************
	Function Name: get_player_type
	Purpose: returns the Computer's player type.
	Parameters: none.
	Return Value: string representing the Computer's player type.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: virtual String get_player_type() const override
	/**
	 Returns the player type, in this case a Computer.
	 @return String representing the player's type.
	 */
	@Override
	public String get_player_type()
	{
		return "Computer";
	}

}