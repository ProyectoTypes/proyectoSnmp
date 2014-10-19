package dom.zabbix.monitoreo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import dom.zabbix.ZabbixManager;

public abstract class ItemManager extends ZabbixManager {

	public String requestCpuItem(final String ip) throws JSONException {

		String token = this.obtenerToken(ip);

		this.getParametrosJson().put("output", "extend");
		this.getParametrosJson().put("host", HOST);
		
		this.getParametros();

		this.getObjetoJson().put("sortfield", "name");
		this.getObjetoJson().put("params", this.getParametrosJson());
		this.getObjetoJson().put("jsonrpc", "2.0");
		this.getObjetoJson().put("method", "item.get");
		this.getObjetoJson().put("auth", token);
		this.getObjetoJson().put("id", "1");

		return this.ejecutarJson().getString("result");
	}
	protected abstract void getParametros();
	
}
