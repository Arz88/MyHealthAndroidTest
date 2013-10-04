package com.example.myhealthmeasurementdevice.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.test.AndroidTestCase;
import device.measurements.values.Measurement;
                
public class MeasurementDeviceTest extends AndroidTestCase {
	private Measurement meassurement = null;

	public MeasurementDeviceTest() {
		super();
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		meassurement = new MockMeassurement();	
	}
	
	public void testRandomGenerator(){
		try{
			Class<?> c = meassurement.getClass();
			Method method = null;
			
			for (int i=0; i<c.getDeclaredMethods().length; i++){
				if (c.getDeclaredMethods()[i].getName().equals("getRandomValue")){
					method = c.getDeclaredMethods()[i];
					break;
				}
			}
			
			assertFalse("method should be assigned", method == null);
			
			if (method != null) {
				method.setAccessible(true);
				
				final int min = 0;
				final int max = 3;
				int randomInt = 0;
				
				int counterSuccess = 0;
				int counterFail = 0;
		
				for (int i=0; i<10000; i++){
					randomInt = (Integer) method.invoke(meassurement, min,max);
					
					//Check if randomNumber is correct and increase counters
					if (randomInt>=min && randomInt<=max){
						counterSuccess++;
					}
					else{
						counterFail++;
					}
				}
				
				//Check if counterFailed = 0 and print numbers if not true
				assertTrue("Some random numbers were wrong, failed " + counterFail + " out of " + (counterSuccess+counterFail), counterFail == 0);
			}
		
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue("Illegal argument", false);
		} catch (IllegalAccessException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue("Illegal access", false);
		} catch (InvocationTargetException e) {
			 //TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue("Invocation target", false);
		}
		
	}

}

class MockMeassurement extends Measurement{
	
	@Override
	public String getMeasurementData() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected int getRandomValue(int min, int max){
		return super.getRandomValue(min, max);
	}
}
