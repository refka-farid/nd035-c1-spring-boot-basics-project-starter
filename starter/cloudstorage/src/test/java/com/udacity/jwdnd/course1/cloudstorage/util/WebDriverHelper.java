package com.udacity.jwdnd.course1.cloudstorage.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverHelper {
    public static void wait_10s(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(webDriver -> {
                    try {
                        Thread.sleep(10_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
        );
    }
}
