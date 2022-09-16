import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
	public static String monthofBirth = "9";
	public static String yearofBirth = "1944";
	public static String day = "12";
	public static String subject = "Maths";
	public static String hobbies = "Sports,Reading";
	public static String adress = "An der Schulstraße 9a";

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		// Browserauswahl
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

		// Fenster maximieren damit Selenium Zugriff auf alle Webelemente bekommt
		driver.manage().window().maximize();
		// Seite aufrufen
		driver.get("https://demoqa.com/automation-practice-form");
		// Name und Vorname eingeben
		driver.findElement(By.id("firstName")).sendKeys(prename);
		driver.findElement(By.id("lastName")).sendKeys(name);
		// EMail eingeben
		driver.findElement(By.xpath("//*[@id=\"userEmail\"]")).sendKeys(email);

		// Geschlechts Eingabe checken
		if (gender.equalsIgnoreCase("male")) {
			driver.findElement(By.xpath("//*[@id=\"genterWrapper\"]/div[2]/div[1]/label")).click();
		} else if (gender.equalsIgnoreCase("female")) {
			driver.findElement(By.xpath("//*[@id=\"genterWrapper\"]/div[2]/div[2]/label")).click();
		} else if (gender.equalsIgnoreCase("other")) {
			driver.findElement(By.xpath("//*[@id=\"genterWrapper\"]/div[2]/div[3]/label")).click();
		} else {
			System.out.println("Gender Error");
		}

		// Telefonnummer eingeben -> Error bei falscher Telefonnummer
		if (tel_number.length() < 10) {
			System.out.println("Number must at least contain 10 digits");
			driver.close();
		} else {
			driver.findElement(By.xpath("//*[@id=\"userNumber\"]")).sendKeys(tel_number);
		}
		// Scroll Down -> Werbung + Scrollbar ganz oben verhindert, dass Selenium auf
		// alle Webseitenelemente zugreifen kann
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,250)");

		// Birthday Box anklicken
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

		// kurz warten die Seite spinnt sonst gerne mal bei der Kalenderbox
		Thread.sleep(500);

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
		Thread.sleep(500);
		e.sendKeys(Keys.ARROW_DOWN);
		e.sendKeys(Keys.ENTER);

		// Checkboxen
		if (hobbies.contains("Sports")) {
			driver.findElement(By.xpath("//*[@id=\"hobbiesWrapper\"]/div[2]/div[1]/label")).click();
		}
		if (hobbies.contains("Reading")) {
			driver.findElement(By.xpath("//*[@id=\"hobbiesWrapper\"]/div[2]/div[2]/label")).click();
		}
		if (hobbies.contains("Music")) {
			driver.findElement(By.xpath("//*[@id=\"hobbiesWrapper\"]/div[2]/div[3]/label")).click();
		}

		// Upload Picture

		WebElement upload = driver.findElement(By.id("uploadPicture"));
		// eignener Pfad muss angepasst werden
		upload.sendKeys("C:\\Users\\fabia\\git\\InterviewSelenium\\SeleniumInterview\\src\\main\\resources\\am.jpg");

		// Adress
		driver.findElement(By.id("currentAddress")).sendKeys(adress);

		// Untersten DropDowns
		WebElement dropDownLeft = driver.findElement(By.xpath("//input[@id='react-select-3-input']"));
		dropDownLeft.sendKeys("Uttar Pradesh");
		Thread.sleep(500);
		dropDownLeft.sendKeys(Keys.ARROW_DOWN);
		dropDownLeft.sendKeys(Keys.ENTER);

		WebElement dropDownRight = driver.findElement(By.xpath("//*[@id=\"react-select-4-input\"]"));
		Thread.sleep(500);
		dropDownRight.sendKeys("Luck");
		dropDownRight.sendKeys(Keys.ARROW_DOWN);
		dropDownRight.sendKeys(Keys.ENTER);

		// Enter als Submit -> da Submit button nicht zu sehen ist
		dropDownRight.sendKeys(Keys.RETURN);
	}

}
