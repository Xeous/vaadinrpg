package com.xeous.main;

import lombok.*;

import com.vaadin.ui.*;

/**
 * 
 * @author Attila Budai <attila.budai@webvalto.hu>
 * 
 */
public abstract class BaseDashboardElement extends Panel {

	@Getter(AccessLevel.PROTECTED)
	private VerticalLayout root;

	public BaseDashboardElement() {
		setupContent();
	}

	protected void setupContent() {
		root = new VerticalLayout();
		root.setMargin(true);
		this.setContent(root);
	}

	protected abstract void refresh();
}
