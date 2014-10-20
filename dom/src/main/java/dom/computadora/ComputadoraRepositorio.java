/*
 * This is a software made for inventory control
 * 
 * Copyright (C) 2014, ProyectoTypes
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * 
 * 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package dom.computadora;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.json.JSONException;

import dom.zabbix.monitoreo.item.CpuItem;
import dom.zabbix.monitoreo.item.RamItem;

@DomainService(menuOrder = "10")
@Named("COMPUTADORA")
public class ComputadoraRepositorio {

	public ComputadoraRepositorio() {

	}

	// //////////////////////////////////////
	// Icono
	// //////////////////////////////////////

	public String getId() {
		return "computadora";
	}

	public String iconName() {
		return "Computadora";
	}

	// //////////////////////////////////////
	// Agregar Computadora
	// //////////////////////////////////////
	@NotContributed
	@MemberOrder(sequence = "10")
	@Named("Agregar Computadora")
	public Computadora addComputadora(final @Named("Direccion Ip") String ip) throws JSONException {
		return nuevaComputadora(ip, this.currentUserName());
	}

	@Programmatic
	public Computadora nuevaComputadora(final String ip, final String creadoPor) throws JSONException {
		final Computadora unaComputadora = container
				.newTransientInstance(Computadora.class);
		unaComputadora.setIp(ip);
		RamItem ram = new RamItem();
		unaComputadora.setMemoria(ram.requestItemGet(ip));
		CpuItem cpu = new CpuItem();
		unaComputadora.setProcesocpu(cpu.requestItemGet(ip));
		container.persistIfNotAlready(unaComputadora);
		container.flush();
		return unaComputadora;
	}

	// //////////////////////////////////////
	// Listar Computadora
	// //////////////////////////////////////

	@MemberOrder(sequence = "20")
	public List<Computadora> listar() {
		final List<Computadora> listaComputadoras = this.container
				.allMatches(new QueryDefault<Computadora>(Computadora.class,
						"eliminarComputadoraTrue"));
		if (listaComputadoras.isEmpty()) {
			this.container
					.warnUser("No hay Computadoras cargados en el sistema.");
		}
		return listaComputadoras;
	}



	// //////////////////////////////////////
	// CurrentUserName
	// //////////////////////////////////////

	private String currentUserName() {
		return container.getUser().getName();
	}

	// //////////////////////////////////////
	// Injected Services
	// //////////////////////////////////////

	@javax.inject.Inject
	private DomainObjectContainer container;

	
}