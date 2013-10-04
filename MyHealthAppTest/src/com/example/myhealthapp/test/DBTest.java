package com.example.myhealthapp.test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.test.AndroidTestCase;
import com.example.myhealthapp.db.Database;

public class DBTest extends AndroidTestCase {
	private Context context = null;
	private Database db = null;
	private Timestamp timestamp = null;
	
	public DBTest() {
		super();
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		context = this.getContext();
		db = new Database(context);
	}
	
	public void testDatabaseCreation() { 
		assertFalse("Context should not be null", context == null);
		assertFalse("Database should not be null", db == null);
		assertTrue("Database should exist", db.databaseExists());
	}
	
	public void testDatabaseInsertion() { 
		java.util.Date date= new java.util.Date();
		timestamp = new Timestamp(date.getTime());
		
		assertFalse("Database test", db == null);
		
		db.executeQuery("INSERT INTO bloodpressure VALUES ('" + timestamp + "', '15.3', '21.3')");
		List<HashMap<String, String>> list = db.sendQuery("bloodpressure", null, null, null, null, null, null, null);
		
		assertTrue("Size should be 1", list.size() == 1);
		
		String systolic = list.get(0).get("systolic");
		String diastolic = list.get(0).get("diastolic");
		
		assertTrue("Systolic should be 15.3", systolic.equals("15.3"));
		assertTrue("Diastolic should be 21.3", diastolic.equals("21.3"));
	} 
	
	public void testDatabaseWhereClause() {
		java.util.Date date= new java.util.Date();
		timestamp = new Timestamp(date.getTime()+(1000*60));
		
		db.executeQuery("INSERT INTO bloodpressure VALUES ('" + timestamp + "', '33.5', '35.5')");
		List<HashMap<String, String>> list = db.sendQuery("bloodpressure", null, "timestamp = '" + timestamp + "'", null, null, null, null, null);
		
		assertTrue("Size should be 1", list.size() == 1);
		
		String systolic = list.get(0).get("systolic");
		String diastolic = list.get(0).get("diastolic");
		
		assertTrue("Systolic should be 33.5", systolic.equals("33.5"));
		assertTrue("Diastolic should be 35.5", diastolic.equals("35.5"));
	}
	
	public void testDatabaseDeletion() { 
		assertTrue("DatabaseList should exist", db.databaseExists());
		
		String name = db.getDatabaseName();
		context.deleteDatabase(name);
		
		assertFalse("DatabaseList should not exist anymore", db.databaseExists());
		
		db.close();
	}

}
