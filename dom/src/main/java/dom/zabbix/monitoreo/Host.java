package dom.zabbix.monitoreo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import dom.zabbix.ZabbixManager;

/**
 * Administra toda la informacion referida al Host.
 * 
 * @author cipoleto
 * 
 */
public class Host extends ZabbixManager {

	/**
	 * Permite obtener el id del host. Con este id es posible realizar consultas
	 * sobre datos que genera el host.
	 * 
	 * @param token
	 * @throws JSONException
	 */
	public String requestHostId(final String ip)
			throws JSONException {
		String token = this.obtenerToken(ip);
		this.getParametrosJson().put("output", "hostid");
		this.getParametrosJson().put("selectGroups", "extend");
		Map<String, String> filtro = new HashMap<String, String>();
		filtro.put("host", "inventario");

		this.getParametrosJson().put("filter", filtro);

		getObjetoJson().put("params", this.getParametrosJson());
		getObjetoJson().put("jsonrpc", "2.0");
		getObjetoJson().put("method", "host.get");
		getObjetoJson().put("auth", token);
		getObjetoJson().put("id", "2");	
		
		return  this.ejecutarJson().getString("result");
	}
	

}
