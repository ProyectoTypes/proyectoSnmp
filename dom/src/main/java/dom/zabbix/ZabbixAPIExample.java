package dom.zabbix;

import org.json.JSONException;
import org.json.JSONObject;

import com.goebl.david.Webb;

public class ZabbixAPIExample {

	public static void main(String[] args) {

		try {

			JSONObject mainJObj = new JSONObject();
			JSONObject paramJObj = new JSONObject();

			mainJObj.put("jsonrpc", "2.0");
			mainJObj.put("method", "user.login");

			paramJObj.put("user", "Admin");
			paramJObj.put("password", "zabbix");

			mainJObj.put("params", paramJObj);
			mainJObj.put("id", "1");

			Webb webb = Webb.create();

			System.out.println("Data to send: " + mainJObj.toString());

			JSONObject result = webb
					.post("http://127.0.0.1/zabbix/api_jsonrpc.php")
					.header("Content-Type", "application/json")
					.useCaches(false).body(mainJObj).ensureSuccess()
					.asJsonObject().getBody();

			System.out.println("Authentication token: "
					+ result.getString("result"));
			
			requestApiInfo(result.getString("result"));
			

		} catch (JSONException je) {

			System.out.println("Error creating JSON request to Zabbix API..."
					+ je.getMessage());

		}

	}

	/**
	 * Formate de la consulta
	 * <p>
	 * { "jsonrpc":"2.0", //json-rpc protocol version used
	 * "method":"apiinfo.version", //full API function name 
	 * "params":[ <arg_1>,<arg_2> ], //positional arguments, or
	 * "params":{"name1":<arg_1>,"name2":<arg_2>}, //named arguments
	 * "auth":"a6e895b98fde40f4f7badf112fd983bf", //omit to work anonymously
	 * "id":2 //any value to identify the request; JSON-RPC 2.0 standard
	 * recommends an integer }
	 * </p>
	 * * @param token
	 * @throws JSONException 
	 */
	public static void requestApiInfo(final String token) throws JSONException {
		JSONObject objetoJson = new JSONObject();
//		JSONObject parametrosJson = new JSONObject();
		objetoJson.put("jsonrpc", "2.0");
		objetoJson.put("method", "apiinfo.version");
		objetoJson.put("auth", token);
		objetoJson.put("id", "2");

		Webb webb = Webb.create();

		System.out.println("Datos enviados: " + objetoJson.toString());
		
		JSONObject result = webb
				.post("http://127.0.0.1/zabbix/api_jsonrpc.php")
				.header("Content-Type", "application/json")
				.useCaches(false).body(objetoJson).ensureSuccess()
				.asJsonObject().getBody();

		System.out.println("Api Info: "
				+ result.getString("result"));

		
	}

}