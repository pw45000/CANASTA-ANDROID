package com.example.myapplication;

import android.os.Build;

import java.util.*;
import java.util.stream.Collectors;

/* ***********************************************************
 * Name:  Patrick Wierzbicki*
 * Project : Canasta Project 3 *
 * Class : CMPS-366-01*
 * Date : 11/16/22*
 *********************************************************** */



public class Human extends Player
{

	/**
	 A copy constructor for human.
	 @param human a human from which to be copied from.
	 */
	public Human(Human human) {
		this.set_player_score(human.get_score());
		this.set_hand(new ArrayList<Card> (human.get_player_hand().get_hand_container()));
		this.set_meld(new ArrayList<ArrayList<Card>>(human.get_player_hand().get_meld()));
	}
	/**
	 A constructor for the human class, particularly representing all of it's data members.
	 @param score, an integer representing the player score.
	 @param hand, an ArrayList of Cards representing the hand.
	 @param melds, an ArrayList of Arraylists of Cards representing the human's melds.
	 */
	public Human(int score, ArrayList<Card> hand,  ArrayList<ArrayList<Card>> melds) {
		this.set_player_score(score);
		this.set_hand(hand);
		this.set_meld(melds);
	}
	/**
	 The default constructor for the human class.
	 */
	public Human() {
		super();
	}






	/* *********************************************************************
	Function Name: print_player_type
	Purpose: To print out the player's type.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	/**
	 Prints the player type, in this case, it's Human.
	 */
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

	/**
	 Simulates the human playing. Deprecated.
	 @param deck a Deck, representing the Round's Deck.
	 @param enemy_melds, an Arraylist of an ArrayList of Cards representing the enemy's melds.
	 @return boolean if the player should quit the round or not.
	 */
	@Override
	public boolean play(Deck deck, ArrayList<ArrayList<Card>> enemy_melds) {
		return true;
	}

	/**
	 Simulates the human drawing. Deprecated.
	 @param deck a Deck, representing the Round's Deck.
	 @return boolean if the player should quit the round or not.
	 */
	@Override
	public boolean draw(Deck deck) {
		return true;
	}
	@Override
	/**
	 Simulates the human melding. Deprecated.
	 */
	public void meld(ArrayList<ArrayList<Card>> enemy_melds) {
		return;
	}
	@Override
	/**
	 Simulates the human discarding. Deprecated.
	 @param deck a Deck, representing the Round's Deck.
	 @param enemy_melds, an Arraylist of an ArrayList of Cards representing the enemy's melds.
	 */
	public void discard(Deck deck,ArrayList<ArrayList<Card>> enemy_melds) {
		return;
	}
	@Override
	/**
	 Simulates the human going out. Deprecated.
	 */
	public boolean choose_to_go_out() {
		return true;
	}

	@Override
	public void strategy(Deck deck,ArrayList<ArrayList<Card>> enemy_melds) {
		return;
	}



	/**
	 a Clone constructor for the Human class.
	 @param original, a Human from which to be copied from.
	 */
	public static Human Clone(Human original) {
		Human clone = new Human(original.get_score(), original.get_player_hand().get_hand_container(), original.get_player_hand().get_meld());
		return clone;
	}

}













