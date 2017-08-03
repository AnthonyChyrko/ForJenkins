package STTA.MavenProject;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SimpleTest {
	String base_url = "http://svyatoslav.biz";
	StringBuffer verificationErrors = new StringBuffer();
//	File path_to_profile = new File("c:/Users/Anton_Chyrko/ff_profile/");	
//	FirefoxProfile profile = new FirefoxProfile();
	WebDriver driver = null;

	@BeforeClass
	public void beforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "c:/Users/Anton_Chyrko/Downloads/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterClass
	public void afterClass() {
		driver.close();
		driver = null;
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

	@Test
	public void tstWt() {
		// 1 (action) Открыть http://svyatoslav.biz/testlab/wt
		driver.get(base_url + "/testlab/wt/");
		// 1 (check) Страница содержит форму с полями «Имя», «Рост», «Вес»,
		// радиокнопкой «Пол» и кнопкой отправки данных «Рассчитать».
		Collection<WebElement> forms = driver.findElements(By.tagName("form"));
		Assert.assertFalse(forms.isEmpty(), "No forms found!");
		Iterator<WebElement> i = forms.iterator();
		boolean form_found = false;
		WebElement form = null;
		while (i.hasNext()) {
			form = i.next();
			if ((form.findElement(By.name("name")).getAttribute("type").equalsIgnoreCase("text"))
					&& (form.findElement(By.name("height")).getAttribute("type").equalsIgnoreCase("text"))
					&& (form.findElement(By.name("weight")).getAttribute("type").equalsIgnoreCase("text"))
					&& (form.findElement(By.xpath("//input[@type=\"submit\"]")).getAttribute("value")
							.equalsIgnoreCase("Рассчитать"))
					&& (form.findElements(By.name("gender")).size() == 2)) {
				form_found = true;
				break;
			}
		}
		Assert.assertTrue(form_found, "No suitable forms found!");
		// 2 (action) В поле «Имя» ввести «username».
		form.findElement(By.name("name")).clear();
		form.findElement(By.name("name")).sendKeys("username");
		// 2 (check) Значение появляется в поле.
		Assert.assertEquals(form.findElement(By.name("name")).getAttribute("value"), "username",
				"Unable to fill 'Имя' filed");
		// 3 (action) В поле «Рост» ввести «50».
		form.findElement(By.name("height")).clear();
		form.findElement(By.name("height")).sendKeys("50");
		// 3 (check) Значение появляется в поле.
		Assert.assertEquals(form.findElement(By.name("height")).getAttribute("value"), "50",
				"Unable to fill 'Рост' filed");
		// 4 (action) В поле «Вес» ввести «3».
		form.findElement(By.name("weight")).clear();
		form.findElement(By.name("weight")).sendKeys("3");
		// 4 (check) Значение появляется в поле.
		Assert.assertEquals(form.findElement(By.name("weight")).getAttribute("value"), "3",
				"Unable to fill 'Вес' filed");
		// 5 (action) В радиокнопке «Пол» выбрать пол «М».
		form.findElement(By.xpath("//input[@name='gender' and @value='m']")).click();
		// 5 (check) Вариант «М» выбран.
		Assert.assertTrue(form.findElement(By.xpath("//input[@name='gender' and @value='m']")).isSelected(),
				"Unable select 'М' gender");
		// 6 (action) Нажать «Рассчитать».
		form.findElement(By.xpath("//input[@type=\"submit\"]")).click();
		// 6 (check) Форма исчезает, в центральной ячейке таблицы появляется
		// надпись «Слишком мала масса тела».
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		forms = driver.findElements(By.tagName("form"));
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		i = forms.iterator();
		form_found = false;
		while (i.hasNext()) {
			form = i.next();
			if ((form.findElement(By.name("name")).getAttribute("type").equalsIgnoreCase("text"))
					&& (form.findElement(By.name("height")).getAttribute("type").equalsIgnoreCase("text"))
					&& (form.findElement(By.name("weight")).getAttribute("type").equalsIgnoreCase("text"))
					&& (form.findElement(By.xpath("//input[@type=\"submit\"]")).getAttribute("value")
							.equalsIgnoreCase("Рассчитать"))
					&& (form.findElements(By.name("gender")).size() == 2)) {
				form_found = true;
				break;
			}
		}
		Assert.assertFalse(form_found, "Form is on the page!");
		Assert.assertEquals(driver.findElement(By.xpath("//table/tbody/tr[2]/td[2]")).getText(),
				"Слишком малая масса тела",
				"Message 'Слишком малая масса тела' either is absent or is not in a proper place");

	}
}
