import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Test_1 {
	public static WebDriver driver;

	public static String browser = "Edge";
	public static String gender = "other";
	public static String tel_number = "03331821234";
	public static String name = "Schmidt";
	public static String prename = "Bob";
	public static String email = "test123@web.de";
	public static String monthofBirth = "12";
	public static String yearofBirth = "2000";
	public static String day = "15";
	public static String subject = "Maths";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		if (browser.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("Edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			System.out.println("Driver Error");
		}

		driver.get("https://demoqa.com/automation-practice-form");
		driver.findElement(By.id("firstName")).sendKeys(prename);
		driver.findElement(By.id("lastName")).sendKeys(name);
		driver.findElement(By.xpath("//*[@id=\"userEmail\"]")).sendKeys(email);

		if (gender.equalsIgnoreCase("male")) {
			driver.findElement(By.xpath("//*[@id=\"genterWrapper\"]/div[2]/div[1]/label")).click();
		} else if (gender.equalsIgnoreCase("female")) {
			driver.findElement(By.xpath("//*[@id=\"genterWrapper\"]/div[2]/div[2]/label")).click();
		} else if (gender.equalsIgnoreCase("other")) {
			driver.findElement(By.xpath("//*[@id=\"genterWrapper\"]/div[2]/div[3]/label")).click();
		} else {
			System.out.println("Gender Error");
		}

		if (tel_number.length() < 10) {
			System.out.println("Number must at least contain 10 digits");
			driver.close();
		} else {
			driver.findElement(By.xpath("//*[@id=\"userNumber\"]")).sendKeys(tel_number);
		}

		driver.findElement(By.id("dateOfBirthInput")).click();

		// Monat des Geburtstages
		WebElement monthElement = driver.findElement(By.className("react-datepicker__month-select"));
		monthElement.click();

		// Number format fuer Monat
		try {
			int month_Converted = Integer.parseInt(monthofBirth);
			month_Converted = month_Converted - 1;
			new Select(monthElement).selectByIndex(month_Converted);
		} catch (NumberFormatException ex) {
			// String format fuer Monat
			new Select(monthElement).selectByVisibleText(monthofBirth);
		}

		// Jahr des Geburtstages
		WebElement yearElement = driver.findElement(By.className("react-datepicker__year-select"));
		yearElement.click();
		new Select(yearElement).selectByVisibleText(yearofBirth);
		yearElement.click();

		// Tag
		driver.findElement(By.className("react-datepicker__day--0" + day)).click();

		// Autofill Container for Subjects
		WebElement e = driver.findElement(By.id("subjectsInput"));
		e.sendKeys(subject);
		e.sendKeys(Keys.ARROW_DOWN);
		e.sendKeys(Keys.ENTER);

	}

}