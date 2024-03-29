package com.example.myapplication;

import java.io.*;

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
* Date : 9/28/22*
*********************************************************** */


public final class Card implements Comparable<Card>, Serializable
{

	/* *********************************************************************
	Function Name: Card
	Purpose: The default constructor of the Card class. Uses constructor member initilization.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */

	public Card()
	{
		this.face = 0;
		this.suit = 0;
		this.point_value = 0;
		//strings cannot be constructor initialized,
		//so they need to be set manually.
		this.string_representation = "EMPTY DISCARD PILE";
		this.has_transferred = false;
	}


	/* *********************************************************************
	Function Name: Card
	Purpose: The default constructor of the Card class.
	Parameters: 
					suit: a passed by value integer that represents the suit of the card (0-3).
					face: a passed by value integer that represents the face of the card (2-14), 
					where 11-13 are the royal faces: ace, jack, queen, king. 
	Return Value: none
	Algorithim: 
					1) The constructor will call translate_to_symbolic_rep, which will turn
					the face and suit integers into their char counterparts, as well as creating a string representation.
					2) The constructor will then call calculate_point_value, which will calculate the point
					value of the card. 
	Assistance Received: none
	********************************************************************* */

	public Card(int suit, int face)
	{
		translate_to_symbolic_rep(face, suit);
		calculate_point_value(this.face, this.suit);
		this.has_transferred = false;
	}


	/* *********************************************************************
	Function Name: Card
	Purpose: The "full" constructor of the Card Class. The parameters passed are the full information about the card.
	Parameters: 
					suit: an integer that represents the suit of the card (0-3).
					face: an integer that represents the face of the card (2-14),
					where 11-13 are the royal faces: ace, jack, queen, king.
					string_representation: The string representation of the Card passed by value. For instance, a three of hearts
					is 3H.
					point_value: An integer representing the Card's value in points. For instance, if the card is 3H, it will be 
					worth 100 points. 
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public Card(char face, char suit, String string_reprensentation, int point_value)
	{
		this.face = face;
		this.suit = suit;
		this.point_value = point_value;
		//strings cannot be constructor initialized,
		//so they need to be set manually.
		this.string_representation = string_reprensentation;
		this.has_transferred = false;
	}


	/* *********************************************************************
	Function Name: Card
	Purpose: The constructor for only being passed a string representation, which fills in the rest of it's
				with the string representation.
	Parameters:
					string_representation: a string which represents the string value of the Cards.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public Card(String string_representation)
	{
		this.string_representation = string_representation;
		this.face = string_representation.charAt(0);
		this.suit = string_representation.charAt(1);
		this.has_transferred = false;
		calculate_point_value(face, suit);
	}


	/* *********************************************************************
	Function Name: Card
	Purpose: The copy constructor for the Card class.
	Parameters: other_card, a Card reference to the Card being copied from.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public Card(final Card other_card)
	{
		point_value = other_card.get_point_value();
		face = other_card.get_card_face();
		suit = other_card.get_card_suit();
		string_representation = other_card.get_card_string();
		this.has_transferred = other_card.get_has_transferred();
	}


	/* *********************************************************************
	Function Name: Card operator=
	Purpose: The copy assignment operator for the Card class.
	Parameters: other_card, a Card reference to the Card being copied from..
	Return Value: A card value that is a copy of the passed Card class.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER NOTE: This 'copyFrom' method was converted from the original copy assignment operator:
//ORIGINAL LINE: Card operator =(const Card& other_card)
	public Card copyFrom(final Card other_card)
	{
		this.face = other_card.face;
		this.suit = other_card.suit;
		this.point_value = other_card.point_value;
		this.string_representation = other_card.string_representation;
		this.has_transferred = other_card.has_transferred;

		return this;

	}



	/* *********************************************************************
	Function Name: Card
	Purpose: The destructor for the Card class.
	Parameters: none
	Return Value: none
	Assistance Received: none
	********************************************************************* */

	public final void close()
	{
		face = '0';
		suit = '0';
		string_representation = "";
		point_value = 0;
		has_transferred = false;
	}


	/* *********************************************************************
	Function Name: translate_to_symbolic_rep
	Purpose: Translate the integers representing the face and suit into their char representations and create a
	string representation using those two chars. 
	Parameters:
					suit: an integer that represents the suit of the card (0-3).
					face: an integer that represents the face of the card (2-14),
					where 11-13 are the royal faces: ace, jack, queen, king
	Return Value: void
	Local Variables: 
		ascii_offset: An offset used to convert an integer to its respective char. 
	Algorithm:
					1) Switch values depending on the suit, assigning a char value based on the number. 
					2) If a face number is under 10, type cast the number plus the ASCII offset
					using static cast. 
					3) If the face number is greater or equal to 10, switch the face depending on the value
					and assign the value accordingly. For instance, 10 will be translated to X. 
					4) Append the two characters into string_representation, so that it can be printed out
					later. 
	Assistance Received: In terms of solving the problem of two characters being concatenated to a string, 
	I decided to append it based upon this answer: 
	https://stackoverflow.com/questions/51017979/joining-two-characters-in-c
	********************************************************************* */
	public final void translate_to_symbolic_rep(int face, int suit)
	{

		//Since the program is converting from ints to chars and with
		//the way char arithmetic works, an offset of 48 will be needed to
		//properly convert an integer to a char. For instance, 1+48=49, which
		//index corresponds to 1 in ASCII.
		int ascii_offset = 48;

		//This switch case uses multiple cases.
		//Unfortunately, Visual C++ doesn't support case ranges,
		//So I had to put all the cases.
		switch (suit)
		{
		case 0:
			this.suit = 'H';
			break;
		case 1:
			this.suit = 'D';
			break;
		case 2:
			this.suit = 'C';
			break;
		case 3:
			this.suit = 'S';
			break;
		}

		if (face < 10)
		{
			//I personally used a static cast so as not to lose any data
			//associated with the char, compared to (char)
			//which might potentially lose data.
			this.face = (char) (face + ascii_offset);
		}
		else
		{
			switch (face)
			{
			case 10:
				this.face = 'X';
				break;
			case 11:
				this.face = 'A';
				break;
			case 12:
				//IMPORTANT NOTE: J is a Jack, not a Joker. Joker's will
				//be added manually, as there are only 4.
				this.face = 'J';
				break;
			case 13:
				this.face = 'Q';
				break;
			case 14:
				this.face = 'K';
				break;
			}
		}
		//Without string conversion, or the appriopriate char
		//arithmetic, it'll be different convert the two chars into a string,
		//so it's easiest just to use std::string's built in push_back function.
		//Making another string could've also been done, but that seems to just be a
		//waste of space.
		this.string_representation = String.valueOf(this.face) + String.valueOf(this.suit);
	}


	/* *********************************************************************
	Function Name: calculate_point_value
	Purpose: Calculate the point value of each Card. 
	Parameters:
					face: a char representing the card's face. An example would be a 1 would be a 1 and K is a King.
					suit: a char representing the card's suite. An example would be a Diamond is D.
	Return Value: void
	Algorithm:
					1) Make a switch case depending on the face. If it's a 3, seperate the case using and if/else statement.
					2) Assign point value within individual cases. 
	Assistance Received: none
	********************************************************************* */
	public final void calculate_point_value(char face, char suit)
	{
		//This switch case uses multiple cases.
		//Unfortunately, Visual C++ doesn't support case ranges.
		//So I had to put all the cases.
		switch (face)
		{
		case 'A':
		case '2':
			this.point_value = 20;
			break;
		case '3':
			//Check if the 3 is of a red suite.
			//Otherwise, assign it a black 3's value.
			if (suit == 'D' || suit == 'H')
			{
				this.point_value = 100;
			}
			else
			{
				this.point_value = 5;
			}
			break;
		case '4':
		case '5':
		case '6':
		case '7':
			this.point_value = 5;
			break;
		case '8':
		case '9':
		case 'X':
		case 'J':
			//https://stackoverflow.com/questions/5502548/checking-if-a-number-is-an-integer-in-java
			if (Character.isDigit(suit))
			{
				this.point_value = 50;
			}
			else
			{
				this.point_value = 10;
			}
			break;
		case 'Q':
		case 'K':
			this.point_value = 10;
			break;
		}
	}


	/* *********************************************************************
	Function Name: get_card_face()
	Purpose: Selector for the face variable.
	Parameters: none
	Return Value: face, a char representing the card's face.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: char get_card_face() const
	public final char get_card_face()
	{
		return face;
	}


	/* *********************************************************************
	Function Name: get_card_suit()
	Purpose: Selector for the suit variable.
	Parameters: none
	Return Value: suit, a char representing the card's suit.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: char get_card_suit() const
	public final char get_card_suit()
	{
		return suit;
	}


	/* *********************************************************************
	Function Name: get_card_string()
	Purpose: Selector for the string_representation variable.
	Parameters: none
	Return Value: string_representation, a string representing the card face and suit. 
	An example would be a three of hearts being 3H. 
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: String get_card_string() const
	public final String get_card_string()
	{
		return string_representation;
	}


	/* *********************************************************************
	Function Name: get_point_value()
	Purpose: Selector for the point_value variable.
	Parameters: none
	Return Value: point_value, a int representing the card. 
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: int get_point_value() const
	public final int get_point_value()
	{
		return point_value;
	}


	/* *********************************************************************
	Function Name: isWild()
	Purpose: Returns if the current card is a wild card or not. 
	Parameters: none
	Return Value: bool for if the current card is a wild card or not.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean isWild() const
	public final boolean isWild()
	{
		//Jokers are notated as J1, J2, so their
		//suit is numeric.
		if (face == '2' || (Character.isDigit(suit)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	/* *********************************************************************
	Function Name: isSpecial()
	Purpose: Returns if the current card is a special card
	Parameters: none
	Return Value: bool for if the current card is a special card or not.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean isSpecial() const
	public final boolean isSpecial()
	{
		if (face == '3')
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	/* *********************************************************************
	Function Name: isNatural()
	Purpose: Returns if the current card is a natural card.
	Parameters: none
	Return Value: bool for if the current card is a natural card or not.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean isNatural() const
	public final boolean isNatural()
	{
		return (!(isSpecial() || isWild()));
	}

	/* *********************************************************************
	Function Name: is_red_three()
	Purpose: Returns if the current card is a red three.
	Parameters: none
	Return Value: bool for if the current card is a red three card or not.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean is_red_three() const
	public final boolean is_red_three()
	{
		if (string_representation.equals("3H") || string_representation.equals("3D"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	/* *********************************************************************
	Function Name: is_joker()
	Purpose: Returns if the current card is a joker card by checking if the suite is a digit.
	Parameters: none
	Return Value: bool for if the current card is a natural card or not.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean is_joker() const
	public final boolean is_joker()
	{
		return (Character.isDigit(suit));
	}


	/* *********************************************************************
	Function Name: get_has_transferred()
	Purpose: Returns if the current card is transferred by getting the data member variable has_transferred,
	which represents this symbolically.
	Parameters: none
	Return Value: bool for if the data member variable has_transferred is true or false.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean get_has_transferred() const
	public final boolean get_has_transferred()
	{
		return has_transferred;
	}



	/* *********************************************************************
	Function Name: get_numeric_value()
	Purpose: Translates the face of the card (stored as a char) back to an int. 
				Typically used for sorting cards in a vector.
	Parameters: none
	Return Value: int representing the integer value of the face of the card.
	Algorithm: 
				1). A switch case is provided that assigns a case based on the face of the card.
				2). Depending on the case, it'll return an int value corresponding to the value of the card. 
				    For non numeric values, it'll return the number based on their position after being made in the Deck.
					 In the case of J, depending on the suit of the card, it'll either be 12(Jack's numeric value)
					 or 15 (Joker's numeric value).
	Local variables: 
		int ascii_offset: a local variable representing 48, a constant to convert from char to int using
		char arithmetic.
	Assistance Received: none
	********************************************************************* */
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: int get_numeric_value() const
	public final int get_numeric_value()
	{
		//this is a value that helps us convert chars to ints using ASCII arithimetic.
		int ascii_offset = 48;

		switch (face)
		{
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return (int)face - ascii_offset;
		//the following cases are their numeric values after being added to the deck.
		//note that J is shared by both Jack and Joker, so a ternary operator is used.
		case 'X':
			return 10;
		case 'A':
			return 11;
		case 'J':
			//check if it's a Joker or Jack by seeing it the suit is numeric(Jokers have a numeric suit).
			return ((Character.isDigit(suit)) ? 15 : 12);
		case 'Q':
			return 13;
		case 'K':
			return 14;
		default:
			return -1;
		}

	}



	/* *********************************************************************
	Function Name: set_has_transferred()
	Purpose: Sets the data member has_transferred.
	Parameters: bool has_transferred, which represents what to set the variable to.
	Return Value: none
	Assistance Received: none
	********************************************************************* */
	public final void set_has_transferred(boolean has_transferred)
	{
		this.has_transferred = has_transferred;
	}
	
	
	
	
	
	
	
	private char face;
	private char suit;
	private String string_representation;
	private int point_value;
	private boolean has_transferred;
	@Override
	public int compareTo(Card other_card) {
		// TODO Auto-generated method stub
		return (this.point_value < other_card.get_point_value()) ? 1: 0;
	}
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 11 * hash + (int)face;
		hash = 11 * hash + (int)suit;
		hash = 11 * hash + point_value;
		hash = 11 * hash + (string_representation == null ? 0 : string_representation.hashCode());
		hash = 11 * hash + Boolean.valueOf(has_transferred).hashCode();
		return hash;
	}
	@Override
	public boolean equals(Object rhs) {
		if (this==rhs)
			return true;
		if (rhs == null)
			return false;
		if (getClass()!= rhs.getClass())
			return false;
		final Card other = (Card) rhs ;
		if (point_value != other.point_value)
			return false;
		if (face != other.face)
			return false;
		if (suit != other.suit)
			return false;
		if (string_representation != other.string_representation)
			return false;
		return true; 
		
	}

	//private Card(Card rhs) {
		// TODO Auto-generated method stub
	//	this.point_value=rhs.get_point_value();
	//	this.face = rhs.get_card_face();
	//	this.suit = rhs.get_card_suit();
	//	this.string_representation = rhs.get_card_string();
	//	this.point_value = rhs.get_point_value();
		//return this;
	//}
	
	
	
	
}