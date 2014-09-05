package com.xeous.main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class TabSheet extends com.vaadin.ui.TabSheet implements com.vaadin.ui.TabSheet.SelectedTabChangeListener {

	private final Map<String, Component> tabPanels = new HashMap<String, Component>();
	private List<Component> tabStack = new LinkedList<>();
	
	public TabSheet() {
		addSelectedTabChangeListener(this);
		setSizeFull();

		this.setCloseHandler(new com.vaadin.ui.TabSheet.CloseHandler() {

			@Override
			public void onTabClose(com.vaadin.ui.TabSheet tabsheet, Component tabContent) {
				tabPanels.remove(tabsheet.getTab(tabContent).getCaption());
				closeTab(tabsheet.getTab(tabContent));
			}
		});
	}
	
	public void closeTab(TabSheet.Tab tab) {
		Component componentToRemove = tab.getComponent();
		tabPanels.remove(this.getTab(componentToRemove).getCaption());
		tabStack.remove(componentToRemove);
		while (!tabStack.isEmpty() && this.getTab(((LinkedList<Component>) tabStack).getLast()) == null) {
			((LinkedList<Component>) tabStack).removeLast();
		}
		if (!tabStack.isEmpty()) {
			this.setSelectedTab(((LinkedList<Component>) tabStack).getLast());
		}
		this.removeComponent(componentToRemove);
	}
	
	public void closeActiveTab() {
		closeTab(this.getTab(this.getSelectedTab()));
	}

	public Component getTab(String caption) {
		if (caption == null || !tabPanels.containsKey(caption)) {
			return null;
		}

		return ((VerticalLayout) tabPanels.get(caption)).getComponent(0);
	}
	
	public TabSheet.Tab addTab(String caption, Component content) {
		return addTab(caption, content, true);
	}

	public TabSheet.Tab addTab(String caption, Component content, boolean closable) {

		if (!tabPanels.containsKey(caption)) {

			if (content != null) {
				content.setSizeFull();
			}
			VerticalLayout layout = new VerticalLayout();
			layout.setSizeFull();
			layout.addComponent(content);
			TabSheet.Tab newTab = null;
			try {
				newTab = this.addTab(layout);
			} catch (Exception e) {
				this.removeTab(this.getTab(layout));
				throw new IllegalStateException("Failed to add tab: ", e);
			}

			tabPanels.put(caption, layout);
			newTab.setCaption(caption);
			newTab.setClosable(closable);
			((LinkedList<Component>) tabStack).push(layout);
			setSelectedTab(layout);
			return newTab;
		} else {
			this.setSelectedTab(tabPanels.get(caption));
			return null;
		}
	}
	
	@Override
	public void selectedTabChange(com.vaadin.ui.TabSheet.SelectedTabChangeEvent event) {
		Component current = event.getTabSheet().getSelectedTab();
		tabStack.remove(current);
		tabStack.add(current);
	}
}
