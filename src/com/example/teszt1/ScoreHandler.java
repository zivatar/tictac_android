package com.example.teszt1;

public class ScoreHandler {
	private static int won;
	private static int lost;
	private static DbHelper mydb;
	
	public static void ScoreHandler(DbHelper db) {
		mydb = db;
	}
	
	public static void loadResultsFromDb(DbHelper mydb) {
		won = mydb.getWin();
	}
	
	public static void resetResults() {
		won = 0;
		lost = 0;
		//saveResultsToDb();
	}
	
	public static void increaseWin() {
		won++;
		saveResultsToDb();
	}
	
	public static void increaseLoose() {
		lost++;
		saveResultsToDb();
	}
	
	public static void saveResultsToDb() {
		mydb.insertResult(won, 0);
		// TODO LOST
	}
	
	public static int getWon() {
		return won;
	}
	
}
