package com.example.lab5_5.a15puzzlepritam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RunnableFuture;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][]buttons=new Button[4][4];
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp=getPreferences(MODE_PRIVATE);

        List<Integer> l=new ArrayList<>();
        for(int i=1;i<=15;i++)
            l.add(i);
        Collections.shuffle(l);
        l.add(0);



        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1);
        //LinearLayout.LayoutParams ll2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);


        int k=0;
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++){
                buttons[i][j]=new Button(this);
                String val=sp.getString(i +" "+ j,l.get(k++)+ "");//two args...key and default value
                buttons[i][j].setText(val);
                buttons[i][j].setLayoutParams(ll);
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setTag(i+" "+j);
            }
        if(buttons[3][3].getText().equals("0"))
        buttons[3][3].setText("");

        LinearLayout rows=new LinearLayout(this);
        rows.setOrientation(LinearLayout.VERTICAL);

        for(int i=0;i<4;i++){
            LinearLayout row=new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            //row.setLayoutParams(ll2);
            rows.addView(row);

            for(int j=0;j<4;j++)
                row.addView(buttons[i][j]);
        }

        LinearLayout row=new LinearLayout(this);
        TextView tvTimerText=new TextView(this);
        tvTimerText.setText("Elapsed Time: ");
        row.addView(tvTimerText);
        final TextView tvTimer=new TextView(this);
        row.addView(tvTimer);
        rows.addView(row);

        new Thread(){
            @Override
            public void run() {
               // super.run();
                try {
                    for ( i = 1; true; i++) {
                        Thread.sleep(1000);
                        runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        tvTimer.setText(i + "");
                                    }
                                }
                        );
                    }
                }
                    catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        setContentView(rows);
    }

    @Override
    public void onClick(View v) {
        Button b=(Button)v;
        String[] s=b.getTag().toString().split(" ");
        int x=Integer.parseInt(s[0]);
        int y=Integer.parseInt(s[1]);

        int[] xx={x-1,x,x+1,x};
        int[] yy={y,y-1,y,y+1};

        for(int k=0;k<4;k++){
            int i=xx[k];
            int j=yy[k];

            if(i>=0&&i<4&&j>=0&&j<4&&buttons[i][j].getText().equals("")){
                buttons[i][j].setText(b.getText());
                b.setText("");
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed(); //terminating the application by calling super classes
        //Shared Preferences
        //key(string) and value pairs
        new AlertDialog.Builder(this)
                .setTitle("save Dialog")
                .setMessage("would u like to save?")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp=getPreferences(MODE_PRIVATE);
                        SharedPreferences.Editor e=sp.edit();


                        for(int i=0;i<4;i++)
                            for(int j=0;j<4;j++)
                                e.putString(i+" "+j,buttons[i][j].getText().toString());
                                e.commit();

                        finish();
                    }
                })
                .show();

    }
}
