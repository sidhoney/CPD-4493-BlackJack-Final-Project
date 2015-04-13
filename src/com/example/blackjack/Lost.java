package com.example.blackjack; //package name

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;

public class Lost extends Activity {
	//Override or add whatever functionality you want other classes to inherit.

    Button backbtnLost; //Assign function for button which takes back to the Game mode
    ImageView lost;		//Assign function for image which displays the Image

    /**
     * The onCreate class implements an application that simply displays the
     * players lost screen as a standard output.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loosescreen);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;

        backbtnLost = (Button) findViewById(R.id.backbut2);
        backbtnLost.setOnClickListener(new OnClickListener() {

            /**
             * The onClick class takes the player back to the game mode upon
             * click and gives the player to play again with the money value of
             * 1000
             */
            @Override
            //Called when the player clicks the button
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Screenthree.class);
                intent.putExtra("MoneyValue", 1000);
                startActivity(intent);

            }

        });

    }

}
