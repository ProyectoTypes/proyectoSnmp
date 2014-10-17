package dom.zabbix;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;

public class ZabbixAcceso {
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	private String password;
	private String userName;
	private String zabbixApiUrl;
	public String authenticate(String username, String password, String url) {
	    this.password = password;
	    this.userName = username;
	    this.zabbixApiUrl = url;
	 
	    StringBuilder uiConnectMessage = new StringBuilder();
	    StringBuilder sb = new StringBuilder();
	    sb.append("{\"jsonrpc\":\"2.0\"").
	    append(",\"params\":{").
	    append("\"user\":\"").append(username).
	    append("\",\"password\":\"").append(password).
	    append("\"},").
	    append("\"method\":\"user.authenticate\",").
	    append("\"id\":\"2\"}");
	 
	    try {
	        HttpResponse response = postAndGet(sb.toString());
	        HttpEntity entity = response.getEntity();
	 
	        HashMap untyped = mapper.readValue(EntityUtils.toString(entity), HashMap.class);
	        auth = untyped.get("result");
	 
	        if (auth == null) {
	            throw new IllegalArgumentException("Authorization failed to : " + url + ", using username : "               + username);
	        }
	        uiConnectMessage.append("Successfully connected to the server\n");
	 
	    } catch (IOException e) {
	        uiConnectMessage.append("Could not connect to the Zabbix Server at : ").
	        append(url).append(" Exception : ").append(e.getMessage()).append("\n");
	    }
	    return uiConnectMessage.toString();
	}
	private HttpResponse postAndGet(String request) throws IOException {
	    HttpClient client = new DefaultHttpClient();
	    HttpPost httpPost = new HttpPost(zabbixApiUrl);
	    httpPost.setEntity(new StringEntity(request));
	    httpPost.addHeader("Content-Type", "application/json-rpc");
	    return client.execute(httpPost);
	}
	
	/***
	 * 
	 */
	private Map<String, String> hostMap = new HashMap<String, String>();
	private Map<String, Map<String, String>> appMap = new HashMap<String, Map<String, String>>();
	private Object auth;
	private Map<String, String> mapper = new HashMap<String, JsonNode>();
	 
	private void getHosts() throws IOException {
	    StringBuilder sb = new StringBuilder();
	    sb.append("{\"jsonrpc\":\"2.0\",");
	    sb.append("\"method\":\"host.get\",");
	    sb.append("\"params\":{");
	    sb.append("\"output\":\"extend\"},");
	    sb.append(" \"auth\":\"").append(auth).append("\",");
	    sb.append("  \"id\":2}");
	 
	    HttpResponse response = postAndGet(sb.toString());
	    HttpEntity entity = response.getEntity();
	 
	    JsonNode rootNode = mapper.readValue(EntityUtils.toString(entity), JsonNode.class);
	    JsonNode resultNode = rootNode.path("result");
	    Iterator hostList = resultNode.getElements();
	    while (hostList.hasNext()) {
	        JsonNode element = hostList.next();
	        hostMap.put(element.findValue("hostid").toString().replaceAll("(\\[|\\]|\")", ""), element.findValue("host")
	            .toString().replaceAll("(\\[|\\]|\")", ""));
	    }
	}
	 
	private void getApps() throws IOException {
	    StringBuilder sb = new StringBuilder();
	    sb.append("{\"jsonrpc\":\"2.0\",");
	    sb.append("\"method\":\"application.get\",");
	    sb.append("\"params\":{");
	    sb.append("\"output\":\"extend\"},");
	    sb.append(" \"auth\":\"").append(auth).append("\",");
	    sb.append("  \"id\":2}");
	 
	    HttpResponse response = postAndGet(sb.toString());
	    HttpEntity entity = response.getEntity();
	 
	    JsonNode rootNode = mapper.readValue(EntityUtils.toString(entity), JsonNode.class);
	    JsonNode resultNode = rootNode.path("result");
	    Iterator appList = resultNode.getElements();
	    while (appList.hasNext()) {
	        JsonNode element = appList.next();
	        JsonNode hosts = element.path("hosts");
	        Iterator hostList = hosts.getElements();
	        while (hostList.hasNext()) {
	            JsonNode hostElement = hostList.next();
	            String hostID = hostElement.findValue("hostid").toString().replaceAll("(\\[|\\]|\")", "");
	            if (hostMap.containsKey(hostID)) {
	                if (appMap.containsKey(hostID)) {
	                    appMap.get(hostElement.findValue("hostid").toString().replaceAll("(\\[|\\]|\")", "")).put(element.
	                        findValue("name").toString().replaceAll("(\\[|\\]|\")", ""), element.findValue("applicationid").
	                        toString().replaceAll("(\\[|\\]|\")", ""));
	 
	                } else {
	                    Map apps = new HashMap();
	                    apps.put(element.findValue("name").toString().replaceAll("(\\[|\\]|\")", ""), element.
	                        findValue("applicationid").toString().replaceAll("(\\[|\\]|\")", ""));
	                    appMap.put(hostID, apps);
	                }
	            }
	        }
	    }
	}
}
