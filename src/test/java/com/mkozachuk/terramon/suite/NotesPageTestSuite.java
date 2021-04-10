package com.mkozachuk.terramon.suite;

import com.mkozachuk.terramon.browser.NotesPageBrowserTest;
import com.mkozachuk.terramon.controller.NotesControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({NotesPageBrowserTest.class, NotesControllerTest.class})
public class NotesPageTestSuite {
}
