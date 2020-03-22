package com.mobile.assignment.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.apache.log4j.Logger.getLogger;

public class CalendarScreen extends Screen{

    private static Logger logger = getLogger(CalendarScreen.class.getName());

    @AndroidFindBy(xpath = "//android.view.View[@text='15']")
    public WebElement currentYearMonth;

    @AndroidFindBy(id = "android:id/next")
    public WebElement tapNext;

    @AndroidFindBy(id = "android:id/prev")
    public WebElement tapPrevious;

    @AndroidFindBy(id = "android:id/date_picker_header_year")
    public WebElement datePickerHeader;

    @AndroidFindBy(id = "android:id/date_picker_header_date")
    public WebElement datePickerDate;

    String tapDate = "//android.view.View[@text='day']";

    public CalendarScreen(AppiumDriver<AndroidElement> appiumDriver) {
        super(appiumDriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumDriver), this);
    }

    public WebElement getCurrentYearMonth() {
        return currentYearMonth;
    }

    public WebElement getTapNext() {
        return tapNext;
    }

    public WebElement getTapPrevious() {
        return tapPrevious;
    }

    public WebElement getDatePickerHeader() {
        return datePickerHeader;
    }

    public WebElement getDatePickerDate() {
        return datePickerDate;
    }

    public int getMonthNumber(String monthName) throws ParseException {
        logger.info("Get Month Number");
        Date date = new SimpleDateFormat("MMMM").parse(monthName);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public String getYear(String date){
        return date.split(" ")[2];
    }

    public String expectedDate(String dateToSelect) {
        String monthName = dateToSelect.split(" ")[1];
        String upToNCharacters = monthName.substring(0, Math.min(monthName.length(), 3));
        return upToNCharacters+" "+dateToSelect.split(" ")[0];
    }

    public int[] getTaps(int givenYear, int displayedYear, int givenMonth, int displayedMonth){
        logger.info("Get TapNext and TapPrev Count");
        int frontTaps=0, backTaps=0, yearCal = 0;
        if (givenYear == displayedYear) {
            if (givenMonth >= displayedMonth) {
                frontTaps = givenMonth - displayedMonth;
            } else backTaps = displayedMonth - givenMonth;
        } else if (givenYear > displayedYear) {
            yearCal = (givenYear - displayedYear) * 12;
            if (givenMonth >= displayedMonth) {
                frontTaps = yearCal + (givenMonth - displayedMonth);
            } else {
                frontTaps = yearCal - (displayedMonth - givenMonth);
            }
        } else {
            yearCal = (displayedYear - givenYear) * 12;
            if (givenMonth >= displayedMonth) {
                backTaps = yearCal - (givenMonth - displayedMonth);
            } else {
                backTaps = yearCal + (displayedMonth - givenMonth);
            }
        }
        return new int[]{frontTaps, backTaps};
    }


    public void selectDate(String dateToSelect) throws ParseException {
        logger.info("Start Select Date Logic");
        int givenYear = Integer.parseInt(dateToSelect.split(" ")[2]);
        int givenMonth = getMonthNumber(dateToSelect.split(" ")[1]);
        int givenDate = Integer.parseInt(dateToSelect.split(" ")[0]);
        int displayedYear = Integer.parseInt(getCurrentYearMonth().getAttribute("content-desc").split(" ")[2]);
        int displayedMonth = getMonthNumber(getCurrentYearMonth().getAttribute("content-desc").split(" ")[1]);
        int[] taps = getTaps(givenYear,displayedYear, givenMonth, displayedMonth);
        carryTaps("tapNext", taps[0]);
        carryTaps("tapPrev", taps[1]);
        appiumDriver.findElement(By.xpath(tapDate.replace("day", String.valueOf(givenDate)))).click();
    }

    public void carryTaps(String action, int tapCount){
        logger.info("Carry Taps for "+action);
        for (int i = 1; i <= tapCount; i++) {
            if (action.equals("tapNext")) { getTapNext().click(); }
            else if (action.equals("tapPrev")) { getTapPrevious().click(); }
        }
    }
}