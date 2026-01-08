package utilities;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener
{
	    @Override
	    public void onTestFailure(ITestResult result) 
	    {
	        System.out.println("Test Failed: " + result.getName());
	    }
	    
}
