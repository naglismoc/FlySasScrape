package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Main m = new Main();
        LocalDate start = LocalDate.of(2019, 11, 4);
        LocalDate end = LocalDate.of(2019, 11, 10);
        FirefoxDriver driver = new FirefoxDriver();
        String allowedChange = "Oslo";


        WebDriverWait wait = new WebDriverWait(driver, 10);
        String flyFrom = "OSL";
        String flyTo = "RIX";

        m.enterWebsite(driver, flyFrom, flyTo, start);

        //atidarom kalendoriu
        String dateFromXPath = "/html/body/div[1]/form/div[4]/div[2]/div[4]/div[2]/div[1]/div[1]/div[2]/div[5]/div[1]/div[2]/input[7]";
        driver.findElementByXPath(dateFromXPath).click();
        //pasirenkam isskridimo data
        m.selectDateInCalendar(driver, start, m);

        //atidarom kalendoriu
        String dateToXPath = "/html/body/div[1]/form/div[4]/div[2]/div[4]/div[2]/div[1]/div[1]/div[2]/div[5]/div[2]/div[2]/input";
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath(dateToXPath));
        actions.moveToElement(element).click().build().perform();

        //pasirenkam gryzimo data
        m.selectDateInCalendar(driver, end, m);
        //isjungiam checkboxa pigiu skrydziu
        WebElement checkbox = driver.findElementByXPath("/html/body/div[1]/form/div[4]/div[2]/div[4]/div[2]/div[1]/div[1]/div[2]/div[12]/div[1]/input");
        if (checkbox.isSelected()) {
            checkbox.click();
        }

        //uzdarom cookius
        try {
            driver.findElementByXPath("/html/body/div[5]/a").click();
        } catch (Exception e) {
        }

        //suvedam miestus kur skrendam
        String inputFromXPath = "//*[@id=\"ctl00_FullRegion_MainRegion_ContentRegion_ContentFullRegion_ContentLeftRegion_CEPGroup1_CEPActive_cepNDPRevBookingArea_predictiveSearch_txtFrom\"]";
        String inputToXPath = "//*[@id=\"ctl00_FullRegion_MainRegion_ContentRegion_ContentFullRegion_ContentLeftRegion_CEPGroup1_CEPActive_cepNDPRevBookingArea_predictiveSearch_txtTo\"]";
        WebElement inputFromField = driver.findElementByXPath(inputFromXPath);
        WebElement inputtoField = driver.findElementByXPath(inputToXPath);
        inputFromField.sendKeys("ARN");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/form/div[4]/div[2]/div[4]/div[2]/div[1]/div[1]/div[2]/div[4]/div[2]/table/tbody/tr[1]/td[2]/div/div"))).click();
        inputtoField.sendKeys("LHR");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/form/div[4]/div[2]/div[4]/div[2]/div[1]/div[1]/div[2]/div[4]/div[2]/table/tbody/tr[2]/td[2]/div/div"))).click();

        //search
        driver.findElementByXPath("/html/body/div[1]/form/div[4]/div[2]/div[4]/div[2]/div[1]/div[1]/div[2]/div[13]/div[2]/div/a").click();

        try {
            Thread.sleep(5000);
            driver.switchTo().alert().accept();
        } catch (InterruptedException e) {

        }

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/form[6]/div[1]/div/div[1]/div[2]/div/div[1]/div[3]/div/div[2]/div[1]/div/div/table/tbody/tr[1]/td[1]")));

        //susikuriu du reference i tables
        WebElement timeTable = driver.findElementByXPath("/html/body/form[6]/div[1]/div/div[1]/div[2]/div/div[1]/div[3]/div/div[2]/div[1]/div/div/table/tbody");
        WebElement timeTableBack = driver.findElementByXPath("/html/body/form[6]/div[1]/div/div[1]/div[2]/div/div[1]/div[3]/div/div[3]/div[1]/div/div/table/tbody");

        //susikuriu du table eiluciu listus
        List<WebElement> tablePriceLines = timeTable.findElements(By.cssSelector("[id*=idLine]"));
        List<WebElement> tableBackPriceLines = timeTableBack.findElements(By.cssSelector("[id*=idLine]"));

        int count = 0;
        List<Flight> flights = new ArrayList<>();
        //einu per skrydziai i prieki ir paspaudziu pigiausia eilutej
        for (int i = 0; i < tablePriceLines.size(); i++) {
            m.clickOnPriceInTable(i, tablePriceLines);

            Flight flight = new Flight();
            int regulatorTIisGoodFlight = 1;

            List<WebElement> routesList = driver.findElements(By.cssSelector("[id^=toggleId_0]")).get(i).findElements(By.className("location"));

            if (!m.isGoodFlightSetChangeAirport(flight, routesList, allowedChange, regulatorTIisGoodFlight)) {
                continue;
            }

            //ir einu per skrydziai atgal ir paspaudziu pigiausia eilutej
            for (int h = 0; h < tableBackPriceLines.size(); h++) {
                m.clickOnPriceInTable(h, tableBackPriceLines);

                List<WebElement> routesBackList = driver.findElements(By.cssSelector("[id^=toggleId_1]")).get(h).findElements(By.className("location"));
                // List<WebElement> routesBackList = routesBack.get(h).findElements(By.className("location"));
                regulatorTIisGoodFlight = 2;
                if (!m.isGoodFlightSetChangeAirport(flight, routesBackList, allowedChange, regulatorTIisGoodFlight)) {
                    continue;
                }

                String[] fromPortList = routesList.get(0).getText().split(",");
                if (routesList.size() == 2) {
                    String[] toPortList = routesList.get(1).getText().split(",");
                    flight.setFirstFlightTo(toPortList[0]);
                }
                if (routesList.size() == 4) {
                    String[] toPortList = routesList.get(3).getText().split(",");
                    flight.setFirstFlightTo(toPortList[0]);
                }

                flight.setPrice(driver.findElementById("totalPriceCash").getText());
                flight.setTaxes(driver.findElementById("taxesAndFees").getText());
                flight.setFirstFlightFrom(fromPortList[0]);

                flight.setDepartueTime1(tablePriceLines.get(i).findElements(By.className("time")).get(1).getText());
                flight.setArivalTime1(tablePriceLines.get(i).findElements(By.className("time")).get(2).getText());
                flight.setDepartueTime2(tableBackPriceLines.get(i).findElements(By.className("time")).get(1).getText());
                flight.setArivalTime2(tableBackPriceLines.get(i).findElements(By.className("time")).get(2).getText());
                flight.setSecondFlightFrom(flight.getFirstFlightTo());
                flight.setSecondFlightTo(flight.getFirstFlightFrom());
                flight.setId("" + i + "/" + h);
                flights.add(flight);

            }

        }
        for (int i = 0; i < flights.size(); i++) {
            System.out.println(flights.get(i).toString());
        }
        System.out.println("count yra " + count);
    }

    public void selectDateInCalendar(FirefoxDriver driver, LocalDate date, Main m) {
        //patikrinam metus
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String yearXPath = "/html/body/div[3]/div/div/span[2]";
        int callendarYearInt = Integer.valueOf(driver.findElementByXPath(yearXPath).getText());
        int startYear = date.getYear();
        if (callendarYearInt > startYear) {
            System.out.println("lygiuojam metus");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div/a[1]/span"))).click();
            // driver.findElementByXPath("/html/body/div[3]/div/a[1]/span").click();
            m.selectDateInCalendar(driver, date, m);
        }
        if (callendarYearInt < startYear) {
            System.out.println("lygiuojam metus");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div/a[2]/span"))).click();
            //  driver.findElementByXPath("/html/body/div[3]/div/a[2]/span").click();
            m.selectDateInCalendar(driver, date, m);
        }
        if (callendarYearInt == startYear) {
            System.out.println("metus sulygiavom. parinkti metai yra " + callendarYearInt);
            m.selectDayAndMonthInCallendar(driver, date, m);
            return;

        }
    }

    public boolean isGoodFlightSetChangeAirport(Flight flight, List<WebElement> routesList, String allowedChange, int regulatorTIisGoodFlight) {
        if (routesList.size() > 4) {
            return false;
        }
        if (routesList.size() == 4) {
            String[] changePortList = routesList.get(1).getText().split(",");
            if (changePortList[0].equals(allowedChange)) {
                if (regulatorTIisGoodFlight == 1) {
                    flight.setFirstFlightChangeStop(changePortList[0]);
                }
                if (regulatorTIisGoodFlight == 2) {
                    flight.setSecondFlightChangeStop(changePortList[0]);
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public void selectDayAndMonthInCallendar(FirefoxDriver driver, LocalDate start, Main m) {

        String currentMonth = driver.findElementByClassName("ui-datepicker-month").getText();
        int currentMonthInt = 1;
        System.out.println(currentMonth);
        currentMonthInt = m.currentMonthToInt(currentMonth);
        System.out.println("dabartinis menuo yra " + currentMonth + ", " + currentMonthInt + "th.lyginam su " + start.getMonthValue());
        if (start.getMonthValue() < currentMonthInt) {
            driver.findElementByXPath("/html/body/div[3]/div/a[1]/span").click();
            m.selectDayAndMonthInCallendar(driver, start, m);
        }
        if (start.getMonthValue() > currentMonthInt) {
            driver.findElementByXPath("/html/body/div[3]/div/a[2]/span").click();
            m.selectDayAndMonthInCallendar(driver, start, m);
            ;
        }
        if (start.getMonthValue() == currentMonthInt) {
            System.out.println("menesi sulygiavom");
            String callendarFromXPath = "/html/body/div[3]";
            driver.findElementByXPath(callendarFromXPath).findElement(By.xpath("//*[contains(text(), '" + start.getDayOfMonth() + "') and @class=\"ui-state-default\"]")).click();
            return;
        }


    }

    public void clickOnPriceInTable(int i, List<WebElement> tablePriceLines) {
        String priceContent = tablePriceLines.get(i).findElement(By.cssSelector("[id*=_ECONBG]")).getText();
        if (priceContent.length() > 1) {
            tablePriceLines.get(i).findElement(By.cssSelector("[id*=_ECONBG]")).click();
        } else {
            priceContent = tablePriceLines.get(i).findElement(By.cssSelector("[id*=_ECOA]")).getText();
            if (priceContent.length() > 1) {
                tablePriceLines.get(i).findElement(By.cssSelector("[id*=_ECOA]")).click();
            } else {
                priceContent = tablePriceLines.get(i).findElement(By.cssSelector("[id*=_PREMN]")).getText();
                if (priceContent.length() > 1) {
                    tablePriceLines.get(i).findElement(By.cssSelector("[id*=_PREMN]")).click();
                }
                priceContent = tablePriceLines.get(i).findElement(By.cssSelector("[id*=_PREMB]")).getText();
                if (priceContent.length() > 1) {
                    tablePriceLines.get(i).findElement(By.cssSelector("[id*=_PREMB]")).click();
                } else {
                    priceContent = tablePriceLines.get(i).findElement(By.cssSelector("[id*=_PREMA]")).getText();
                    if (priceContent.length() > 1) {
                        tablePriceLines.get(i).findElement(By.cssSelector("[id*=_PREMA]")).click();
                    }
                }
            }
        }

    }

    public int currentMonthToInt(String currentMonth) {
        int currentMonthInt = 0;
        switch (currentMonth) {
            case "JANUARY":
                currentMonthInt = 1;
                break;
            case "FEBRUARY":
                currentMonthInt = 2;
                break;
            case "MARCH":
                currentMonthInt = 3;
                break;
            case "APRIL":
                currentMonthInt = 4;
                break;
            case "MAY":
                currentMonthInt = 5;
                break;
            case "JUNE":
                currentMonthInt = 6;
                break;
            case "JULY":
                currentMonthInt = 7;
                break;
            case "AUGUST":
                currentMonthInt = 8;
                break;
            case "SEPTEMBER":
                currentMonthInt = 9;
                break;
            case "OCTOBER":
                currentMonthInt = 10;
                break;
            case "NOVEMBER":
                currentMonthInt = 11;
                break;
            case "DECEMBER":
                currentMonthInt = 12;
                break;

        }
        return currentMonthInt;
    }


    public void enterWebsite(FirefoxDriver driver, String flyFrom, String flyTo, LocalDate start) {
        String siteUrl = "https://classic.flysas.com/en/de";
        driver.navigate().to(siteUrl);
        System.out.println("praejo");
    }
}
