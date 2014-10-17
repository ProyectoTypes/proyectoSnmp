package dom.snmp;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.snmp4j.smi.OID;

@DomainService
@Named("Monitorear Hardware")
public class SNMPManagerRepositorio {

	public String actualizar() throws IOException {
		SNMPManager client = new SNMPManager("udp:127.0.0.1/161");
		client.start();
		String sysDescr = client
				.getAsString(new OID(".1.3.6.1.2.1.1.1.0"));

		return sysDescr;
	}

	public String escanearSistemaLocal() throws IOException {

		SNMPManager client = new SNMPManager("udp:127.0.0.1/161");
		client.start();
		String sysDescr = client.getAsString(new OID("system"));

		return sysDescr;
	}

	public String obtenerSistemaOperativo() throws IOException {

		SNMPManager client = new SNMPManager("udp:127.0.0.1/161");
		client.start();
		String sysDescr = client.getAsString(new OID(".1.3.6.1.2.1.1.1.0"));

		this.container.informUser(sysDescr);
		//String[] cadena =  sysDescr.split(" ");
		//return cadena[0];
		return null;
		
		/*
		    snmpPrueba client = new snmpPrueba("udp:127.0.0.1/161");
			client.start();
			String sysDescr = client.getAsString(new OID(".1.3.6.1.2.1.1.1.0"));
			System.out.println(sysDescr);
		 */
	}
	
	
	@Inject
	private DomainObjectContainer container;

}
