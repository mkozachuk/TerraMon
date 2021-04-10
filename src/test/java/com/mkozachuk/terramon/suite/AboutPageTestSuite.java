package com.mkozachuk.terramon.suite;

import com.mkozachuk.terramon.browser.AboutPageBrowserTest;
import com.mkozachuk.terramon.controller.AboutControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AboutControllerTest.class, AboutPageBrowserTest.class, AboutControllerTest.class})
public class AboutPageTestSuite {
}
