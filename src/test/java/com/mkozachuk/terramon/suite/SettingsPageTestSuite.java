package com.mkozachuk.terramon.suite;

import com.mkozachuk.terramon.browser.SettingsPageBrowserTest;
import com.mkozachuk.terramon.controller.SettingsControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({SettingsPageBrowserTest.class, SettingsControllerTest.class})
public class SettingsPageTestSuite {
}
