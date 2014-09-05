package com.xeous.main;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

@Getter
@Setter(AccessLevel.PROTECTED)
public class Menu extends CustomComponent {
	private HorizontalLayout layout = new HorizontalLayout();
	private MainView mainView;
	private MenuBar admin = new MenuBar();
	private MenuBar menuBar = new MenuBar();

	List<MenuItem> createMenus = new ArrayList<>();

	@Override
	public void attach() {
		super.attach();
		setupComponents();
	}

	public Menu(MainView mainView) {
		this.mainView = mainView;
	}

	private void setupComponents() {
		layout.setSizeFull();
		menuBar.setWidth(100, Unit.PERCENTAGE);
		menuBar.setAutoOpen(true);
		admin.setAutoOpen(true);
		layout.addComponent(menuBar);
		layout.setComponentAlignment(menuBar, Alignment.BOTTOM_LEFT);
		layout.addComponent(admin);
		layout.setComponentAlignment(admin, Alignment.BOTTOM_RIGHT);
		layout.setExpandRatio(menuBar, 1f);
		createMenuItems();
		setCompositionRoot(layout);
	}

	protected void createMenuItems() {
		createAdminMenu();
		createCharacterMenu();
		removeEmptyMenus();
	}

	private void removeEmptyMenus() {
		for (MenuItem item : createMenus) {
			if (!item.hasChildren()) {
				menuBar.removeItem(item);
			}
		}

		createMenus.clear();
	}

	protected void createCharacterMenu() {
		MenuBar.MenuItem characterItem = createMenuItem("Karakter");
		characterItem.addItem("Inventory", null, new PanelCommand<>());
		characterItem.addItem("Map", null, new PanelCommand<>());
		characterItem.addItem("Stats", null, new PanelCommand<>());
	}

	private MenuItem createMenuItem(String s) {
		MenuItem item = menuBar.addItem(s, null);
		createMenus.add(item);
		return item;
	}

	protected void createAdminMenu() {
		MenuBar.MenuItem adminItem = admin.addItem("User menu", null, null);
		adminItem.addSeparator();
		adminItem.addItem("Kijelentkezés", null, new LogoutCommand());
	}

	protected final class LogoutCommand implements MenuBar.Command {

		@Override
		public void menuSelected(MenuItem selectedItem) {
			((MainUI) UI.getCurrent()).setLoggedin(false);
			UI.getCurrent().getNavigator().navigateTo("login");
		}
	}

	@AllArgsConstructor
	private final class PanelCommand<T extends Component> implements
			PermissionedCommand {

		@Getter
		private Class<T> componentClass;

		@Getter
		private boolean skipPermissionCheck;

		private PanelCommand() {
			this.skipPermissionCheck = false;
		}

		@Override
		public void menuSelected(MenuItem selectedItem) {
			mainView.addTab(selectedItem.getText(), new Dashboard(),
					skipPermissionCheck);

		}
	}

	private interface PermissionedCommand extends Command {
		Class<?> getComponentClass();
	}

}
