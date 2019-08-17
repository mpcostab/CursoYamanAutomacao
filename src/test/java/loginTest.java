import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import javax.print.DocFlavor;
import java.util.Locale;

public class loginTest {
    public WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        return new ChromeDriver();
    }

    public WebDriver driver = getDriver();
    public WebDriverWait wait = new WebDriverWait(driver, 15);
    Faker faker = new Faker(new Locale("pt-BR"));

    @Before
    public void inicio() {

        driver.manage().window().maximize();
        //driver.manage().window().fullscreen();
        driver.get("https://login.webmotors.com.br");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loader")));


    }


    //WebDriver driver = new ChromeDriver();

    @Test

    public void emailIncorreto() throws InterruptedException {

        //WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("email")).sendKeys("cursoyaman10@yaman.com.br");
        driver.findElement(By.id("senha")).sendKeys("yaman12345");
        //caso sofra com o selenium para encontrar o elemento
        //WebElement element = driver.findElement(By.id("btnEntrar"));
        //Actions actions = new Actions(driver);
        //actions.moveToElement(element).click().perform();
        driver.findElement(By.id("btnEntrar")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='form__field__error show']")));
        String emailIncoreto = driver.findElement(By.xpath("//span[@class='form__field__error show']")).getText();
        Assert.assertEquals("E-mail não cadastrado.", emailIncoreto);

    }

    @Test
    public void cadastrar() throws InterruptedException {

        int numero = faker.number().numberBetween(1, 1000);
        String inteiro = Integer.toString(numero);
        String email = "cursoyaman" + numero + "@yaman.com.br";
        //faker.number().numberBetween(1, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("btnNovaConta")));
        WebElement element = driver.findElement(By.id("btnNovaConta"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("btnNovaConta")));
        actions.click(element).perform();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("nome")));
        driver.findElement(By.id("nome")).sendKeys("Aluno Yaman");
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("senha")).sendKeys("12345");
        driver.findElement(By.id("btnEntrarNovaConta")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='header__user-data']")));
        String nomeCadastrado = driver.findElement(By.xpath("//div[@class='header__user-data']")).getText();
        Assert.assertTrue(nomeCadastrado.contains(email));

    }

    @After
    public void fim() {
        driver.quit();
    }
}

