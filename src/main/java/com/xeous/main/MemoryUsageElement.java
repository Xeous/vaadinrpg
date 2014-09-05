package com.xeous.main;

import com.vaadin.ui.Label;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class MemoryUsageElement extends BaseDashboardElement {

	private Label maxMemory       = new Label();
	private Label allocatedMemory = new Label();
	private Label freeMemory      = new Label();

	public static final String LABEL_CAPTION = "Memória használat";

	@Override
	public void attach() {
		super.attach();
		setupElement();
	}

	protected void setupElement() {
		initFields();
		getRoot().addComponent(maxMemory);
		getRoot().addComponent(allocatedMemory);
		getRoot().addComponent(freeMemory);
	}

	@Override
	protected void refresh() {
		initFields();
	}

	protected void initFields() {
		Runtime runtime = Runtime.getRuntime();

		allocatedMemory.setCaption("Lefoglalt: " + (runtime.totalMemory() / 1048576) + " MB");
		freeMemory.setCaption("Szabad: " + (runtime.freeMemory() / 1048576) + " MB");
		maxMemory.setCaption("Max: " + (runtime.maxMemory() / 1048576) + " MB");

		int used = (int)((runtime.totalMemory() - runtime.freeMemory()) / 1048576);
		this.setCaption(LABEL_CAPTION + " (" + used + " MB" + ")");
	}
}
