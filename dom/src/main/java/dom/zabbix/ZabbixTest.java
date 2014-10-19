package dom.zabbix;

import org.json.JSONException;

import dom.zabbix.monitoreo.CpuItem;
import dom.zabbix.monitoreo.Host;
import dom.zabbix.monitoreo.ItemManager;

public class ZabbixTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Host host = new Host();
		try {
			System.out.println("HOST ID "+ host.requestHostId("127.0.0.1"));
			ItemManager cpu = new CpuItem();
			System.out.println("CPU "+cpu.requestCpuItem("127.0.0.1"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: "+e.getMessage());
		}
	}

}
