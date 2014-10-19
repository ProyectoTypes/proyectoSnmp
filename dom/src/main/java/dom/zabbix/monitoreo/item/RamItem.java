package dom.zabbix.monitoreo.item;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

public class RamItem extends ItemManager {

	@Override
	protected void cargarParametros() {
		Map<String, String> cpu = new HashMap<String, String>();
		cpu.put("key_", "system.cpu.load");//Renombrar la key para cada caso.
		try {
			this.getParametrosJson().put("search", cpu);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
