package dom.zabbix.monitoreo.item;

import org.json.JSONException;

import dom.zabbix.ZabbixManager;

public abstract class ItemManager extends ZabbixManager {

	public String requestItemGet(final String ip) throws JSONException {

		String token = this.obtenerToken(ip);

		this.getParametrosJson().put("output", "extend");
		this.getParametrosJson().put("host", HOST);

		this.cargarParametros();

		this.getObjetoJson().put("sortfield", "name");
		this.getObjetoJson().put("params", this.getParametrosJson());
		this.getObjetoJson().put("jsonrpc", "2.0");
		this.getObjetoJson().put("method", "item.get");
		this.getObjetoJson().put("auth", token);
		this.getObjetoJson().put("id", "1");

		String resultado ="Sin Especificar";
		String[] cadena = this.ejecutarJson().getString("result").split(",");
		for(int i =0 ; i<cadena.length;i++)
			if(cadena[i].startsWith("\"lastvalue"))
				resultado = cadena[i].split(":")[1];
		return resultado;
	}

	/**
	 * Metodo abtrascto del template method. Cada subclase implementara este
	 * metodo a su criterio.
	 */
	protected abstract void cargarParametros();

}
