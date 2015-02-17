package org.csulb.cecs.ui;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.csulb.cecs.ui.security.AccessDeniedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventBusListenerMethod;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.mvp.MvpPresenterView;
import org.vaadin.spring.navigator.SpringViewProvider;
import org.vaadin.spring.security.Security;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@VaadinComponent
@SuppressWarnings("serial")
public class MainLayout extends VerticalLayout implements ViewDisplay, ClickListener, ViewChangeListener {
	
	private Panel viewContainer;
	
	private HorizontalLayout navbar;
	
	private MenuBar menuBar;
	
	private Button btnHome;
	private Button btnUser;
	private Button btnAdmin;
	private Button btnAdminHidden;
	private Button btnSignIn;
	private Button btnSignUp;
	private Button btnLogout;
	
	private String key = UUID.randomUUID().toString();
	
	@Autowired
	Security security;
	
	@Autowired
	SpringViewProvider springViewProvider;
	
	@Autowired
	EventBus eventBus;
	
	@PostConstruct
	public void postConstuct() {
		setSizeFull();
		eventBus.subscribe(this);
		
		navbar = new HorizontalLayout();
		navbar.setWidth("100%");
		//navbar.setMargin(true);
		navbar.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		addComponent(navbar);
		menuBar = new MenuBar();
		menuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		navbar.addComponent(menuBar);
		navbar.setComponentAlignment(menuBar, Alignment.MIDDLE_LEFT);
		navbar.setExpandRatio(menuBar, 1);
		initMenuBar();
		/*// Serve the image from the theme
		Resource res = new ClassResource("/img/csulb.gif");


		// Display the image without caption
		Image brandImage = new Image(null, res);*/
		
		
		final Label brandImage = new Label("CSULB Classes");
		brandImage.addStyleName(ValoTheme.LABEL_H4);
		brandImage.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		//navbar.addComponent(brandImage);
		//navbar.setComponentAlignment(brandImage, Alignment.MIDDLE_LEFT);
		//navbar.setExpandRatio(brandImage, 1);
		
		
				
		btnHome = new Button("Home", FontAwesome.HOME);
		btnHome.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnHome.setData(ViewToken.HOME);
		btnHome.addClickListener(this);
		navbar.addComponent(btnHome);
		
		btnUser = new Button("User home", FontAwesome.USER);
		btnUser.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnUser.setData(ViewToken.USER);
		btnUser.addClickListener(this);
		navbar.addComponent(btnUser);
		
		btnAdmin = new Button("Admin home", FontAwesome.USER_MD);
		btnAdmin.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnAdmin.setData(ViewToken.ADMIN);
		btnAdmin.addClickListener(this);
		navbar.addComponent(btnAdmin);
		
		btnAdminHidden = new Button("Admin secret", FontAwesome.EYE_SLASH);
		btnAdminHidden.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnAdminHidden.setData(ViewToken.ADMIN_HIDDEN);
		btnAdminHidden.addClickListener(this);
		navbar.addComponent(btnAdminHidden);
		
		btnSignIn = new Button("Sign in", FontAwesome.SIGN_IN);
		btnSignIn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnSignIn.setData(ViewToken.SIGNIN);
		btnSignIn.addClickListener(this);
		navbar.addComponent(btnSignIn);
		
		btnSignUp = new Button("Add User", FontAwesome.PENCIL_SQUARE_O);
		btnSignUp.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnSignUp.setData(ViewToken.SIGNUP);
		btnSignUp.addClickListener(this);
		navbar.addComponent(btnSignUp);
						
		btnLogout = new Button("Logout", FontAwesome.SIGN_OUT);
		btnLogout.setData("-");
		btnLogout.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		navbar.addComponent(btnLogout);
		btnLogout.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				logout();			
			}
		});
		
		viewContainer = new Panel();
		viewContainer.setSizeFull();
		
		addComponent(viewContainer);
		setExpandRatio(viewContainer, 1);				
	}
	
	private void initMenuBar(){
		MenuItem menuItemUser = menuBar.addItem("User", null);
		menuItemUser.addItem("Survey Form",new MenuBar.Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().getNavigator().navigateTo("/survey");	
			}
		});
				
		MenuItem menuItemAdmin = menuBar.addItem("Admin", null);
		menuItemAdmin.addItem("Add Instructor", new MenuBar.Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().getNavigator().navigateTo(ViewToken.SIGNUP);				
			}
		});
		menuItemAdmin.addItem("Request Survey", new MenuBar.Command() {	
			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().getNavigator().navigateTo(ViewToken.SURVEYREQUEST);				
			}
		});
		//Resource admin menuitem
		MenuItem menuItemAdminResources = menuItemAdmin.addItem("Resources", null);
		menuItemAdminResources.addItem("Courses", new MenuBar.Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().getNavigator().navigateTo(ViewToken.COURSE);				
			}
		});
		menuItemAdminResources.addItem("Rooms", new MenuBar.Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().getNavigator().navigateTo(ViewToken.ROOMS);				
			}
		});
		
		
	}
	
	@PreDestroy
	public void preDestroy() {
		eventBus.unsubscribe(this);
	}
	
	@Override
	public void showView(View view) {
		
		if (security.hasAuthority("ROLE_USER")) {
			displayUserNavbar();
		} else if (security.hasAuthority("ROLE_ADMIN")) {
			displayAdminNavbar();
		} else {
			displayAnonymousNavbar();
		}
		
		if (view instanceof MvpPresenterView) {
			viewContainer.setContent(((MvpPresenterView) view).getViewComponent());
		}
		
	}
	
	private void logout() {
		AnonymousAuthenticationToken authenticationToken = new AnonymousAuthenticationToken(key, "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
		final SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticationToken);
        
        UI.getCurrent().getNavigator().navigateTo(ViewToken.HOME);
        eventBus.publish(EventScope.UI, this, new UserSignedOutEvent());
	}

	@Override
	public void buttonClick(ClickEvent event) {
		
		UI.getCurrent().getNavigator().navigateTo((String) event.getButton().getData());				
									
	}

	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
		return true;
	}

	@Override
	public void afterViewChange(ViewChangeEvent event) {
		for (int i=0; i<navbar.getComponentCount(); i++) {
			
			if (navbar.getComponent(i) instanceof Button) {
				final Button btn = (Button) navbar.getComponent(i);
				btn.removeStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
				
				String view = (String) btn.getData();
				
				if (event.getViewName().equals(view)) {
					btn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
				}
			}
		}
						
	}
	
	private void displayAnonymousNavbar() {
		btnAdminHidden.setVisible(false);
		btnLogout.setVisible(false);
		btnSignIn.setVisible(true);
		btnSignUp.setVisible(false);	
		menuBar.setVisible(true);
	}
	
	private void displayUserNavbar() {
		btnAdminHidden.setVisible(false);
		btnLogout.setVisible(true);
		btnSignIn.setVisible(false);
		btnSignUp.setVisible(false);
		menuBar.setVisible(true);
	}
	
	private void displayAdminNavbar() {
		btnAdminHidden.setVisible(true);
		btnLogout.setVisible(true);
		btnSignIn.setVisible(false);
		btnSignUp.setVisible(true);
		menuBar.setVisible(true);
	}
	
	
	@EventBusListenerMethod
	public void onAccessDenied(org.vaadin.spring.events.Event<AccessDeniedEvent> event) {
		//TODO Redirect to login,
		
		if (event.getPayload().getCause() != null) {
			Notification.show("Access is denied.", "Service can be invoked only by Admin users.", Type.ERROR_MESSAGE);
		} else {
			if (event.getPayload().getViewToken().equals(ViewToken.USER)) {
				Notification.show("Access is denied.", "You must sign in before accessing User home.", Type.ERROR_MESSAGE);
			} else {
				Notification.show("Access is denied.", "You must sign in as Admin user before accessing Admin home.", Type.ERROR_MESSAGE);
			}
		}
	}

}
