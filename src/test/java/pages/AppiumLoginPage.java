package pages;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

public class AppiumLoginPage {
	AndroidDriver driver;
	WebDriverWait wait;

	public AppiumLoginPage(AndroidDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//android.widget.ImageView[@content-desc='Select English']")
	private WebElement click_english_btn;

	@FindBy(id = "in.amazon.mShop.android.shopping:id/continue_button")
	private WebElement continue_english_btn;

	@FindBy(id = "in.amazon.mShop.android.shopping:id/skip_sign_in_button")
	private WebElement skip_signin_btn;

	public void click_english() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		wait.until(ExpectedConditions.visibilityOf(click_english_btn));
		click_english_btn.click();
	}

	public void click_continue_english() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		wait.until(ExpectedConditions.visibilityOf(continue_english_btn));
		continue_english_btn.click();
	}

	public AppiumSearchPage click_skip_signin() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		try {
			skip_signin_btn.click();
		} catch (NoSuchElementException ex) {
			System.out.println("Skip Login does not exist");
		} finally {
			return new AppiumSearchPage(driver);
		}
	}

	public void screenshot(String filename) throws IOException {
		File srcFile = driver.getScreenshotAs(OutputType.FILE);
		String screenshotpath = (System.getProperty("user.dir") + "//Screenshots/");
		File targetFile = new File(screenshotpath + filename + ".jpg");
		FileUtils.copyFile(srcFile, targetFile);
	}
}
