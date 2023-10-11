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

public class AppiumSearchPage {
	AndroidDriver driver;
	WebDriverWait wait;

	public AppiumSearchPage(AndroidDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "in.amazon.mShop.android.shopping:id/chrome_search_hint_view")
	private WebElement search_bar_enable;

	@FindBy(id = "in.amazon.mShop.android.shopping:id/rs_search_src_text")
	private WebElement search_bar;

	public void click_to_enable_search() {
		wait.until(ExpectedConditions.visibilityOf(search_bar_enable));
		search_bar_enable.click();
	}

	public void click_to_search(String item) {
		wait.until(ExpectedConditions.visibilityOf(search_bar));
		search_bar.sendKeys(item);
		List<WebElement> item_list = driver.findElements(
				By.xpath("//android.widget.Button[@index=1 and @text='"+item+"' and not(@content-desc='Clear search keywords')]"));

		for (WebElement sp_item : item_list) {
			if (sp_item.isDisplayed()) {
				sp_item.click();
				break;
			} else {
				scrollAction();
			}
		}
	}

	public void scrollAction() {
		int ht = driver.manage().window().getSize().getHeight();
		int wdth = driver.manage().window().getSize().getWidth();

		int startX = (int) (0.5 * wdth);
		int startY = (int) (0.8 * ht);

		int endX = startX;
		int endY = (int) (0.2 * ht);

		driver.hideKeyboard();
		TouchAction action = new TouchAction(driver);
		action.longPress(PointOption.point(startX, startY)).moveTo(PointOption.point(endX, endY)).release().perform();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	}

	public AppiumProductPage click_search_list_specific_item(String product_name) {
		boolean product_found = false;
		while (!product_found) {
			List<WebElement> item_list = driver.findElements(By.xpath("//android.view.View[@text]"));
			for (WebElement item : item_list) {
				if (item.getAttribute("text").equals(product_name)) {
					try {
						this.screenshot("ChooseProduct");
					} catch (IOException e) {
						e.printStackTrace();
					}
					item.click();
					product_found = true;
					break;
				}
			}
			scrollAction();
		}
		return new AppiumProductPage(driver);
	}

	public void screenshot(String filename) throws IOException {
		File srcFile = driver.getScreenshotAs(OutputType.FILE);
		String screenshotpath = (System.getProperty("user.dir") + "//Screenshots/");
		File targetFile = new File(screenshotpath + filename + ".jpg");
		FileUtils.copyFile(srcFile, targetFile);
	}
}
