package STTA.MavenProject;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SampleTest_with_PageObjectSample {
	String base_url = "http://svyatoslav.biz";
	StringBuffer verificationErrors = new StringBuffer();
//	File path_to_profile = new File("d:/_work/ff_profile/");
//	FirefoxProfile profile = new FirefoxProfile(path_to_profile);
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
	public void sampleTest() {
		// 1 (action) Открыть http://svyatoslav.biz/testlab/wt
		driver.get(base_url + "/testlab/wt/");
		// С этого момента можно использовать PajeObject.
		PageObjectSample page = new PageObjectSample(driver);
		// 1 (check) Страница содержит форму с полями «Имя», «Рост», «Вес»,
		// радиокнопкой «Пол» и кнопкой отправки данных «Рассчитать».
		Assert.assertTrue(page.isFormPresentForReal(), "No suitable forms found!");
		// 2 (action) В поле «Имя» ввести «username».
		page.setName("username");
		// 2 (check) Значение появляется в поле.
		Assert.assertEquals(page.getName(), "username", "Unable to fill 'Имя' field");
		// 3 (action) В поле «Рост» ввести «50».
		page.setHeight("50");
		// 3 (check) Значение появляется в поле.
		Assert.assertEquals(page.getHeight(), "50", "Unable to fill 'Рост' field");
		// 4 (action) В поле «Вес» ввести «3».
		page.setWeight("3");
		// 4 (check) Значение появляется в поле.
		Assert.assertEquals(page.getWeight(), "3", "Unable to fill 'Вес' field");
		// 5 (action) В радиокнопке «Пол» выбрать пол «М».
		page.setGender("m");
		// 5 (check) Вариант «М» выбран.
		Assert.assertEquals(page.getGender(), "m", "Unable select 'М' gender");
		// 6 (action) Нажать «Рассчитать».
		page.submitForm();
		// 6 (check) Форма исчезает, в центральной ячейке таблицы появляется
		// надпись «Слишком мала масса тела».
		Assert.assertFalse(page.isFormPresentForReal(), "Form is on the page!");
		Assert.assertTrue(page.userMessageEquals("Слишком малая масса тела"),
				"Mes-sage 'Слишком малая масса тела' either is absent or is not in a proper place");
	}

}