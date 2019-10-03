import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;

public class EmployeeAPITest {



    @Test(priority = 1)
    public static void positiveScenario() throws IOException {
        String testScenario = "This is a positive test case scenario";
        Reporting.test = Reporting.reports.startTest (testScenario);
        Reporting.test.log (LogStatus.INFO, testScenario);
        EmployeeAPICalls.createEmployee();
        Reporting.test.log (LogStatus.INFO, "Create new employee API called");
        EmployeeAPICalls.getEmployee(testScenario, GenericMethods.getCellData (1,0));
        EmployeeAPICalls.updateEmployeeSalary();
        Reporting.test.log (LogStatus.INFO, "Employee info updated");
        EmployeeAPICalls.deleteEmployee();
        Reporting.test.log (LogStatus.INFO, "Employee daleted");
    }

    @Test(priority = 2)
    public static void negativeScenarioDeleteBeforeSearching() throws IOException {
        String testScenario = "This is a negative test case scenario where employee record is deleted before fetching the employee record";
        Reporting.test = Reporting.reports.startTest (testScenario);
        Reporting.log.info (testScenario);
        EmployeeAPICalls.createEmployee();
        Reporting.test.log (LogStatus.INFO, "Create new employee API called");
        EmployeeAPICalls.deleteEmployee();
        Reporting.test.log (LogStatus.INFO, "Employee Deleted");
        EmployeeAPICalls.getEmployee(testScenario,GenericMethods.getCellData (1,0));
        Reporting.test.log (LogStatus.INFO, "Verifying negeative test");
    }
    @Test(priority = 3)
    public static void negativeScenarioGetEmployeeWithIncorrectID() throws IOException {
        String testScenario = "This is a negative test case scenario where employee record is fetched the wiht incorrect employee ID";
        Reporting.test = Reporting.reports.startTest (testScenario);
        EmployeeAPICalls.getEmployee(testScenario, GenericMethods.getCellData (1,4));
        Reporting.test.log (LogStatus.INFO, "Verifying negeative test");
    }

    @AfterMethod
    public void afterClass(){
        Reporting.reports.endTest (Reporting.test);
        Reporting.reports.flush ();
    }

}
