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

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Audited;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.DescribedAs;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ObjectType;

import dom.impresora.Impresora;
import dom.impresora.Impresora.TipoImpresora;
import dom.impresora.ImpresoraRepositorio;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.APPLICATION)
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Uniques({ @javax.jdo.annotations.Unique(name = "Computadora_ip_must_be_unique", members = {
		"creadoPor", "ip" }) })
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "autoCompletePorComputadora", language = "JDOQL", value = "SELECT "
				+ "FROM dom.computadora.Computadora "
				+ "WHERE creadoPor == :creadoPor && " + "ip.indexOf(:ip) >= 0"),
		@javax.jdo.annotations.Query(name = "eliminarComputadoraFalse", language = "JDOQL", value = "SELECT "
				+ "FROM dom.computadora.Computadora "
				+ "WHERE creadoPor == :creadoPor "
				+ "   && habilitado == false"),
		@javax.jdo.annotations.Query(name = "eliminarComputadoraTrue", language = "JDOQL", value = "SELECT "
				+ "FROM dom.computadora.Computadora "
				+ "WHERE habilitado == true"),
		@javax.jdo.annotations.Query(name = "buscarPorIp", language = "JDOQL", value = "SELECT "
				+ "FROM dom.computadora.Computadora "
				+ "WHERE creadoPor == :creadoPor "
				+ "   && ip.indexOf(:ip) >= 0") })
@ObjectType("COMPUTADORA")
@Audited
@AutoComplete(repository = ComputadoraRepositorio.class, action = "autoComplete")
@Bookmarkable
public class Computadora implements Comparable<Computadora> {

	// //////////////////////////////////////
	// Identificacion en la UI
	// //////////////////////////////////////

	public String title() {
		return this.getIp();
	}

	public String iconName() {
		return "Computadora";
	}

	// //////////////////////////////////////
	// IP (propiedad)
	// //////////////////////////////////////

	@PrimaryKey
	private String ip;

	@javax.jdo.annotations.Column(allowsNull = "false")
	@javax.jdo.annotations.PrimaryKey(column = "id")
	@DescribedAs("Direccion IP de la Computadora:")
	@MemberOrder(sequence = "10")
	public String getIp() {
		return ip;
	}

	public void setIp(final String ip) {
		this.ip = ip;
	}

	// //////////////////////////////////////
	// Mother (propiedad)
	// //////////////////////////////////////

	private String mother;

	@javax.jdo.annotations.Column(allowsNull = "false")
	@DescribedAs("Mother de la Computadora:")
	@MemberOrder(sequence = "20")
	public String getMother() {
		return mother;
	}

	public void setMother(final String mother) {
		this.mother = mother;
	}

	// //////////////////////////////////////
	// Procesador (propiedad)
	// //////////////////////////////////////

	private String procesador;

	@javax.jdo.annotations.Column(allowsNull = "false")
	@DescribedAs("Procesador de la Computadora:")
	@MemberOrder(sequence = "30")
	public String getProcesador() {
		return procesador;
	}

	public void setProcesador(final String procesador) {
		this.procesador = procesador;
	}

	// //////////////////////////////////////
	// Disco (propiedad)
	// //////////////////////////////////////

	public static enum CategoriaDisco {
		Seagate, Western, Otro;
	}

	private CategoriaDisco disco;

	@javax.jdo.annotations.Column(allowsNull = "false")
	public CategoriaDisco getDisco() {
		return disco;
	}

	public void setDisco(final CategoriaDisco disco) {
		this.disco = disco;
	}

	// //////////////////////////////////////
	// Memoria (propiedad)
	// //////////////////////////////////////

	private String memoria;

	@javax.jdo.annotations.Column(allowsNull = "false")
	@DescribedAs("Memoria de la Computadora:")
	@MemberOrder(sequence = "50")
	public String getMemoria() {
		return memoria;
	}

	public void setMemoria(final String memoria) {
		this.memoria = memoria;
	}

	// //////////////////////////////////////
	// Habilitado (propiedad)
	// //////////////////////////////////////

	public boolean habilitado;

	@Hidden
	@MemberOrder(sequence = "40")
	public boolean getEstaHabilitado() {
		return habilitado;
	}

	public void setHabilitado(final boolean habilitado) {
		this.habilitado = habilitado;
	}

	// //////////////////////////////////////
	// Impresora (propiedad)
	// //////////////////////////////////////

	private Impresora impresora;

	@MemberOrder(sequence = "50")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public Impresora getImpresora() {
		return impresora;
	}

	public void setImpresora(final Impresora impresora) {
		this.impresora = impresora;
	}

	public List<Impresora> choicesImpresora() {
		return this.impresoraRepositorio.listar();

	}

	public void modifyImpresora(final Impresora impresora) {
		Impresora currentImpresora = getImpresora();
		if (impresora == null || impresora.equals(currentImpresora)) {
			return;
		}
		impresora.agregarComputadora(this);
		return;
	}

	public boolean hideQuitarImpresora() {
		if (this.getImpresora() == null) {
			return true;
		}
		return false;
	}

	@Named("Borrar Impresora")
	public Computadora quitarImpresora() {
		Impresora currentImpresora = getImpresora();
		if (currentImpresora == null) {
			return this;
		}
		currentImpresora.setComputadora(null);
		setImpresora(null);
		return this;
	}

	@Hidden
	public void limpiarImpresora() {
		Impresora impresora = getImpresora();
		if (impresora == null) {
			return;
		}
		impresora.limpiarComputadora(this);
	}

	@Named("Nueva Impresora")
	public Impresora addImpresora(
			final @Named("Modelo") String modeloImpresora,
			final @Named("Fabricante") String fabricanteImpresora,
			final @Named("Tipo") TipoImpresora tipoImpresora) {
		return impresoraRepositorio.nuevaImpresora(modeloImpresora,
				fabricanteImpresora, tipoImpresora, this.currentUserName());

	}

	// //////////////////////////////////////
	// CurrentUserName
	// //////////////////////////////////////

	private String currentUserName() {
		return container.getUser().getName();
	}

	// //////////////////////////////////////
	// creadoPor
	// //////////////////////////////////////

	private String creadoPor;

	@Hidden
	@javax.jdo.annotations.Column(allowsNull = "false")
	public String getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(final String creadoPor) {
		this.creadoPor = creadoPor;
	}

	
	// //////////////////////////////////////
	// Injected Services
	// //////////////////////////////////////

	@Inject
	private ImpresoraRepositorio impresoraRepositorio;

	@Inject
	private DomainObjectContainer container;

	@Override
	public int compareTo(Computadora arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}