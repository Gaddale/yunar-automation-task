package assignment;

import com.mobile.assignment.Configuration;
import com.mobile.assignment.screens.CalendarScreen;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;

import static org.apache.log4j.Logger.getLogger;

public class CalendarTest extends TestBase {
    private static Logger logger = getLogger(CalendarTest.class.getName());

    @Test
    public void launch() throws ParseException {
        CalendarScreen calendarScreen = new CalendarScreen(appiumDriver);
        String dateToSelect = Configuration.DATE_TO_SELECT.getValue();
        calendarScreen.selectDate(dateToSelect);
        logger.info(dateToSelect+ " Date is selected");
        Assert.assertEquals(calendarScreen.getDatePickerHeader().getText(), calendarScreen.getYear(dateToSelect));
        logger.info(calendarScreen.getYear(dateToSelect)+ " Year displayed on the Date Picker Header");
        Assert.assertTrue(calendarScreen.getDatePickerDate().getText().contains(calendarScreen.expectedDate(dateToSelect)));
        logger.info(calendarScreen.expectedDate(dateToSelect) + " Date displayed on the Date Picker Header");
    }
}
