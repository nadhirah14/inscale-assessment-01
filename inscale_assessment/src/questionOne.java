import java.io.FileInputStream;
import java.time.Duration;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.paulhammant.ngwebdriver.ByAngular;


public class questionOne {
    
    public static void main(String[] args) throws Exception {
        
        WebDriver driver = new ChromeDriver();
        //get location of chrome driver
        String driverPath = "./inscale_assessment/inscale_assessment/lib/";
        System.getProperty("webdriver.chrome.driver", driverPath);

        //load web in browser
        String baseUrl = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
        driver.get(baseUrl);
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    
        //login as bank manager
    	driver.findElement(By.xpath("//button[text()='Bank Manager Login']")).click();
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        
        //add customer
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[1]")).click();

        Thread.sleep(5000);
        
        //verify form displayed
        By elementLocator02 = ByAngular.model("fName");
		List<WebElement> elements02 = driver.findElements(elementLocator02);
		if (elements02.size() > 0) {
		    System.out.println("add customer form displayed");
            Thread.sleep(5000);
		} else {
		    System.out.println("add customer form not displayed");
		}

        //input firstname, lastname, postcode
        String[] firstname = {"Christopher", "Frank", "Christopher", "Connely", "Jackson", "Minka", "Jackson"};
        String[] lastname = {"Connely", "Christopher", "Minka", "Jackson", "Frank", "Jackson", "Connely"};
        String[] postcode = {"L789C349", "A897N450", "M098Q585", "L789C349", "L789C349", "A897N450", "L789C349"};


        for (int i = 0;i<firstname.length;i++){
            driver.findElement(ByAngular.model("fName")).sendKeys(firstname[i]);
            driver.findElement(ByAngular.model("lName")).sendKeys(lastname[i]);
            driver.findElement(ByAngular.model("postCd")).sendKeys(postcode[i]);
            driver.findElement(By.xpath("html/body/div/div/div[2]/div/div[2]/div/div/form/button")).click();
            
            Thread.sleep(10000);
            
            driver.switchTo( ).alert( ).accept();
            
            Thread.sleep(10000);
        }

        //go to customers tab
        driver.findElement(ByAngular.buttonText("Customers")).click();
        Thread.sleep(10000);
        
        //verify all data created
        String filepath = "./inscale_assessment/inscale_assessment/lib/q1_data.xlsx";
        FileInputStream myfile = new FileInputStream(filepath);
        Workbook workbook = new XSSFWorkbook(myfile);
        Sheet sheet = workbook.getSheetAt(0);

        int rowcount = sheet.getLastRowNum();
        int colCount = sheet.getRow(0).getLastCellNum();

        for(int i=0;i<rowcount;i++){
            Row row = sheet.getRow(1);

            String[] actColumn = new String[colCount];
        
            for (int j=0;j<colCount;j++){
                actColumn[j] = row.getCell(j).toString();
            }

        WebElement table = driver.findElement(By.xpath("//table[(@class='table table-bordered table-striped')]"));
        List<WebElement> rowsList = table.findElements(By.xpath("//table[@class='table table-bordered table-striped']/tbody/tr"));

    
            //iterate through each row
            for (int rownum = 0; rownum<rowsList.size();rownum++){
                WebElement rows = rowsList.get(rownum);

                //get each cell from current row
                List<WebElement> columnsList = rows.findElements(By.xpath("//table[@class='table table-bordered table-striped']/tbody/tr/td"));

                //iterate through each column
                for (int colnum = 0; colnum<columnsList.size();colnum++){
                    WebElement column = columnsList.get(colnum);

                    String actualValue = column.getText();
                    String expValue = actColumn[colnum];

                    if (actualValue.equals(expValue)){
                        System.out.println("True - actual : "+actualValue+" | expected : "+expValue);
                    }else{
                        System.out.println("False - actual : "+actualValue+" | expected : "+expValue);
                    }

                }

            }
    
        workbook.close();
        myfile.close();

        }

        //delete customer  Frank
        WebElement search = driver.findElement(ByAngular.model("searchCustomer"));
        search.sendKeys("Jackson");
            
        Thread.sleep(10000);

        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/table/tbody/tr[2]/td[5]/button")).click();
        
        Thread.sleep(10000);       
        
        search.clear();

         //delete customer Christopher Connely
        search.sendKeys("christopher");

        Thread.sleep(10000);
        
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/table/tbody/tr[1]/td[5]/button")).click();

        Thread.sleep(10000);
            

        //close browser
        //driver.quit();



    }
}
