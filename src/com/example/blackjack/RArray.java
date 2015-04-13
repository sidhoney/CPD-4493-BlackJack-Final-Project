package com.example.blackjack;

//Create an empty class called RArray
public class RArray {

    //Initialize the deck of cards into array

    public int[] returnArray() {
        int[] cards = new int[52];
        int[] check = new int[52];
        boolean moveOn;
        //alloting cards to i and j
        for (int i = 0; i < 52; i++) {
            check[i] = i;
            cards[i] = 0;
        }
        for (int i = 0; i < 52; i++) {
            do {
                int j = (int) (Math.random() * 100) % 52;//Pick random cards
                if (cards[j] == 0) {
                    cards[j] = i;
                    moveOn = true;
                } else {
                    moveOn = false;
                }
            } while (!moveOn);
        }
        return cards; // Picks up the cards and returns back
    }
}
