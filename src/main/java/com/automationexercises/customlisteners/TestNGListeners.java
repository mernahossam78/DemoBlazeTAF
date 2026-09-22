package com.automationexercises.customlisteners;

import com.automationexercises.FileUtils;
import com.automationexercises.drivers.WebDriverProvider;
import com.automationexercises.media.ScreenshotManager;
import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;
import com.automationexercises.utils.report.AllureAttachementManager;
import com.automationexercises.utils.report.AllureConstants;
import com.automationexercises.utils.report.AllureEnvironmentManager;
import com.automationexercises.utils.report.AllureReportGenerator;
import com.automationexercises.validations.Validation;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;

public class TestNGListeners implements IExecutionListener, IInvokedMethodListener, ITestListener {

    public void onExecutionStart() {
        LogsManager.info("Execution started");
        cleanTestOutputDirectories();
        LogsManager.info("Test output directories cleaned");
        createTestOutputDirectories();
        LogsManager.info("Test output directories created");
        PropertyReader.loadProperties();
        LogsManager.info("Properties loaded");
        AllureEnvironmentManager.setAllureEnvironmentVariables();
        LogsManager.info("Allure environment variables set");
    }

    public void onExecutionFinish() {
        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(true);
        AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        LogsManager.info("Execution finished");
    }

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            LogsManager.info("Test Case " + testResult.getName() + " started");
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        WebDriver driver = null;
        if (method.isTestMethod()) {
            Validation.assertAll();
            if (testResult.getInstance() instanceof WebDriverProvider provider)
                driver = provider.getWebDriver(); //initialize driver from WebDriverProvider
            switch (testResult.getStatus()) {
                case ITestResult.SUCCESS -> ScreenshotManager.takeFullPageScreenshot(driver, testResult.getName());
                case ITestResult.FAILURE -> ScreenshotManager.takeFullPageScreenshot(driver, testResult.getName());
                case ITestResult.SKIP -> ScreenshotManager.takeFullPageScreenshot(driver, testResult.getName());
            }
            AllureAttachementManager.attachLogs();
            AllureAttachementManager.attachScreenshot(String.valueOf(driver), testResult.getName());
        }
    }

    public void onTestSuccess(ITestResult result) {
        LogsManager.info("Test name: " + result.getName() + " Status: PASSED");
    }

    public void onTestFailure(ITestResult result) {
        LogsManager.info("Test name: " + result.getName() + " Status: FAILED");
    }

    public void onTestSkipped(ITestResult result) {
        LogsManager.info("Test name: " + result.getName() + " Status: SKIPPED");
    }

    //Cleaning and creating dirs.

    private void cleanTestOutputDirectories() {
        //Implement logic to clean test output directories
        FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtils.cleanDirectory(new File(ScreenshotManager.SCREENSHOT_PATH));
        FileUtils.cleanDirectory(new File(LogsManager.LOGS_PATH));

    }

    private void createTestOutputDirectories() {
        FileUtils.createDirectory(ScreenshotManager.SCREENSHOT_PATH);
        //we didn't create new dirs. for logs and allure because they are created automatically
    }
}
