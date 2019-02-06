package com.restapi.client;

import java.applet.Applet;
import com.restapi.client.impl.*;

public class RESTClientApplet extends Applet {

	public void init() {
		GETRequestProcessor reqProc = new GETRequestProcessor();
		reqProc.testGETAPI1();
	}

}
