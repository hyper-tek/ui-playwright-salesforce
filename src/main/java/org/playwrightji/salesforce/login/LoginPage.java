package org.playwrightji.salesforce.login;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {
    //locators
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String LOGIN_BTN = "Log In";
    private static final String FORGOT_BTN = "Forgot Your Password?";
    private static final String TRY_FOR_FREE_BTN = "Try for Free";
    private Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public LoginPage(Page page, String username, String password) {
        this.page = page;
        setUsername(username);
        setPassword(password);
        clickLogin();
    }

    public void setUsername(String username) {
        page.getByLabel(USERNAME).fill(username);;
    }
    public void setPassword(String password) {
        page.getByLabel(PASSWORD).fill(password);
    }
    public void clickLogin() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(LOGIN_BTN)).click();
    }

    public void clickForgotPassword() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(FORGOT_BTN)).click();
    }

    public void clickTryForFree() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(TRY_FOR_FREE_BTN)).click();
    }

}
