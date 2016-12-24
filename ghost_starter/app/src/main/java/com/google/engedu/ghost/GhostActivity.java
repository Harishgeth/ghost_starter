package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends ActionBarActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    public String ghost_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager=getAssets() ;
        try
        {
            InputStream inputStream=assetManager.open("words.txt");
            dictionary=new SimpleDictionary(inputStream);
        } catch (IOException e) {
            Toast toast=Toast.makeText(this,"could not load dictionary",Toast.LENGTH_LONG);
            toast.show();



        }
        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void bluff(){
        TextView text=(TextView)findViewById(R.id.ghostText);
        TextView label = (TextView) findViewById(R.id.gameStatus);
        Random r;
        r = new Random();

    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        String comp_string=dictionary.getGoodWordStartingWith(ghost_name);
        if(comp_string==null){
            bluff();
        }
        else{
            int n=comp_string.length();
            String c=comp_string.substring(n,n);

        }

        userTurn = true;
        label.setText(USER_TURN);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }
    public boolean onKeyDown (int keyCode, KeyEvent event)
    {
        userTurn=true;

        TextView label = (TextView) findViewById(R.id.gameStatus);
        TextView text = (TextView) findViewById(R.id.ghostText);
        label.setText("Your Turn");
        text.setText(ghost_name);
       char c=(char)event.getUnicodeChar();
        if(c>='a'&&(c<='z') || c>='A' && c<='Z')
        {
            ghost_name=ghost_name+c;
           text.setText(ghost_name);
            if (ghost_name.length() >=4) {
                if (dictionary.isWord(ghost_name)) {
                    label.setText("COMPUTER WINS");
                    ghost_name="";
                    return true;
                }
                else if(dictionary.getAnyWordStartingWith(ghost_name)==null){
                    label.setText("COMPUTER CHALLENGED AND WON hahaha");
                    ghost_name="";
                    return true;
                }
            }
        }
        computerTurn();
        return true;
    }
    public View challenge(View V)
    {

        TextView label = (TextView) findViewById(R.id.gameStatus);

        if (ghost_name.length() >=4) {
            if (dictionary.isWord(ghost_name)) {
                label.setText("USER WINS");
                ghost_name="";
            }
           else {
               String s=dictionary.getAnyWordStartingWith(ghost_name);
                if(s!=null){

                    label.setText("(CONT)YOUR TURN:");
                }
                else{
                    label.setText("YOU WIN:");
                    ghost_name="";

                }
            }

        }
        return V;
    }
}
