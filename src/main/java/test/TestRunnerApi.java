package test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

@CucumberOptions (
	    /*
	     * 
	     * 
	     * 		!! TestRunnerAPI !!
	     * 					API
	     */
	      		  tags = "@TagName"
	     /* 
	     * 
	     * 
	     * 
	     * 
	     * 
	     * 
	     * 
	     */
	    ,monochrome = true
    ,features = "src/test/java/api/features/"
    ,glue = "api.stepDefinitions"
    ,publish = false
    ,plugin = {"pretty"
//    		,"pretty:target/cucumber-reports/cucumber-pretty.txt"
    		,"html:target/cucumber-reports/report.html"        		
    		,"json:target/cucumber-reports/report.json"
    		,"usage:target/cucumber_usage.json"
    		}
)
@ExtendedCucumberOptions(
		jsonReport = "target/cucumber-reports/report.json",
		jsonUsageReport = "target/cucumber-usage.json",
		reportPrefix = "sample-prefix",
		outputFolder = "target/cucumber-reports/",
//				featureOverviewChart = true
		        detailedReport = true,
		        detailedAggregatedReport = true
//				,toPDF = true
		)
public class TestRunnerApi extends TestBase {
    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
		//bu noktada ilgili cihaz için kullanım süreci başlar ve tarih set edilir.
    	releaseAllDevices("android");
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }
    
    @Test(groups = "cucumber", description = "Run Cucumber Features.", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
		//bu noktada ilgili cihazın kullanım tarihi update olmalı.
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

	@DataProvider(parallel = true)
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
		//bu noktada ilgili cihaz boşa cıkarılır ve tarih kaydı silinebilir.
        testNGCucumberRunner.finish();
		CucumberResultsOverview results = new CucumberResultsOverview();
		results.setOutputDirectory("target");
		results.setOutputName("cucumber-results");
		results.setSourceFile("target/cucumber-reports/report.json");
		results.execute();
    }
}