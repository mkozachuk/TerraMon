package com.mkozachuk.terramon.suite;

import com.mkozachuk.terramon.browser.DataPageBrowserTest;
import com.mkozachuk.terramon.controller.TerraDataControllerTest;
import com.mkozachuk.terramon.service.MonitoringServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({DataPageBrowserTest.class, TerraDataControllerTest.class, MonitoringServiceTest.class})
public class DataPageTestSuite {
}
