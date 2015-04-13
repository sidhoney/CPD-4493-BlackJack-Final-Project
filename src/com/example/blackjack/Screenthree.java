package com.example.blackjack; //package name

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class Screenthree extends Activity implements SeekBar.OnSeekBarChangeListener, OnClickListener {

    // Declare three variables for the seek bar
    SeekBar aSeekBar;
    TextView aProgressTxt;
    TextView aTrackingText;

    // initialize four action buttons
    Button backbutton;
    ImageButton hitbutton, staybutton;
    ImageView playerCard3, playerCard4, playerCard5;

    // Card deck (52) cards in each deck
    static int[] cardS = new int[52];

    // Player's score arrays
    static int[] dealer = new int[5];
    static int[] player = new int[5];

    // Count on no of cards that has been called by player
    static int cardCount = 0;

    // Count on no of cards that has been called by dealer
    static int dealerCount = 3;

    // Initialize strings needed for the cards
    static String dealerCard1, dealerCard2;
    static String dealerCard3;
    static String dealerCard4;
    static String dealerCard5;
    static String playersCard1, playersCard2, playersCard3, playersCard4, playersCard5;

    // Using boolean to check if a player 'busts'
    static boolean checkDealA = false;
    static boolean checkDeal = false;
    static boolean checkPlayA = false;
    static boolean checkPlay = false;
    static boolean PlayerBust = false;
    static boolean didDealerBust = false;

    // Create the Drawable types for all 10 images in the table
    static Drawable Dcard1, Dcard2, Dcard3, Dcard4, Dcard5;
    static Drawable Pcard1, Pcard2, Pcard3, Pcard4, Pcard5;

    // Create ImageSource variables for all Drawable types in the table
    static int D1, D2, D3, D4, D5;
    static int P1, P2, P3, P4, P5;

    // Define the betting three variables
    static int currentMoney = 1000;
    static double currentBet;
    static boolean Acceptbet = false;

    // initialize winner
    static int Winner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_screen);

        // Get the extra variable sent by the other activities to restore the value of money to the default
        int resetMoney = getIntent().getExtras().getInt("MoneyValue");
        currentMoney = resetMoney; // resetting the money 
        // Make the variables for the seek bar
        aSeekBar = (SeekBar) findViewById(R.id.seek);
        aSeekBar.setOnSeekBarChangeListener(this);
        aProgressTxt = (TextView) findViewById(R.id.progress); // Find by id
        aTrackingText = (TextView) findViewById(R.id.tracking); // Find by id
        backbutton = (Button) findViewById(R.id.backbutton2); // Find by id
        hitbutton = (ImageButton) findViewById(R.id.buttonhit); // Find by id
        staybutton = (ImageButton) findViewById(R.id.buttonstay); // Find by id
        playerCard3 = (ImageView) findViewById(R.id.you3); // Find by id
        playerCard4 = (ImageView) findViewById(R.id.you4); // Find by id
        playerCard5 = (ImageView) findViewById(R.id.you5); // Find by id

        aProgressTxt.setText("Current Amount: $ " + currentMoney); //Displaying the current amount of the player

        // Randomize the deck of cards 
        cardS = returnArray();

        // Allot cards for the dealer and player
        dealerCard1 = getCard(cardS, 0);
        playersCard1 = getCard(cardS, 1);
        dealerCard2 = getCard(cardS, 2);
        playersCard2 = getCard(cardS, 3);
        dealerCard3 = getCard(cardS, 4);
        playersCard3 = getCard(cardS, 5);
        dealerCard4 = getCard(cardS, 6);
        playersCard4 = getCard(cardS, 7);
        dealerCard5 = getCard(cardS, 8);
        playersCard5 = getCard(cardS, 9);

        // Adding first two values for both dealer and player to an initialized array
        dealer[0] = checkValue(dealerCard1);
        dealer[1] = checkValue(dealerCard2);
        player[0] = checkValue(playersCard1);
        player[1] = checkValue(playersCard2);

        // Get variables that reference all of the possible cards
        final ImageView firstDealer = (ImageView) findViewById(R.id.dealer1);
        final ImageView secondDealer = (ImageView) findViewById(R.id.dealer2);
        final ImageView thirdDealer = (ImageView) findViewById(R.id.dealer3);
        final ImageView fourthDealer = (ImageView) findViewById(R.id.dealer4);
        final ImageView fifthDealer = (ImageView) findViewById(R.id.dealer5);
        final ImageView firstPlayer = (ImageView) findViewById(R.id.you1);
        final ImageView secondPlayer = (ImageView) findViewById(R.id.you2);
        final ImageView thirdPlayer = (ImageView) findViewById(R.id.you3);
        final ImageView fourthPlayer = (ImageView) findViewById(R.id.you4);
        final ImageView fifthPlayer = (ImageView) findViewById(R.id.you5);

        // Set up all the images to the right values assigned
        D1 = getResources().getIdentifier(returnPath(dealerCard1), null, getPackageName());
        Dcard1 = getResources().getDrawable(D1);
        D2 = getResources().getIdentifier(returnPath(dealerCard2), null, getPackageName());
        Dcard2 = getResources().getDrawable(D2);
        secondDealer.setImageDrawable(Dcard2);
        D3 = getResources().getIdentifier(returnPath(dealerCard3), null, getPackageName());
        Dcard3 = getResources().getDrawable(D3);
        thirdDealer.setImageDrawable(Dcard3);
        D4 = getResources().getIdentifier(returnPath(dealerCard4), null, getPackageName());
        Dcard4 = getResources().getDrawable(D4);
        fourthDealer.setImageDrawable(Dcard4);
        D5 = getResources().getIdentifier(returnPath(dealerCard5), null, getPackageName());
        Dcard5 = getResources().getDrawable(D5);
        fifthDealer.setImageDrawable(Dcard5);
        P1 = getResources().getIdentifier(returnPath("cardback"), null, getPackageName());
        Pcard1 = getResources().getDrawable(P1);
        firstPlayer.setImageDrawable(Pcard1);
        P2 = getResources().getIdentifier(returnPath("cardback"), null, getPackageName());
        Pcard2 = getResources().getDrawable(P2);
        secondPlayer.setImageDrawable(Pcard2);
        P3 = getResources().getIdentifier(returnPath(playersCard3), null, getPackageName());
        Pcard3 = getResources().getDrawable(P3);
        thirdPlayer.setImageDrawable(Pcard3);
        P4 = getResources().getIdentifier(returnPath(playersCard4), null, getPackageName());
        Pcard4 = getResources().getDrawable(P4);
        fourthPlayer.setImageDrawable(Pcard4);
        P5 = getResources().getIdentifier(returnPath(playersCard5), null, getPackageName());
        Pcard5 = getResources().getDrawable(P5);
        fifthPlayer.setImageDrawable(Pcard5);

        try {
            View.OnClickListener handler = new View.OnClickListener() {
                public void onClick(final View v) {

                    // Create the alert dialogues
                    AlertDialog.Builder betBeforeHit = new AlertDialog.Builder(v.getContext());
                    betBeforeHit.setMessage("You must place a bet before you can hit!")//Message
                            .setTitle("Alert")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog dialog_card_betBeforeHit = betBeforeHit.create();
                    dialog_card_betBeforeHit.getWindow().setGravity(Gravity.BOTTOM);

                    AlertDialog.Builder betBeforeStay = new AlertDialog.Builder(v.getContext());
                    //Alert Dialogue that displays on the screen with a message
                    betBeforeStay.setMessage("You must place a bet before you can stay!")//Message
                            .setTitle("Alert")
                            //Button post alert message
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog dialog_card_betBeforeStay = betBeforeStay.create();
                    dialog_card_betBeforeStay.getWindow().setGravity(Gravity.BOTTOM);
                    //Alert Dialogue that displays on the screen with a message
                    AlertDialog.Builder betBeforeDouble = new AlertDialog.Builder(v.getContext());
                    betBeforeDouble.setMessage("You must place a bet before you can double!")
                            .setTitle("Alert")
                            //Button post alert message
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog dialog_card_betBeforeDouble = betBeforeDouble.create();
                    dialog_card_betBeforeDouble.getWindow().setGravity(Gravity.BOTTOM);

                    AlertDialog.Builder doubleDown = new AlertDialog.Builder(v.getContext());
                    //Alert Dialogue that displays on the screen with a message
                    doubleDown.setMessage("You can only double down if you have not yet hit!")
                            .setTitle("Alert")//Message
                            //Button post alert message
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog dialog_card_doubleDown = doubleDown.create();
                    dialog_card_doubleDown.getWindow().setGravity(Gravity.BOTTOM);

                    AlertDialog.Builder playerBusted = new AlertDialog.Builder(v.getContext());
                    //Alert Dialogue that displays on the screen with a message
                    playerBusted.setMessage("The value of your cards are over 21. You loose!")
                            .setTitle("Busted!")//Message
                            //Button post alert message
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    changeEverything(false, firstPlayer, secondPlayer, thirdPlayer, fourthPlayer, fifthPlayer,
                                            firstDealer, secondDealer, thirdDealer, fourthDealer, fifthDealer, v);
                                }
                            });

                    AlertDialog dialog_card_playerBusted = playerBusted.create();
                    dialog_card_playerBusted.getWindow().setGravity(Gravity.BOTTOM);

                    AlertDialog.Builder dealerBusted = new AlertDialog.Builder(v.getContext());
                    //Alert Dialogue that displays on the screen with a message
                    dealerBusted.setMessage("The value of the dealer's cards are over 21! You won!")
                            .setTitle("You Win!")//Message
                            //Button post alert message
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    changeEverything(true, firstPlayer, secondPlayer, thirdPlayer, fourthPlayer, fifthPlayer,
                                            firstDealer, secondDealer, thirdDealer, fourthDealer, fifthDealer, v);
                                }
                            });

                    AlertDialog dialog_card_dealerBusted = dealerBusted.create();
                    dialog_card_dealerBusted.getWindow().setGravity(Gravity.BOTTOM);

                    AlertDialog.Builder playerWon = new AlertDialog.Builder(v.getContext());
                    //Alert Dialogue that displays on the screen with a message
                    playerWon.setMessage("You won the hand!")
                            .setTitle("Congrats!")//Message
                            //Button post alert message
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    changeEverything(true, firstPlayer, secondPlayer, thirdPlayer, fourthPlayer, fifthPlayer,
                                            firstDealer, secondDealer, thirdDealer, fourthDealer, fifthDealer, v);
                                }
                            });

                    AlertDialog dialog_card_playerWon = playerWon.create();
                    dialog_card_playerWon.getWindow().setGravity(Gravity.BOTTOM);

                    AlertDialog.Builder dealerWon = new AlertDialog.Builder(v.getContext());
                    //Alert Dialogue that displays on the screen with a message
                    dealerWon.setMessage("Dealer Wins")
                            .setTitle("You Loose!")//Message
                            //Button post alert message
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    changeEverything(false, firstPlayer, secondPlayer, thirdPlayer, fourthPlayer, fifthPlayer,
                                            firstDealer, secondDealer, thirdDealer, fourthDealer, fifthDealer, v);
                                }
                            });

                    AlertDialog dialog_card_dealerWon = dealerWon.create();
                    dialog_card_dealerWon.getWindow().setGravity(Gravity.BOTTOM);

                    //Using Switch Case for the cards
                    switch (v.getId()) {

                        case R.id.backbutton2: //Go back to mainScreen
                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.buttonhit: // See next players card
                            if (Acceptbet) {
                                if (cardCount == 0) {
                                    playerCard3.setVisibility(View.VISIBLE);
                                    player[2] = checkValue(playersCard3);
                                    cardCount++;
                                    PlayerBust = checkBust(player);
                                    if (PlayerBust) {
                                        dialog_card_playerBusted.show();
                                        break;
                                    }
                                } else if (cardCount == 1) {
                                    playerCard4.setVisibility(View.VISIBLE);
                                    player[3] = checkValue(playersCard4);
                                    cardCount++;
                                    PlayerBust = checkBust(player);
                                    if (PlayerBust) {
                                        dialog_card_playerBusted.show();
                                        break;
                                    }
                                } else if (cardCount == 2) {
                                    playerCard5.setVisibility(View.VISIBLE);
                                    player[4] = checkValue(playersCard5);
                                    cardCount++;
                                    PlayerBust = checkBust(player);
                                    if (PlayerBust) {
                                        dialog_card_playerBusted.show();
                                        break;
                                    } else {
                                        firstDealer.setImageDrawable(Dcard1);
                                        Winner = lastHand(thirdDealer, fourthDealer, fifthDealer);
                                        if (Winner == 1) {
                                            dialog_card_dealerBusted.show();
                                        } else if (Winner == 2) {
                                            dialog_card_dealerWon.show();
                                        } else if (Winner == 3) {
                                            dialog_card_playerWon.show();
                                        }
                                    }
                                }
                            } else {
                                betBeforeHit.show();
                            }
                            break;
                        case R.id.buttonstay:
                            if (Acceptbet) {
                                D1 = getResources().getIdentifier(returnPath(dealerCard1), null, getPackageName());
                                Dcard1 = getResources().getDrawable(D1);
                                firstDealer.setImageDrawable(Dcard1);
                                Winner = lastHand(thirdDealer, fourthDealer, fifthDealer);
                                if (Winner == 1) {
                                    dialog_card_dealerBusted.show();
                                } else if (Winner == 2) {
                                    dialog_card_dealerWon.show();
                                } else {
                                    dialog_card_playerWon.show();
                                }
                            } else {
                                betBeforeStay.show();
                            }
                            break;
                        case R.id.betbutton:
                            if (!Acceptbet) {
                                Acceptbet = true;
                                currentBet = (((double) aSeekBar.getProgress()) / 100) * (currentMoney);
                                if (currentBet == 0) {
                                    currentBet = 1;
                                }
                                P1 = getResources().getIdentifier(returnPath(playersCard1), null, getPackageName());
                                Pcard1 = getResources().getDrawable(P1);
                                firstPlayer.setImageDrawable(Pcard1);
                                P2 = getResources().getIdentifier(returnPath(playersCard2), null, getPackageName());
                                Pcard2 = getResources().getDrawable(P2);
                                secondPlayer.setImageDrawable(Pcard2);
                                aProgressTxt.setText("Current Bet: $" + currentBet);
                            }
                            break;

                        case R.id.buttondouble:

                            if (Acceptbet) {
                                if (cardCount == 0) {
                                    currentBet *= 2;
                                    D1 = getResources().getIdentifier(returnPath(dealerCard1), null, getPackageName());
                                    Dcard1 = getResources().getDrawable(D1);
                                    firstDealer.setImageDrawable(Dcard1);
                                    playerCard3.setVisibility(View.VISIBLE);
                                    player[2] = checkValue(playersCard3);
                                    cardCount++;
                                    PlayerBust = checkBust(player);
                                    if (PlayerBust) {
                                        dialog_card_playerBusted.show();
                                        break;
                                    }
                                    firstDealer.setImageDrawable(Dcard1);
                                    Winner = lastHand(thirdDealer, fourthDealer, fifthDealer);
                                    if (Winner == 1) {
                                        dialog_card_dealerBusted.show();
                                    } else if (Winner == 2) {
                                        dialog_card_dealerWon.show();
                                    } else {
                                        dialog_card_playerWon.show();
                                    }
                                } else {
                                    doubleDown.show();
                                }
                            } else {
                                betBeforeDouble.show();
                            }
                            break;
                    }
                }
            };

            // set the listeners
            findViewById(R.id.backbutton2).setOnClickListener(handler);
            findViewById(R.id.buttonhit).setOnClickListener(handler);
            findViewById(R.id.buttonstay).setOnClickListener(handler);
            findViewById(R.id.betbutton).setOnClickListener(handler);
            findViewById(R.id.buttondouble).setOnClickListener(handler);

        } catch (Exception e) {
            Log.e("Android Button Tutorial", e.toString());
        }

    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        if (Acceptbet == false) {
            aProgressTxt.setText(
                    getString(R.string.seekbar_from_touch) + " $" + (((double) aSeekBar.getProgress()) / 100) * (currentMoney) + "?");
        }
    }

    // Starting the tracking of Seekbar

    public void onStartTrackingTouch(SeekBar seekBar) {
        aTrackingText.setText(getString(R.string.seekbar_tracking_on));
    }

    // Stopping the tracking of Seekbar

    public void onStopTrackingTouch(SeekBar seekBar) {
        aTrackingText.setText(getString(R.string.seekbar_tracking_off));
    }

    //Initialize cards into an array

    public static int[] returnArray() {
        int[] cards = new int[52];
        int[] check = new int[52];
        boolean moveOn;
        for (int i = 0; i < 52; i++) {
            check[i] = i;
            cards[i] = 0;
        }
        for (int i = 0; i < 52; i++) {
            do {
                int j = (int) (Math.random() * 100) % 52;
                if (cards[j] == 0) {
                    cards[j] = i;
                    moveOn = true;
                } else {
                    moveOn = false;
                }
            } while (!moveOn);
        }
        return cards;
    }

    // using switch case for the random cards 
    public static String getCard
    (int[] inputArray, int num) {
        int j = inputArray[num];
        switch (j) {
            case 0:
                return "ba"; // Returns Spade Ace
            case 1:
                return "b2"; // Returns Spade 2
            case 2:
                return "b3"; // Returns Spade 3
            case 3:
                return "b4"; // Returns Spade 4
            case 4:
                return "b5"; // Returns Spade 5
            case 5:
                return "b6"; // Returns Spade 6
            case 6:
                return "b7"; // Returns Spade 7
            case 7:
                return "b8"; // Returns Spade 8
            case 8:
                return "b9"; // Returns Spade 9
            case 9:
                return "b10"; // Returns Spade 10
            case 10:
                return "bj"; // Returns Spade Jack
            case 11:
                return "bq"; // Returns Spade Queen
            case 12:
                return "bk"; // Returns Spade King
            case 13:
                return "ra"; // Returns Heart Ace
            case 14:
                return "r2"; // Returns Heart 2
            case 15:
                return "r3"; // Returns Heart 3
            case 16:
                return "r4"; // Returns Heart 4
            case 17:
                return "r5"; // Returns Heart 5
            case 18:
                return "r6"; // Returns Heart 6
            case 19:
                return "r7"; // Returns Heart 7
            case 20:
                return "r8"; // Returns Heart 8
            case 21:
                return "r9"; // Returns Heart 9
            case 22:
                return "r10"; // Returns Heart 10
            case 23:
                return "rj"; // Returns Heart Jack
            case 24:
                return "rq"; // Returns Heart Queen
            case 25:
                return "rk"; // Returns Heart King
            case 26:
                return "ga"; // Returns Clubs Ace
            case 27:
                return "g2"; // Returns Clubs 2
            case 28:
                return "g3"; // Returns Clubs 3
            case 29:
                return "g4"; // Returns Clubs 4
            case 30:
                return "g5"; // Returns Clubs 5
            case 31:
                return "g6"; // Returns Clubs 6
            case 32:
                return "g7"; // Returns Clubs 7
            case 33:
                return "g8"; // Returns Clubs 8
            case 34:
                return "g9"; // Returns Clubs 9
            case 35:
                return "g10"; // Returns Clubs 10
            case 36:
                return "gj"; // Returns Clubs 
            case 37:
                return "gq"; // Returns Clubs Queen
            case 38:
                return "gk"; // Returns Clubs King
            case 39:
                return "pa"; // Returns Diamonds Ace
            case 40:
                return "p2"; // Returns Diamonds 2
            case 41:
                return "p3"; // Returns Diamonds 3
            case 42:
                return "p4"; // Returns Diamonds 4
            case 43:
                return "p5"; // Returns Diamonds 5
            case 44:
                return "p6"; // Returns Diamonds 6
            case 45:
                return "p7"; // Returns Diamonds 7
            case 46:
                return "p8"; // Returns Diamonds 8
            case 47:
                return "p9"; // Returns Diamonds 9
            case 48:
                return "p10"; // Returns Diamonds 10
            case 49:
                return "pj"; // Returns Diamonds Jack
            case 50:
                return "pq"; // Returns Diamonds Queen
            case 51:
                return "pk"; // Returns Diamonds King
        }
        return "ba"; // Else final returns Spade Ace
    }

    public static String returnPath(String str) {
        return "drawable/" + str;
    }

    public static int checkValue(String val) {
        if (val.equals("ba") || val.equals("ra") || val.equals("ga") || val.equals("pa")) {
            return 1;
            // This returns 1 to indicate to the algorithm that if it can either be a 1 or 11
        } else if (val.equals("b2") || val.equals("r2") || val.equals("g2") || val.equals("p2")) {
            return 2;
        } else if (val.equals("b3") || val.equals("r3") || val.equals("g3") || val.equals("p3")) {
            return 3;
        } else if (val.equals("b4") || val.equals("r4") || val.equals("g4") || val.equals("p4")) {
            return 4;
        } else if (val.equals("b5") || val.equals("r5") || val.equals("g5") || val.equals("p5")) {
            return 5;
        } else if (val.equals("b6") || val.equals("r6") || val.equals("g6") || val.equals("p6")) {
            return 6;
        } else if (val.equals("b7") || val.equals("r7") || val.equals("g7") || val.equals("p7")) {
            return 7;
        } else if (val.equals("b8") || val.equals("r8") || val.equals("g8") || val.equals("p8")) {
            return 8;
        } else if (val.equals("b9") || val.equals("r9") || val.equals("g9") || val.equals("p9")) {
            return 9;
        } else if (val.equals("b10") || val.equals("r10") || val.equals("g10") || val.equals("p10")) {
            return 10;
        } else {
            return 10;
        }
    }

    public static boolean checkWinner(int[] dealer, int[] player) {
        // true if the case of dealer, and false if the case of player
        // totaldealerA and totalplayerA are played as if A = 11
        int totaldealerA = 0, totaldealer = 0, totalplayerA = 0, totalplayer = 0;

        // final winner boolean
        boolean winner;

        // Choosing the better hand to use the final variables
        int finalDealerTotal = 0, finalPlayerTotal = 0;

        for (int i = 0; i < dealer.length; i++) {
            totaldealer += dealer[i];
            if (dealer[i] == 1) {
                totaldealerA += 11;
            } else {
                totaldealerA += dealer[i];
            }
        }
        for (int i = 0; i < player.length; i++) {
            totalplayer += player[i];
            if (player[i] == 1) {
                totalplayerA += 11;
            } else {
                totalplayerA += player[i];
            }
        }
        // If any of these are over 21, set them equal to true
        if (totaldealerA > 21) {
            checkDealA = true;
        }
        if (totaldealer > 21) {
            checkDeal = true;
        }
        if (totalplayerA > 21) {
            checkPlayA = true;
        }
        if (totalplayer > 21) {
            checkPlay = true;
        }

        // If they are equal, set the final total to one of the values
        // If not equal, set the one with the higher value
        if (totaldealerA == totaldealer) {
            finalDealerTotal = totaldealerA;
        } else if (totaldealerA > totaldealer) {
            finalDealerTotal = totaldealerA;
        } else if (totaldealerA < totaldealer) {
            finalDealerTotal = totaldealer;
        }
        if (totalplayerA == totalplayer) {
            finalPlayerTotal = totalplayerA;
        } else if (totalplayerA > totalplayer) {
            finalPlayerTotal = totalplayerA;
        } else if (totalplayerA < totalplayer) {
            finalPlayerTotal = totalplayer;
        }
        // Check for player has busted. If yes, set the value of the hand equal to zero
        // If one has busted, set the value to the hand not busted
        if (checkDealA && checkDeal) {
            finalDealerTotal = 0;
        } else if (checkDealA) {
            finalDealerTotal = totaldealer;
        } else if (checkDeal) {
            finalDealerTotal = totaldealerA;
        }
        if (checkPlayA && checkPlay) {
            finalPlayerTotal = 0;
            // Will only reach here if one of them is not busted
        } else if (checkPlayA) {
            finalPlayerTotal = totalplayer;
        } else if (checkPlay) {
            finalPlayerTotal = totalplayerA;
        }

        if (finalPlayerTotal == finalDealerTotal) {
            winner = true;
        } else if (finalPlayerTotal < finalDealerTotal) {
            winner = true;
        } else {
            winner = false;
        }

        return winner;
    }

	public static boolean checkHit(int[] hand) {
        // Total has the Ace as 1
        int total = 0;
        // TotalA has the Ace as 11
        int totalA = 0;
        for (int i = 0; i < hand.length; i++) {
            total += hand[i];
            if (hand[i] == 1) {
                totalA += 11;
            } else {
                totalA += hand[i];
            }
        }
        if (totalA > 21 && total < 17) {
            return true;
        } else if (totalA >= 17) {
            return false;
        } else {
            return true;
        }
    }

    //Class and switch case for the last hand
    public static int lastHand(ImageView thirdDealer, ImageView fourthDealer, ImageView fifthDealer) {
        boolean didDealerBust = false;
        boolean hit;
        do {
            hit = checkHit(dealer);
            if (hit && dealerCount < 6) {
                switch (dealerCount) {
                    case 3:
                        dealer[2] = checkValue(dealerCard3);
                        didDealerBust = checkBust(dealer);
                        thirdDealer.setVisibility(View.VISIBLE);
                        dealerCount++;
                        break;
                    case 4:
                        dealer[3] = checkValue(dealerCard4);
                        didDealerBust = checkBust(dealer);
                        fourthDealer.setVisibility(View.VISIBLE);
                        dealerCount++;
                        break;
                    case 5:
                        dealer[4] = checkValue(dealerCard5);
                        didDealerBust = checkBust(dealer);
                        fifthDealer.setVisibility(View.VISIBLE);
                        dealerCount++;
                        break;
                }
            }
        } while (hit);

        if (didDealerBust) {
            // Dealer won
            return 1;
        } else {
            boolean win = checkWinner(dealer, player);
            if (win) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    //class for array

    public static boolean checkBust(int[] arr) {
        int total = 0;
        for (int i = 0; i < arr.length; i++) {
            total += arr[i];
        }
        if (total > 21) {
            return true;
        } else {
            return false;
        }
    }

    public void changeEverything(boolean win, ImageView firstPlayer, ImageView secondPlayer, ImageView thirdPlayer, ImageView fourthPlayer,
            ImageView fifthPlayer, ImageView firstDealer, ImageView secondDealer, ImageView thirdDealer, ImageView fourthDealer, ImageView fifthDealer, View v) {
        if (win) {
            currentMoney += currentBet;
        } else {
            currentMoney -= currentBet;
        }
        if (currentMoney <= 0) {
            Intent intent1 = new Intent(v.getContext(), Lost.class);
            startActivity(intent1);
        } else if (currentMoney > 10000) {
            Intent intent2 = new Intent(v.getContext(), Won.class);
            startActivity(intent2);
        }

        //Condition for current bet
        currentBet = 0;
        cardS = returnArray();
        dealerCard1 = getCard(cardS, 0);
        playersCard1 = getCard(cardS, 1);
        dealerCard2 = getCard(cardS, 2);
        playersCard2 = getCard(cardS, 3);
        dealerCard3 = getCard(cardS, 4);
        playersCard3 = getCard(cardS, 5);
        dealerCard4 = getCard(cardS, 6);
        playersCard4 = getCard(cardS, 7);
        dealerCard5 = getCard(cardS, 8);
        playersCard5 = getCard(cardS, 9);
        dealer[0] = checkValue(dealerCard1);
        dealer[1] = checkValue(dealerCard2);
        player[0] = checkValue(playersCard1);
        player[1] = checkValue(playersCard2);

        // set rest of array equal to zero
        dealer[2] = 0;
        dealer[3] = 0;
        dealer[4] = 0;
        player[2] = 0;
        player[3] = 0;
        player[4] = 0;

        // Creating case 
        D1 = getResources().getIdentifier(returnPath("cardback"), null, getPackageName());
        Dcard1 = getResources().getDrawable(D1);
        firstDealer.setImageDrawable(Dcard1);
        D2 = getResources().getIdentifier(returnPath(dealerCard2), null, getPackageName());
        Dcard2 = getResources().getDrawable(D2);
        secondDealer.setImageDrawable(Dcard2);
        D3 = getResources().getIdentifier(returnPath(dealerCard3), null, getPackageName());
        Dcard3 = getResources().getDrawable(D3);
        thirdDealer.setImageDrawable(Dcard3);
        D4 = getResources().getIdentifier(returnPath(dealerCard4), null, getPackageName());
        Dcard4 = getResources().getDrawable(D4);
        fourthDealer.setImageDrawable(Dcard4);
        D5 = getResources().getIdentifier(returnPath(dealerCard5), null, getPackageName());
        Dcard5 = getResources().getDrawable(D5);
        fifthDealer.setImageDrawable(Dcard5);
        P1 = getResources().getIdentifier(returnPath("cardback"), null, getPackageName());
        Pcard1 = getResources().getDrawable(P1);
        firstPlayer.setImageDrawable(Pcard1);
        P2 = getResources().getIdentifier(returnPath("cardback"), null, getPackageName());
        Pcard2 = getResources().getDrawable(P2);
        secondPlayer.setImageDrawable(Pcard2);
        P3 = getResources().getIdentifier(returnPath(playersCard3), null, getPackageName());
        Pcard3 = getResources().getDrawable(P3);
        thirdPlayer.setImageDrawable(Pcard3);
        P4 = getResources().getIdentifier(returnPath(playersCard4), null, getPackageName());
        Pcard4 = getResources().getDrawable(P4);
        fourthPlayer.setImageDrawable(Pcard4);
        P5 = getResources().getIdentifier(returnPath(playersCard5), null, getPackageName());
        Pcard5 = getResources().getDrawable(P5);
        fifthPlayer.setImageDrawable(Pcard5);

        // Set the non-initial cards to the invisible
        thirdPlayer.setVisibility(View.INVISIBLE);
        fourthPlayer.setVisibility(View.INVISIBLE);
        fifthPlayer.setVisibility(View.INVISIBLE);
        thirdDealer.setVisibility(View.INVISIBLE);
        fourthDealer.setVisibility(View.INVISIBLE);
        fifthDealer.setVisibility(View.INVISIBLE);

        //Alloting values to the variables
        cardCount = 0;
        dealerCount = 3;
        Acceptbet = false;
        aProgressTxt.setText("Amount: $ " + currentMoney); //Displays the amount

    }

    //Creating empty onClick class

    @Override
    public void onClick(View arg0) {

    }
}
