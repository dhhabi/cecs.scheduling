package org.csulb.cecs.ui.sidebar;

import org.springframework.stereotype.Component;
import org.vaadin.spring.sidebar.annotation.SideBarSection;
import org.vaadin.spring.sidebar.annotation.SideBarSections;


@SideBarSections({
        @SideBarSection(id = Sections.PLANNING, caption = "Planning"),
        @SideBarSection(id = Sections.EXECUTION, caption = "Execution"),
        @SideBarSection(id = Sections.REPORTING, caption = "Reporting")
})
@Component
public class Sections {

    public static final String PLANNING = "planning";
    public static final String EXECUTION = "execution";
    public static final String REPORTING = "reporting";
}