package com.mobile.assignment.screens;

import org.openqa.selenium.WebDriver;

public class Screen {

    protected WebDriver appiumDriver;

    public Screen(WebDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public WebDriver getWebDriver() {
        return appiumDriver;
    }
}
