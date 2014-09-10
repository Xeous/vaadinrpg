package com.xeous.main;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.shape.Circle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class Dashboard extends CustomComponent {

	public static final String REFRESH_BUTTON_CAPTION = "Dashboard Frissítés";
	public static final Integer FIRST_SPLIT_POSITION = 240;
	public static final Integer SECOND_SPLIT_POSITION = 830;

	private VerticalLayout verticalLayout = new VerticalLayout();
	private HorizontalLayout layout = new HorizontalLayout();
	private VerticalLayout firstColumn = new VerticalLayout();
	private VerticalLayout secondColumn = new VerticalLayout();
	private VerticalLayout thirdColumn = new VerticalLayout();
	private VerticalLayout refreshButtonLayout = new VerticalLayout();
	private List<BaseDashboardElement> components = new ArrayList<>();
	private Button refreshButton = new Button(REFRESH_BUTTON_CAPTION);
	@Setter(AccessLevel.NONE)
	private Button.ClickListener listener;
	private BaseDashboardElement memoryUsageElement;

	private void setupSplitPanels() {
		HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		HorizontalSplitPanel horizontalSplitPanel2 = new HorizontalSplitPanel();
		horizontalSplitPanel
				.setSplitPosition(FIRST_SPLIT_POSITION, Unit.PIXELS);
		horizontalSplitPanel2.setSplitPosition(SECOND_SPLIT_POSITION,
				Unit.PIXELS);
		horizontalSplitPanel.setStyleName(Reindeer.SPLITPANEL_SMALL);
		horizontalSplitPanel2.setStyleName(Reindeer.SPLITPANEL_SMALL);

		horizontalSplitPanel.setFirstComponent(firstColumn);
		horizontalSplitPanel.setSecondComponent(horizontalSplitPanel2);
		horizontalSplitPanel2.setFirstComponent(secondColumn);
		horizontalSplitPanel2.setSecondComponent(thirdColumn);
		layout.addComponent(horizontalSplitPanel);
	}

	protected void setupElements() {
		memoryUsageElement = new MemoryUsageElement();

		addComponent(memoryUsageElement);
		setupSplitPanels();
		setupLayout();
		setupVerticalLayout();
		setCompositionRoot(verticalLayout);

	}

	private void setupLayout() {
		layout.setSizeFull();
		layout.setHeight(100, Unit.PERCENTAGE);
		firstColumn.addComponent(memoryUsageElement);
		firstColumn.addComponent(refreshButtonLayout);
		DrawingArea canvas = new DrawingArea(400, 400);
		Circle circle = new Circle(100, 100, 50);
		circle.setFillColor("red");
		canvas.add(circle);
	//	secondColumn.addComponent(canvas);
		DrawingArea canvas2 = new DrawingArea(400, 400);
		RootPanel.get();
		canvas2.add(circle);

	}

	protected void addComponent(BaseDashboardElement... elements) {
		for (BaseDashboardElement element : elements) {
			this.components.add(element);
		}

	}

	protected void refreshElements() {
		for (BaseDashboardElement element : components) {
			element.refresh();
		}
	}

	protected void setupVerticalLayout() {
		verticalLayout.setSizeFull();
		verticalLayout.addComponent(layout);
		verticalLayout.setExpandRatio(layout, 0.9f);
		setupRefreshButton();
	}

	private void setupRefreshButton() {
		refreshButtonLayout.setMargin(true);
		refreshButtonLayout.setSpacing(false);
		listener = createRefreshButtonClickListener();
		refreshButton.addClickListener(listener);
		refreshButtonLayout.addComponent(refreshButton);
	}

	protected Button.ClickListener createRefreshButtonClickListener() {
		return new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				refreshElements();
			}
		};
	}

	@Override
	public void attach() {
		super.attach();
		setupElements();
	}

}
