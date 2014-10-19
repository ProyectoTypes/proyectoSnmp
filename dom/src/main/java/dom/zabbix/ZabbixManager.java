package dom.zabbix;

import org.json.JSONObject;

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

	protected String getToken(final String ip) {
		return ZabbixAutenticacion.obtenerTokenPorIp(ip);
	}

}
