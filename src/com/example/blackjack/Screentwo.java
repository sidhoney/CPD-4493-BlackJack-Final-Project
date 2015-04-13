package com.example.blackjack;//package name

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Screentwo extends Activity {
    //Override or add whatever functionality you want other classes to inherit.

    Button button; //Assign function for button which takes back to the home page
    //onCreate class

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);
        addListenerOnButton();
    }

    //addListener class

    public void addListenerOnButton() {

        final Context context = this;
        //Takes the player to the home screen
        button = (Button) findViewById(R.id.backbutton);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);

            }

        });

    }

}
