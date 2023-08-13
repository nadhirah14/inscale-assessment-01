import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement;
import com.paulhammant.ngwebdriver.ByAngular;
import org.openqa.selenium.By;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.time.Duration;

public class questionTwo {
    

    public static void main(String[] args) throws Exception {

        
        WebDriver driver = new ChromeDriver();
        //get location of chrome driver
        String driverPath = "./inscale_assessment/inscale_assessment/lib/";
        System.getProperty("webdriver.chrome.driver", driverPath);

        //load web in browser
        String baseUrl = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
        driver.get(baseUrl);
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        //login as customer
        driver.findElement(By.xpath("//button[text()='Customer Login']")).click();
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        //select as Hermione and login
        new Select (driver.findElement(By.id("userSelect"))).selectByValue("1");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.findElement(By.xpath("//button[text()='Login']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        //select account 1003
        new Select (driver.findElement(By.id("accountSelect"))).selectByValue("number:1003");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        //get xlsx file
        String filepath = "./inscale_assessment/inscale_assessment/lib/q2_data.xlsx";
        
        FileInputStream myfile = new FileInputStream(filepath);
        Workbook workbook = new XSSFWorkbook(myfile);
        Sheet sheet = workbook.getSheetAt(0);
        
        for (Row row : sheet) {
            String type_exl = row.getCell(0).toString();
            String amount_exl = row.getCell(1).toString();
            String expected_balance_exl  = row.getCell(2).toString();

            
            //check transaction type and perform deposit/withdrawal
            if (type_exl.equalsIgnoreCase("credit")) {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
                System.out.println("c");
                performDeposit(driver, amount_exl, expected_balance_exl);
            } else if (type_exl.equalsIgnoreCase("debit")) {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
                System.out.println("d");
                performWithdraw(driver, amount_exl, expected_balance_exl);
            }
   
        }
    
        workbook.close();
        myfile.close();

    }

        //perform Deposit transaction from if else checking
        public static void performDeposit(WebDriver driver, String amount_exl, String expected_balance_exl){
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            
            //click Deposit button
            driver.findElement(ByAngular.buttonText("Deposit")).click();
           
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            
            //enter deposit amount
            WebElement deposit_amount = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input"));
            deposit_amount.sendKeys(String.valueOf(amount_exl));
            System.out.println("input deposit : "+amount_exl);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            
            //click deposit button
            driver.findElement(By.xpath("//button[text()='Deposit']")).click();
            
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

            //get current balance from ui
            WebElement acBalanceElement = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]"));
            String actualBalance = acBalanceElement.getText();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

            //check if expected balance from excel matches actual balance in ui
            if (expected_balance_exl == actualBalance){
                System.out.println("true - withdraw expected : "+expected_balance_exl+"  | actual : "+actualBalance);
            }else {
                System.out.println("false - withdraw expected : "+expected_balance_exl+"  | actual : "+actualBalance); 
            }
        }

        //perform WITHDRAW transaction from if else checking
        public static void performWithdraw(WebDriver driver, String amount_exl, String expected_balance_exl){
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            
            //click Withdrawl button
            driver.findElement(ByAngular.buttonText("Withdrawl")).click();
    
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            
            //enter withdrawal amount
            WebElement wdr_amount = driver.findElement(ByAngular.model("amount"));
            wdr_amount.sendKeys(String.valueOf(amount_exl));
            System.out.println("input withdraw : "+amount_exl);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
           
            //click withdraw button
            driver.findElement(By.xpath("//button[text()='Withdraw']")).click();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

            //get current balance from ui
            WebElement acBalanceElement = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]"));
            String actualBalance = acBalanceElement.getText();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
           
            //check if expected balance from excel matches actual balance in ui
            if (expected_balance_exl == actualBalance){
                System.out.println("true - withdraw expected : "+expected_balance_exl+"  | actual : "+actualBalance);
            }else {
                System.out.println("false - withdraw expected : "+expected_balance_exl+"  | actual : "+actualBalance); 
            }

    //close browser
    //driver.quit();

               
    }
}


