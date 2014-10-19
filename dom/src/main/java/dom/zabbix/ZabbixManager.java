package dom.zabbix;

public abstract class ZabbixManager {

	protected String getToken(final String ip)
	{
		return ZabbixAutenticacion.obtenerTokenPorIp(ip);
	}
	
	
	
}
