package dom.zabbix.monitoreo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

public class CpuItem extends ItemManager {

	@Override
	protected void getParametros() {
		Map<String, String> cpu = new HashMap<String, String>();
		cpu.put("key", "system.cpu.load");
		try {
			this.getParametrosJson().put("search", cpu);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
