import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Reporting {

    public static Logger log = LogManager.getLogger (EmployeeAPITest.class.getName ());
    public static ExtentReports reports = new ExtentReports ("report.html");
    public static ExtentTest test;
}
