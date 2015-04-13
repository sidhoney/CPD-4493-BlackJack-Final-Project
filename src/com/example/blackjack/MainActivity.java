package com.example.blackjack;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

//Main Activity class
public class MainActivity extends Activity {

    Button btn1;//initialize button
    Button btn2;//initialize button

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;

        btn1 = (Button) findViewById(R.id.button_one);//Assign button to view by ID
        btn2 = (Button) findViewById(R.id.button_two);//Assign button to view by ID

        //Allot button to click
        btn1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Screentwo.class);
                startActivity(intent);

            }

        });

        btn2.setOnClickListener(new OnClickListener() {
            /**
             * The onClick class takes the player back to the game mode upon
             * click and gives the player to play again with the money value of
             * 1000
             */

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Screenthree.class);
                intent.putExtra("MoneyValue", 1000);
                startActivity(intent);

            }

        });

    }

}
