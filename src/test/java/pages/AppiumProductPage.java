package pages;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;

public class AppiumProductPage {
	AndroidDriver driver;
	WebDriverWait wait;

	public AppiumProductPage(AndroidDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//android.widget.TextView[@text='Added to cart']")
	WebElement added_to_cart_text;

	public boolean confirm_product_added() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		wait.until(ExpectedConditions.visibilityOf(added_to_cart_text));
		try {
			this.screenshot("SucessfullyAddedToCart");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return added_to_cart_text.isDisplayed();
	}

	public void scrollAction() {
		int ht = driver.manage().window().getSize().getHeight();
		int wdth = driver.manage().window().getSize().getWidth();

		int startX = (int) (0.5 * wdth);
		int startY = (int) (0.65 * ht);

		int endX = startX;
		int endY = (int) (0.35 * ht);

		driver.hideKeyboard();
		TouchAction action = new TouchAction(driver);

		action.longPress(PointOption.point(startX, startY)).moveTo(PointOption.point(endX, endY)).release().perform();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	}

	public void add_to_cart() {
		boolean product_found = false;
		while (!product_found) {
			List<WebElement> item_list = driver.findElements(By.xpath("//android.widget.Button[@text='Add to Cart']"));
			if (item_list.size() > 0) {
				for (WebElement item : item_list) {
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
					if (item.isDisplayed()) {
						item.click();
						product_found = true;
						break;
					}
					scrollAction();
				}

			} else {
				scrollAction();
			}
		}

	}

	public void screenshot(String filename) throws IOException {
		File srcFile = driver.getScreenshotAs(OutputType.FILE);
		String screenshotpath = (System.getProperty("user.dir") + "//Screenshots/");
		File targetFile = new File(screenshotpath + filename + ".jpg");
		FileUtils.copyFile(srcFile, targetFile);
	}
}
