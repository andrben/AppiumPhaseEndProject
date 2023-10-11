package stepdefs;

import java.io.IOException;

import org.junit.Assert;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AppiumLoginPage;
import pages.AppiumProductPage;
import pages.AppiumSearchPage;

public class Amazon {

	AndroidDriver driver = HooksAppium.driver;

	AppiumLoginPage login_pg;
	AppiumSearchPage search_pg;
	AppiumProductPage product_pg;

	@Given("User is logged into Amazon app")
	public void user_is_logged_into_amazon_app() {
		login_pg = new AppiumLoginPage(driver);
		login_pg.click_english();
		login_pg.click_continue_english();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		search_pg = login_pg.click_skip_signin();
	}

	@When("User searches for and selects {string}")
	public void user_searches_for(String product) {
		search_pg.click_to_enable_search();
		search_pg.click_to_search(product);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			search_pg.screenshot("SearchProduct");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@And("User chooses the product named {string}")
	public void chooses_the_product_named(String product_name) {
		product_pg = search_pg.click_search_list_specific_item(product_name);
	}

	@And("User adds it to cart")
	public void adds_it_to_cart() {
		product_pg.add_to_cart();
		try {
			product_pg.screenshot("AddProductToCart");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Then("The product gets added to cart")
	public void the_product_gets_added_to_cart() {
		Assert.assertTrue(product_pg.confirm_product_added());
	}

}
