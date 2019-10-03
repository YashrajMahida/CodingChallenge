import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;

import java.io.IOException;

public class EmployeeAPICalls {


    public static void createEmployee () throws IOException {
        int beforeAddingEmployeeCount = getTotalEmployeeCount ();
        RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
        RequestSpecification request = RestAssured.given ();
        String employeeName = GenericMethods.generateRandowName (10);
        GenericMethods.setCellData (employeeName,1,1);
        String employeeSalary = String.valueOf (GenericMethods.generateRandomSalary ());
        GenericMethods.setCellData (employeeSalary,1,2);
        String employeeAge = String.valueOf (GenericMethods.generateRandomAge ());
        GenericMethods.setCellData (employeeAge,1,3);
        Reporting.test.log (LogStatus.INFO,"Creating employee with following details: Employee name- "+employeeName+" Employee Salary: "+employeeSalary+" Employee Age: "+employeeAge);

        JSONObject requestParams = new JSONObject ();
        requestParams.put ("name", employeeName);
        requestParams.put ("salary", employeeSalary);
        requestParams.put ("age", employeeAge);
        request.header ("Content-Type", "application/json");
        request.body (requestParams.toString ());
        Response response = request.post ("/create");
        System.out.println (response.body ().asString ());
        int statusCode = response.statusCode ();
        if (statusCode==200) {
            Assert.assertEquals (200, statusCode);
            Reporting.test.log (LogStatus.PASS, "Status Code is 200");
        }else {
            Reporting.test.log (LogStatus.FAIL,"Status code is not 200");
        }
        String responseBody = response.body ().asString ();
        Reporting.test.log (LogStatus.INFO,"Response Body: "+responseBody);
        JsonPath jsonPathEvaluator = response.jsonPath();
        String employeeID = jsonPathEvaluator.getString ("id");
        System.out.println ("Employee ID: "+employeeID);
        Reporting.test.log (LogStatus.INFO,"Employee ID is: "+employeeID);
        GenericMethods.setCellData (employeeID,1,0);
        int AfterAddingEmployeeCount = getTotalEmployeeCount ();
        if (beforeAddingEmployeeCount+1==AfterAddingEmployeeCount) {
            Assert.assertEquals (beforeAddingEmployeeCount + 1, AfterAddingEmployeeCount);
            Reporting.test.log (LogStatus.PASS,"New employee creation is successfull");
        }
    }

    public static void getEmployee (String testScenario, String EmployeeID) throws IOException {
        RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
        RequestSpecification request = RestAssured.given ();
        request.header ("Content-Type", "application/json");
        Response response = request.get("/employee/"+EmployeeID);

        //System.out.println (response.body ().asString ());
        int statusCode = response.statusCode ();
        if (statusCode==200) {
            Assert.assertEquals (200, statusCode);
            Reporting.test.log (LogStatus.PASS, "Status Code is 200");
            Reporting.test.log (LogStatus.PASS,"Employee Details: "+response.asString ());
        }else {
            Reporting.test.log (LogStatus.FAIL,"Status code is not 200");
        }
        JsonPath jsonPathEvaluator = response.jsonPath();

        switch (testScenario) {

            case "This is a positive test case scenario":
                String employee_name = jsonPathEvaluator.getString ("employee_name");
                String employee_salary = jsonPathEvaluator.getString ("employee_salary");
                String employee_age = jsonPathEvaluator.getString ("employee_age");
                Assert.assertEquals (GenericMethods.getCellData (1, 1), employee_name);
                Assert.assertEquals (GenericMethods.getCellData (1, 2), employee_salary);
                Assert.assertEquals (GenericMethods.getCellData (1, 3), employee_age);
                Reporting.test.log (LogStatus.PASS,"Employee Details verified.");
                break;

            case "This is a negative test case scenario where employee record is deleted before fetching the employee record":
                String responseBody = response.body ().asString ();
                Assert.assertEquals ("false",responseBody);
                Reporting.test.log (LogStatus.PASS,"Negative Test Pass");
                break;

            case "This is a negative test case scenario where employee record is fetched the wiht incorrect employee ID":
                String responseBody2 = response.body ().asString ();
                Assert.assertEquals ("false",responseBody2);
                Reporting.test.log (LogStatus.PASS,"Negative Test Pass");
                break;
        }

    }

    public static void updateEmployeeSalary () throws IOException {
        RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
        RequestSpecification request = RestAssured.given ();

        String employeeSalary = String.valueOf (GenericMethods.generateRandomSalary ());
        Reporting.test.log (LogStatus.INFO,"Changing employee salalry to: "+employeeSalary);
        GenericMethods.setCellData (employeeSalary,1,2);

        JSONObject requestParams = new JSONObject ();
        requestParams.put ("name", GenericMethods.getCellData (1,1));
        requestParams.put ("salary", employeeSalary);
        requestParams.put ("age", GenericMethods.getCellData (1,3));
        request.header ("Content-Type", "application/json");
        request.body (requestParams.toString ());


        request.header ("Content-Type", "application/json");
        Response response = request.put ("/update/"+GenericMethods.getCellData (1,0));
        Reporting.log.info (response.body ().asString ());
        System.out.println (response.body ().asString ());
        int statusCode = response.statusCode ();
        if (statusCode==200) {
            Assert.assertEquals (200, statusCode);
            Reporting.test.log (LogStatus.PASS, "Status Code is 200");
            Reporting.test.log (LogStatus.INFO,"Employee Salary updated");
        }else {
            Reporting.test.log (LogStatus.FAIL,"Status code is not 200");
        }
        JsonPath jsonPathEvaluator = response.jsonPath();
        String employee_name = jsonPathEvaluator.getString ("name");
        String employee_salary = jsonPathEvaluator.getString ("salary");
        String employee_age = jsonPathEvaluator.getString ("age");
        Assert.assertEquals (GenericMethods.getCellData (1,1), employee_name);
        Assert.assertEquals (GenericMethods.getCellData (1,2), employee_salary);
        Assert.assertEquals (GenericMethods.getCellData (1,3), employee_age);

    }

    public static void deleteEmployee () throws IOException {
        int beforeDeletingEmployeeCount = getTotalEmployeeCount ();
        Reporting.log.info ("Before Deleting the total number of employees are"+beforeDeletingEmployeeCount);
        RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
        RequestSpecification request = RestAssured.given ();
        request.header ("Content-Type", "application/json");
        Response response = request.delete ("/delete/"+GenericMethods.getCellData (1,0));
        int statusCode = response.statusCode ();
        JsonPath jsonPathEvaluator = response.jsonPath();
        if (statusCode==200) {
            Assert.assertEquals (200, statusCode);
            Reporting.test.log (LogStatus.PASS, "Status Code is 200");
        }else {
            Reporting.test.log (LogStatus.FAIL,"Status code is not 200");
        }
        String successMessage = jsonPathEvaluator.getString ("success.text");
        Assert.assertEquals ("successfully! deleted Records",successMessage);
        Reporting.test.log (LogStatus.PASS, successMessage);
        int AfterDeletinggEmployeeCount = getTotalEmployeeCount ();
        Reporting.log.info ("After Deleting the total number of employees are"+AfterDeletinggEmployeeCount);
        Assert.assertEquals (AfterDeletinggEmployeeCount,beforeDeletingEmployeeCount-1);

    }

    public static int getTotalEmployeeCount(){
        RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
        RequestSpecification request = RestAssured.given ();
        request.header ("Content-Type", "application/json");
        Response response = request.get ("/employees");
        int statusCode = response.statusCode ();
        Assert.assertEquals (200, statusCode);
        int totalNumberOfEmplyees = response.jsonPath ().getList ("id").size ();
        return totalNumberOfEmplyees;
    }
}
