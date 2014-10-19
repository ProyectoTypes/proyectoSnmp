package dom.zabbix;

import org.json.JSONObject;

import com.goebl.david.Webb;

public abstract class ZabbixManager {

	public ZabbixManager() {
		this.objetoJson = new JSONObject();
		this.parametrosJson = new JSONObject();
	}

	private JSONObject objetoJson;

	public JSONObject getObjetoJson() {
		return objetoJson;
	}

	public void setObjetoJson(JSONObject objetoJson) {
		this.objetoJson = objetoJson;
	}

	private JSONObject parametrosJson;

	public JSONObject getParametrosJson() {
		return parametrosJson;
	}

	public void setParametrosJson(JSONObject parametrosJson) {
		this.parametrosJson = parametrosJson;
	}

	protected String obtenerToken(final String ip) {
		return ZabbixAutenticacion.obtenerTokenPorIp(ip);
	}
	protected JSONObject ejecutarJson()
	{
		Webb webb = Webb.create();

		return webb
		.post("http://127.0.0.1/zabbix/api_jsonrpc.php")
		.header("Content-Type", "application/json").useCaches(false)
		.body(getObjetoJson()).ensureSuccess().asJsonObject().getBody();

	}

}
