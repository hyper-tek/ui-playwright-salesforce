package tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.playwrightji.configuration.Configuration;
import org.playwrightji.salesforce.login.LoginPage;
import org.playwrightji.salesforce.login.Register;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestSuite_01 extends TestSetup {
    @BeforeClass
    public void setupBeforeClass() {
        extentTest = reporter.createTest("TS_01 Launch Salesforce Login", "Verify login functionality");
    }

    @Test
    public void loginTest() {
        testNode = extentTest.createNode("TC_01 Verify Login Screen with Valid Credentials");
        testNode.assignCategory("TC_01-Open-Salesforce-Login");
        loginPage = new LoginPage(page, configurationFactory.getConfiguration().getUsername(),
                configurationFactory.getConfiguration().getPassword());
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("View profile")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Log Out")).click();
    }

    @Test
    public void registerTest(){
        testNode = extentTest.createNode("TC_02 Verify Register Screen loads with correct form fields");
        testNode.assignCategory("TC_02-Register-Salesforce-Trial");
        loginPage = new LoginPage(page);
        loginPage.clickTryForFree();
        Register register = new Register(page,"Joseph", "Ibrahimovic", "Automation", "joseph.ibrahimovic@outlook.com","0400100200","Deloitte",
                Register.Country.AFGHANISTAN, Register.CompanySize.S, Register.CompanyLanguage.DUTCH);

    }

    @Test
    public void accountTest() throws InterruptedException {
        testNode = extentTest.createNode("TC_03 Verify account can be created");
        testNode.assignCategory("TC_03-Account-creation");
        loginPage = new LoginPage(page, configurationFactory.getConfiguration().getUsername(),
                configurationFactory.getConfiguration().getPassword());
        page.locator("lightning-formatted-text").filter(new Locator.FilterOptions().setHasText("$69,000")).click();
        page.locator("lightning-formatted-text").filter(new Locator.FilterOptions().setHasText("$184,500")).click();
        page.locator("lightning-formatted-text").filter(new Locator.FilterOptions().setHasText("$250,000")).click();
        page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Manage")).click();
        page.locator("runtime_mobilesapp-launch-pad-tile").filter(new Locator.FilterOptions().setHasText("Products")).locator("img").click();
        Thread.sleep(1000);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("New")).click();
        page.getByLabel("Product Name*").click();
        page.getByLabel("Product Name*").fill("product name 02");
        page.getByLabel("Product Name*").press("Tab");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("--None--")).click();
        page.getByRole(AriaRole.MENUITEMRADIO, new Page.GetByRoleOptions().setName("None").setExact(true)).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Product Code")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Product Code")).fill("pc02");
        page.getByLabel("Active").check();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Product Description")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Product Description")).fill("product pc02");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save").setExact(true)).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Cancel")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("View profile")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Log Out")).click();
    }
}
