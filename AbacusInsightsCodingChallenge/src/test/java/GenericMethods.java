import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

public class GenericMethods {

    public static String getCellData(int rowNum, int cellNum) throws IOException, NullPointerException{
    XSSFWorkbook ExcelWBook;
    XSSFSheet ExcelWSheet;
    XSSFCell ExcellWCell;
    FileInputStream ExcelFile;
    ExcelFile = new FileInputStream ("EmployeeData.xlsx");
    ExcelWBook = new XSSFWorkbook (ExcelFile);
    ExcelWSheet = ExcelWBook.getSheet ("Employee");
    ExcellWCell = ExcelWSheet.getRow (rowNum).getCell (cellNum);
    String value = ExcellWCell.toString ();
    ExcelFile.close ();
    ExcelWBook.close ();
    return value;
}

    //This method gets excel file, row and column number and set a value to the that cell.
    public static void setCellData(String value, int rowNum, int cellNum) throws IOException {
        XSSFWorkbook ExcelWBook;
        XSSFSheet ExcelWSheet;
        XSSFCell ExcellWCell;
        FileInputStream ExcelFile;
        ExcelFile = new FileInputStream ("EmployeeData.xlsx");
        ExcelWBook = new XSSFWorkbook (ExcelFile);
        ExcelWSheet = ExcelWBook.getSheet ("Employee");
        ExcellWCell = ExcelWSheet.getRow (rowNum).getCell (cellNum);
        ExcellWCell.setCellValue (value);
        FileOutputStream fos = new FileOutputStream ("EmployeeData.xlsx");
        ExcelWBook.write (fos);
        fos.flush ();
        fos.close ();
        ExcelFile.close ();
        ExcelWBook.close ();
    }

    public static String generateRandowName (int length) {
        String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabsdefghijklmnopqrstuvwxyz";
        Random random = new Random ();
        StringBuilder b = new StringBuilder ();
        for (int i = 0; i < length; i++) {
            b.append (base.charAt (random.nextInt (base.length ())));
        }
        return b.toString ();
    }

    public static int generateRandomAge(){
            int min = 0;
            int max = 105;
            Random r = new Random();
            return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
    }

    public static int generateRandomSalary(){
        int min = 0;
        int max = 999999;
        Random r = new Random();
        return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
    }
}
