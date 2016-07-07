package com.example.lab5_5.a15puzzlepritam;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][]buttons=new Button[4][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                buttons[i][j].setText(l.get(k++)+ "");
                buttons[i][j].setLayoutParams(ll);
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setTag(i+" "+j);
            }
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
        int p=1;
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++){
                if(buttons[i][j].getText().equals(p++)&&buttons[3][3].getText().equals(""))
                {new AlertDialog.Builder(this)
                        .setMessage("Success")
                        .show();
                break;
                }


            }


    }
}
