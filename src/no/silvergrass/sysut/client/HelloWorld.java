package no.silvergrass.sysut.client;

import no.silvergrass.sysut.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


import com.googlecode.charts4j.*;

import static com.googlecode.charts4j.Color.*;
import static com.googlecode.charts4j.UrlUtil.normalize;
import static org.junit.Assert.assertEquals;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HelloWorld implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	// loadMenu items
	private Button inputPageButton;
	private Label errorLabel;
	
	// inputPage items
	private Button inputPageCloseButton, inputPageSubmitButton;
	private TextBox inputBox;
	private DialogBox inputDialogBox;
	private Label inputPageLabel;
	private int inputPageInput;
	
	// simulationPage items
	private DialogBox resultDialogBox;
	private Button resultPageCloseButton;
	
	
	private MyOnClickHandler handle;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// defaultLoading();
		loadMenu();
	}
	
	public void loadMenu(){
		inputPageButton = new Button("Input page");
		errorLabel = new Label();

		RootPanel.get("sendButtonContainer").add(inputPageButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		handle = new MyOnClickHandler();
		inputPageButton.addClickHandler(handle);
	}
	
	public void inputPage(){
		inputDialogBox = new DialogBox();
		inputPageCloseButton = new Button("Close");
		inputPageSubmitButton = new Button("Submit");
		inputPageLabel = new Label();	
		inputBox = new TextBox();
		
		inputDialogBox.setText("Input values here");
		inputDialogBox.setAnimationEnabled(true);
		
		inputPageLabel.setText("Input number of lightblubs to simulate");
		
		inputPageCloseButton.getElement().setId("closeButton");
		inputPageSubmitButton.getElement().setId("submitButton");
		
		inputDialogBox.setText("Input page");
		inputDialogBox.setAnimationEnabled(true);
		// We can set the id of a widget by accessing its Element
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(inputPageLabel);
		dialogVPanel.add(inputBox);
		dialogVPanel.getElement().setAttribute("align", "left");

		dialogVPanel.add(inputPageSubmitButton);
		dialogVPanel.add(inputPageCloseButton);
		
		inputDialogBox.setWidget(dialogVPanel);
		
		inputPageCloseButton.addClickHandler(handle);
		inputPageSubmitButton.addClickHandler(handle);
		
		inputDialogBox.center();
		inputPageCloseButton.setFocus(true);
	}
	
	//TODO:
	public void simulationPage(int count){
		greetingService.getSimulatedLights(count, 24,
				new AsyncCallback<Double[]>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						errorLabel.setText("Server contact failed: " + caught.getMessage());
					}

					@Override
					public void onSuccess(Double[] result) {
						inputDialogBox.hide();
						
						resultDialogBox = new DialogBox();
						resultPageCloseButton = new Button("Close");
						resultPageCloseButton.getElement().setId("resultPageCloseButton");
						
						VerticalPanel dialogVPanel = new VerticalPanel();
						dialogVPanel.addStyleName("dialogVPanel");
						
						resultDialogBox.setWidget(dialogVPanel);
						
						/**Testing av resultat*/
//						String temp = "";
//						
//						for(double r: result){
//							temp += r + ", ";
//						}
//						errorLabel.setText(temp);
						
						//Tungvint måte å gjøre det på, men det virker.
						double[] temp = new double[result.length];
						int tempi = 0;
						for (Double r: result){
							temp[tempi] = r.doubleValue();
							tempi++;
						}
						
						// TODO Auto-generated method stub
				        Line line1 = Plots.newLine(Data.newData(temp), Color.newColor("CA3D05"), "Lightblubs simulated");
				        line1.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
				        line1.addShapeMarkers(Shape.DIAMOND, Color.newColor("CA3D05"), 12);
				        line1.addShapeMarkers(Shape.DIAMOND, Color.WHITE, 8);
				        
				        // Defining chart.
//				        LineChart chart = GCharts.newLineChart(line1);
//				        chart.setSize(600, 450);
//				        chart.setTitle("Web Traffic|(in billions of hits)", WHITE, 14);
//				        chart.addHorizontalRangeMarker(40, 60, Color.newColor(RED, 30));
//				        chart.addVerticalRangeMarker(70, 90, Color.newColor(GREEN, 30));
//				        chart.setGrid(25, 25, 3, 2);
//
//				        // Defining axis info and styles
//				        AxisStyle axisStyle = AxisStyle.newAxisStyle(WHITE, 12, AxisTextAlignment.CENTER);
//				        AxisLabels xAxis = AxisLabelsFactory.newAxisLabels("Nov", "Dec", "Jan", "Feb", "Mar");
//				        xAxis.setAxisStyle(axisStyle);
//				        AxisLabels xAxis2 = AxisLabelsFactory.newAxisLabels("2007", "2007", "2008", "2008", "2008");
//				        xAxis2.setAxisStyle(axisStyle);
//				        AxisLabels yAxis = AxisLabelsFactory.newAxisLabels("", "25", "50", "75", "100");
//				        AxisLabels xAxis3 = AxisLabelsFactory.newAxisLabels("Month", 50.0);
//				        xAxis3.setAxisStyle(AxisStyle.newAxisStyle(WHITE, 14, AxisTextAlignment.CENTER));
//				        yAxis.setAxisStyle(axisStyle);
//				        AxisLabels yAxis2 = AxisLabelsFactory.newAxisLabels("Hits", 50.0);
//				        yAxis2.setAxisStyle(AxisStyle.newAxisStyle(WHITE, 14, AxisTextAlignment.CENTER));
//				        yAxis2.setAxisStyle(axisStyle);
//
//				        // Adding axis info to chart.
//				        chart.addXAxisLabels(xAxis);
//				        chart.addXAxisLabels(xAxis2);
//				        chart.addXAxisLabels(xAxis3);
//				        chart.addYAxisLabels(yAxis);
//				        chart.addYAxisLabels(yAxis2);
//
//				        // Defining background and chart fills.
//				        chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("1F1D1D")));
//				        LinearGradientFill fill = Fills.newLinearGradientFill(0, Color.newColor("363433"), 100);
//				        fill.addColorAndOffset(Color.newColor("2E2B2A"), 0);
//				        chart.setAreaFill(fill);
//				        String url = chart.toURLString();
				        
//				        ChartWidget widget = new ChartWidget( chart );
					}
				});
	}
	

	/**
	 * Default test load.
	 */
	public void defaultLoading() {
		final Button sendButton = new Button("Say hello!");
		final TextBox nameField = new TextBox();
		nameField.setText("Won't you say hello here?");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a
			 * response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Why won't you say hello to me? :-(");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
	
	private class MyOnClickHandler implements ClickHandler, KeyUpHandler{

		@Override
		public void onKeyUp(KeyUpEvent event) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			if (event.getSource() == inputPageButton ){
				errorLabel.setText("inputPageButton works!");
				inputPage();
			} else if (event.getSource() == inputPageCloseButton){
				inputDialogBox.hide();
				inputPageButton.setEnabled(true);
				inputPageButton.setFocus(true);
			} else if (event.getSource() == inputPageSubmitButton){
				try{
//					inputPageInput = Integer.parseInt(inputBox.getText());
//					errorLabel.setText("inputPageSubmitButton works! Input is: " + inputPageInput);
					simulationPage(Integer.parseInt(inputBox.getText()));
				}catch(Exception e){
					inputPageLabel.setText("Invalid input. Input number of lightblubs to simulate.");
				}
				//errorLabel.setText("inputPageSubmitButton works! Input is: " + inputPageInput);
			}
		}
		
	}
}
