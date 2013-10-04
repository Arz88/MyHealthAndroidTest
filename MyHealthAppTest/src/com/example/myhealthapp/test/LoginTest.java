package com.example.myhealthapp.test;

import com.example.myhealthapp.LoginActivity;
import com.example.myhealthapp.R;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {
	
	private Activity loginActivity;
	private TextView password; 
	private TextView username; 
	private static final String D_PASSWORD = "190";
	private static final String D_USERNAME = "IK";
	
	@SuppressWarnings("deprecation")
	public LoginTest() {
		super("com.example.myhealthapp", LoginActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		super.setActivityInitialTouchMode(false);
		loginActivity = getActivity();
		password = (TextView) loginActivity.findViewById(R.id.password);
		username = (TextView) loginActivity.findViewById(R.id.username);
	}
	
	public void testTextViewInput() { 
		
		//Has to run in a separate 'runOnUiThread to have access to requestFocus() method
		new Thread() {
	        @Override
	        public void run() {
				try{
					runTestOnUiThread(new Runnable() {
						boolean start = false;
			            @Override
			            public void run() {
			            	if (!start){
				            	username.setFocusable(true);
				            	username.requestFocus();
				            	username.append(D_USERNAME);
			
				            	password.setFocusable(true);
				            	password.requestFocus();
				            	password.append(D_PASSWORD);
				            	start = true;
			            	}
			            }
					});
			
				} catch (Throwable e) {
					e.printStackTrace();
				}
	        }
		}.start();
		
		//Check input
		sendKeys("ENTER");
		String usernameText = username.getText().toString(); 
		String passwordText = password.getText().toString();
		assertTrue("Username should be " + D_USERNAME, usernameText.equals(D_USERNAME));
		assertTrue("Password should be 190 " + D_PASSWORD, passwordText.equals(D_PASSWORD));
	}  
}
