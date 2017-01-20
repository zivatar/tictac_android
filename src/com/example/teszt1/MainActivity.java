package com.example.teszt1;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
	Button btnAdd;
	TextView txtValue;
	int cnt = 0;
	private DbHelper mydb;
	private String LOG = "com.example.teszt1";
	private ScoreHandler score;
	private GameBoard gb;
	private Solver solver;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        //this.deleteDatabase(mydb.DB_NAME);
        
        mydb = new DbHelper(this);
        
        score = new ScoreHandler();
        score.loadResultsFromDb(mydb);
        
        btnAdd = (Button)findViewById(R.id.btnAdd);
        txtValue = (TextView)findViewById(R.id.txtValue);
        txtValue.setText(Integer.toString(cnt));
        btnAdd.setOnClickListener(this);
        
        gb = new GameBoard();
        solver = new Solver();
    }
    
    public void refreshGameBoard() {
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    //@Override
    public void onClick(View v) {
    	cnt++;
    	txtValue.setText(Integer.toString(cnt));
    	
    	Date currentDate = new Date(System.currentTimeMillis());
    	
    	mydb = new DbHelper(this);
    	mydb.insertResult(cnt, 0);
    }
    
    public void onClick_reset(View v) {
    	score.resetResults();
    	cnt = score.getWon();
    	txtValue.setText(Integer.toString(cnt));
    }
    
    public void onClick_step(View v) {
    	switch(v.getId()) {
    	case R.id.btnBottomCenter:
    	case R.id.btnBottomLeft:
    	case R.id.btnBottomRight:
    	case R.id.btnCenterCenter:
    	case R.id.btnCenterLeft:
    	case R.id.btnCenterRight:
    	case R.id.btnTopCenter:
    	case R.id.btnTopLeft:
    	case R.id.btnTopRight:
    	}
    }
    
}
