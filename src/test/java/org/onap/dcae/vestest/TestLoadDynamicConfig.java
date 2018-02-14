/*-
 * ============LICENSE_START=======================================================
 * PROJECT
 * ================================================================================
 * Copyright (C) 2018 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */
package org.onap.dcae.vestest;

import static org.junit.Assert.*;

import java.io.FileReader;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.onap.dcae.controller.FetchDynamicConfig;
import org.onap.dcae.controller.LoadDynamicConfig;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TestLoadDynamicConfig {

	LoadDynamicConfig lc;
	String propop =  "src/test/resources/test_collector_ip_op.properties";
	
	
	@Before
	public void setUp() throws Exception {
	
		lc = new LoadDynamicConfig();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoad() {

		
		Boolean flag=false;
		

		lc.propFile = "src/test/resources/test_collector_ip_op.properties";
		lc.configFile = "src/test/resources/controller-config_dmaap_ip.json";
		
		String data = LoadDynamicConfig.readFile(propop);
		assertEquals(data.isEmpty(), flag);
	}


	@Test
	public void testwrite() {

		
		Boolean flag=false;
		
		lc.propFile = "src/test/resources/test_collector_ip_op.properties";
		lc.configFile = "src/test/resources/controller-config_dmaap_ip.json";
		lc.dmaapoutputfile = "src/test/resources/DmaapConfig-op.json";
		
		String data = LoadDynamicConfig.readFile(lc.configFile);
		JSONObject jsonObject = new JSONObject(data);
		lc.writeconfig(jsonObject);

		try{
			 JsonParser parser = new JsonParser();
			FileReader fr = new FileReader ( lc.dmaapoutputfile );
			final JsonObject jo =  (JsonObject) parser.parse (fr);
			final String jsonText = jo.toString ();
			jsonObject = new JSONObject ( jsonText );
		}
		catch(Exception e){
			System.out.println("Exception while opening the file");
			e.printStackTrace();
		}
		if(jsonObject.has("ves-fault-secondary"))
		{
			flag = true;
		}
		
		assertEquals(true, flag);

	}
}

