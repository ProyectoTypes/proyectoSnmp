package dom.zabbix;

import java.io.IOException;
import java.util.HashMap;

public class ZabbixAcceso {
	
	private String password;
	private String userName;
	public String authenticate(String username, String password, String url) {
	    this.password = password;
	    this.userName = username;
	    String zabbixApiUrl = url;
	 
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
}
