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

			requestHost(result.getString("result"));

		} catch (JSONException je) {

			System.out.println("Error creating JSON request to Zabbix API..."
					+ je.getMessage());

		}

	}

	/**
	 * Formate de la consulta
	 * <p>
	 * { "jsonrpc":"2.0", //json-rpc protocol version used
	 * "method":"apiinfo.version", //full API function name "params":[
	 * <arg_1>,<arg_2> ], //positional arguments, or
	 * "params":{"name1":<arg_1>,"name2":<arg_2>}, //named arguments
	 * "auth":"a6e895b98fde40f4f7badf112fd983bf", //omit to work anonymously
	 * "id":2 //any value to identify the request; JSON-RPC 2.0 standard
	 * recommends an integer }
	 * </p>
	 * * @param token
	 * 
	 * @throws JSONException
	 */
	public static void requestApiInfo(final String token) throws JSONException {
		JSONObject objetoJson = new JSONObject();
		// JSONObject parametrosJson = new JSONObject();
		objetoJson.put("jsonrpc", "2.0");
		objetoJson.put("method", "apiinfo.version");
		objetoJson.put("auth", token);
		objetoJson.put("id", "2");

		Webb webb = Webb.create();

		System.out.println("Datos enviados: " + objetoJson.toString());

		JSONObject result = webb
				.post("http://127.0.0.1/zabbix/api_jsonrpc.php")
				.header("Content-Type", "application/json").useCaches(false)
				.body(objetoJson).ensureSuccess().asJsonObject().getBody();

		System.out.println("Api Info: " + result.getString("result"));

	}

	/**
	 * FORMATO JSON
	 * <p>
	 * { "jsonrpc": "2.0", "method": "host.get",<br/>
	 * "params":<br/>
	 * { <br/>
	 * "output": ["hostid", "host" ],<br/>
	 * "selectInterfaces": [ "interfaceid", "ip" ] <br/>
	 * , "id": 2, <br/>
	 * "auth": "0424bd59b807674191e7d77572075f33"<br/>
	 * </p>
	 */
	public static void requestHost(final String token) throws JSONException {
		JSONObject objetoJson = new JSONObject();
		JSONObject parametrosJson = new JSONObject();
		JSONObject interfacesJson = new JSONObject();

		// Seteando datos de la sesion.
		objetoJson.put("jsonrpc", "2.0");
		objetoJson.put("method", "host.get");

		objetoJson.put("auth", token);
		objetoJson.put("id", "2");
		// Fin.
		// Seteando parametros
		String[] p = new String[2];
		p[0]= "hostid";
		p[1]="host";
		parametrosJson.put("output", p);
		p[0]= "interfaceid";
		p[1]="ip";
		parametrosJson.put("selectInterfaces", p);

		objetoJson.put("params", parametrosJson);

//		objetoJson.put("selectInterfaces", interfacesJson);
		// Fin.
		// objetoJson.put("selectInterfaces", "[ \"interfaceid\", \"ip\" ]");

		Webb webb = Webb.create();

		System.out.println("Datos enviados: " + objetoJson.toString());

		JSONObject result = webb
				.post("http://127.0.0.1/zabbix/api_jsonrpc.php")
				.header("Content-Type", "application/json").useCaches(false)
				.body(objetoJson).ensureSuccess().asJsonObject().getBody();

		System.out.println("Host info: " + result.getString("result"));

	}
	

}