package no.silvergrass.sysut.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	
	void getSimulatedLights(int count, int hours, AsyncCallback<Double[]> callback)
			throws IllegalArgumentException;
}
