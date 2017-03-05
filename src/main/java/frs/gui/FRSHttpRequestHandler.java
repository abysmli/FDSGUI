package frs.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * class HttpRequestHandler Handler center for all post requests Get all request
 * from Java GUI and send them to backend Get Post Responds from backend and
 * forward to Java GUI
 * 
 * @author Li, Yuan
 */
public class FRSHttpRequestHandler {

	private final HttpClient client = HttpClientBuilder.create().build();
	private final CookieStore cookieStore = new BasicCookieStore();
	private final HttpContext httpContext = new BasicHttpContext();
	private final String USER_AGENT = "Mozilla/5.0";
	private String sURL;

	public FRSHttpRequestHandler(String URL) {
		sURL = URL;
		httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
	}
	
	public JSONObject resetDatabase() throws Exception {
		String url = "http://" + sURL + "/resetDatabase";
		List<NameValuePair> urlParameters = new ArrayList<>();
		StringBuilder content = sendPostRequest(url, urlParameters);
		JSONObject result = new JSONObject(content.toString());
		return result;
	}

	public JSONArray getFaults() throws Exception {
		String url = "http://" + sURL + "/getFaults";
		List<NameValuePair> urlParameters = new ArrayList<>();
		StringBuilder content = sendPostRequest(url, urlParameters);
		JSONArray result = new JSONArray(content.toString());
                
		JSONArray finalResult = new JSONArray();
		for(int i = 0; i<result.length(); i++) {
			JSONObject obj = result.getJSONObject(i);
			JSONObject executeCommand = new JSONObject(obj.getString("execute_command"));
			obj.put("execute_command", executeCommand);
			finalResult.put(obj);
		}
		
		return finalResult;
	}
	
	public JSONArray getComponents() throws Exception {
		String url = "http://" + sURL + "/getComponents";
		List<NameValuePair> urlParameters = new ArrayList<>();
		StringBuilder content = sendPostRequest(url, urlParameters);
		JSONArray finalResult = new JSONArray(content.toString());
		return finalResult;
	}
	
	public JSONArray getFunctions() throws Exception {
		String url = "http://" + sURL + "/getFunctions";
		List<NameValuePair> urlParameters = new ArrayList<>();
		StringBuilder content = sendPostRequest(url, urlParameters);
		JSONArray finalResult = new JSONArray(content.toString());
		return finalResult;
	}
	
	public JSONArray getSubsystems() throws Exception {
		String url = "http://" + sURL + "/getSubsystems";
		List<NameValuePair> urlParameters = new ArrayList<>();
		StringBuilder content = sendPostRequest(url, urlParameters);
		JSONArray finalResult = new JSONArray(content.toString());
		return finalResult;
	}
	
	public JSONArray getSubfunctions() throws Exception {
		String url = "http://" + sURL + "/getSubfunctions";
		List<NameValuePair> urlParameters = new ArrayList<>();
		StringBuilder content = sendPostRequest(url, urlParameters);
		JSONArray finalResult = new JSONArray(content.toString());
		return finalResult;
	}
	
	public JSONArray getMainfunctions() throws Exception {
		String url = "http://" + sURL + "/getMainfunctions";
		List<NameValuePair> urlParameters = new ArrayList<>();
		StringBuilder content = sendPostRequest(url, urlParameters);
		JSONArray finalResult = new JSONArray(content.toString());
		return finalResult;
	}
	
	public JSONArray getSubsystemComponentRel() throws Exception {
		String url = "http://" + sURL + "/getSubsystemComponentRel";
		List<NameValuePair> urlParameters = new ArrayList<>();
		StringBuilder content = sendPostRequest(url, urlParameters);
		JSONArray finalResult = new JSONArray(content.toString());
		return finalResult;
	}
	
	public JSONArray getComponentFunctionRel() throws Exception {
		String url = "http://" + sURL + "/getComponentFunctionRel";
		List<NameValuePair> urlParameters = new ArrayList<>();
		StringBuilder content = sendPostRequest(url, urlParameters);
		JSONArray finalResult = new JSONArray(content.toString());
		return finalResult;
	}
	
	public JSONArray getSubfunctionFunctionRel() throws Exception {
		String url = "http://" + sURL + "/getSubfunctionFunctionRel";
		List<NameValuePair> urlParameters = new ArrayList<>();
		StringBuilder content = sendPostRequest(url, urlParameters);
		JSONArray finalResult = new JSONArray(content.toString());
		return finalResult;
	}
	
	public JSONArray getMainfunctionSubfunctionRel() throws Exception {
		String url = "http://" + sURL + "/getMainfunctionSubfunctionRel";
		List<NameValuePair> urlParameters = new ArrayList<>();
		StringBuilder content = sendPostRequest(url, urlParameters);
		JSONArray finalResult = new JSONArray(content.toString());
		return finalResult;
	}
	
	public JSONArray getSymptoms() throws Exception {
		String url = "http://" + sURL + "/getSymptoms";
		List<NameValuePair> urlParameters = new ArrayList<>();
		StringBuilder content = sendPostRequest(url, urlParameters);
		JSONArray finalResult = new JSONArray(content.toString());
		return finalResult;
	}

	public JSONObject getLastComponentValue() throws Exception {
		String url = "http://" + sURL + "/getLastComponentValue";
		List<NameValuePair> urlParameters = new ArrayList<>();
		StringBuilder content = sendPostRequest(url, urlParameters);
		JSONObject finalResult = new JSONObject(content.toString());
		return finalResult;
	}
	
	public JSONArray getComponentValue() throws Exception {
		String url = "http://" + sURL + "/getComponentValue";
		List<NameValuePair> urlParameters = new ArrayList<>();
		StringBuilder content = sendPostRequest(url, urlParameters);
		JSONArray finalResult = new JSONArray(content.toString());
		return finalResult;
	}
	
	public JSONArray getFaultProcedureInfos() throws Exception {
		String url = "http://" + sURL + "/getFaultProcedureInfos";
		List<NameValuePair> urlParameters = new ArrayList<>();
		StringBuilder content = sendPostRequest(url, urlParameters);
		JSONArray finalResult = new JSONArray(content.toString());
		return finalResult;
	}
	
	private StringBuilder sendPostRequest(String url, List<NameValuePair> urlParameters)
			throws IOException, UnsupportedEncodingException, IllegalStateException {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("User-Agent", USER_AGENT);
		HttpResponse response = client.execute(httpGet, httpContext);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuilder content = new StringBuilder();
		String line;
		while (null != (line = rd.readLine())) {
			content.append(line);
		}
		return content;
	}
}
