package com.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.testBase.TestBase;

public class ListenerEx extends TestBase implements ITestListener{

	public void onTestStart(ITestResult result) {
		test = report.createTest(result.getName());
		log.info("Testcase ready to start with name: "+result.getName());
	}

	public void onTestSuccess(ITestResult result) {
		log.info("Testcase passed successfully with name: "+result.getName());
		test.log(Status.PASS, "Testcase passed successfully");
	}

	public void onTestFailure(ITestResult result) {
		log.info("Testcase failed with name: "+result.getName());
		log.info(result.getThrowable());
		test.log(Status.FAIL, "Testcase failed");
		test.log(Status.FAIL, result.getThrowable());
		String path = takeScreenShot(result.getName());
		test.addScreenCaptureFromPath(path);
	}

	public void onTestSkipped(ITestResult result) {
		log.info("Testcase skipped with name: "+result.getName());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
		
	}

	public void onStart(ITestContext context) {
		log.info("Test suite is started  "+context.getName());
	}

	public void onFinish(ITestContext context) {
		log.info("Test suite is finished  "+context.getName());
		report.flush();
	}

}
