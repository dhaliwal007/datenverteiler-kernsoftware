/*
 * Copyright 2007 by Kappich Systemberatung Aachen 
 * 
 * This file is part of de.bsvrz.puk.config.
 * 
 * de.bsvrz.puk.config is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * de.bsvrz.puk.config is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with de.bsvrz.puk.config; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package de.bsvrz.puk.config.configFile.datamodel;

import de.bsvrz.dav.daf.main.config.ConfigurationArea;

/**
 * Diese Klasse stellt die Abh�ngigkeit von einem Konfigurationsbereich zu einem anderen dar.
 *
 * @author Kappich Systemberatung
 * @version $Revision: 5074 $
 */
public class ConfigurationAreaDependency {

	/** Gibt an, ab welcher Version die Abh�ngigkeit aufgetreten ist. Vorher konnte der Bereich (<code>_area</code>) ohne den anderen Bereich benutzt werden. */
	private final short _dependencyOccurredAtVersion;

	/**
	 * Von diesem Bereich ist ein anderer Bereich abh�ngig. Der Bereich wird �ber die Pid referenziert, da es vorkommen kann, dass der Bereich nicht vorliegt (er
	 * wurde nicht kopiert, als Beispiel).
	 */
	private final String _pidDependantArea;

	/**
	 * Version, in der der Bereich <code>_dependantArea</code> zur Verf�gung stehen muss. Damit im Bereich <code>_area</code> alle Referenzen aufgel�st werden
	 * k�nnen.
	 */
	private final short _neededVersion;

	/** Gibt an, ob es sich nur um optionale Referenzen zwischen den beiden Bereichen handelt oder ob die Referenzen wirklich ben�tigt werden. */
	private ConfigurationAreaDependencyKind _kind;


	/**
	 * @param dependencyOccurredAtVersion Ab welcher Version ist die Abh�ngigkeit von <code>area</code> und <code>dependantArea</code> aufgetreten. Vor dieser
	 *                                    Version konnte <code>area</code> ohne <code>dependantArea</code> existieren.
	 * @param neededVersion               Version, in der der Bereich <code>dependantArea</code> zur Verf�gung stehen muss, damit der Bereich <code>area</code>
	 *                                    alle Referenzen aufl�sen kann.
	 * @param dependantArea               Bereich, von dem der Bereich <code>area</code> abh�ngig ist.
	 * @param kind                        Art der Abh�ngigkeit.
	 */
	public ConfigurationAreaDependency(
			final short dependencyOccurredAtVersion,
			final short neededVersion,
			final ConfigurationArea dependantArea,
			final ConfigurationAreaDependencyKind kind) {
		this(dependencyOccurredAtVersion, neededVersion, dependantArea.getPid(), kind);
	}

	public ConfigurationAreaDependency(
			final short dependencyOccurredAtVersion, final short neededVersion, final String pidDependantArea, final ConfigurationAreaDependencyKind kind) {
		_dependencyOccurredAtVersion = dependencyOccurredAtVersion;
		_pidDependantArea = pidDependantArea;
		_neededVersion = neededVersion;
		_kind = kind;
	}

	/** @return Version, ab der der Bereich vom Bereich {@link #getDependantArea()} abh�ngig wurde. */
	public short getDependencyOccurredAtVersion() {
		return _dependencyOccurredAtVersion;
	}

	/** @return Pid des Bereichs, von dem ein anderer Bereich abh�ngig ist. */
	public String getDependantArea() {
		return _pidDependantArea;
	}

	/** @return Version, in der der Bereich {@link #getDependantArea()} vorliegen muss, damit der Bereich alle Abh�ngigkeiten aufl�sen kann. */
	public short getNeededVersion() {
		return _neededVersion;
	}

	/** @return Art der Abh�ngigkeit zwischen den beiden Bereichen. */
	public ConfigurationAreaDependencyKind getKind() {
		return _kind;
	}

	public int hashCode() {
		int result = 17;
		result = 3 * result + getDependantArea().hashCode();
		// damit die beiden Versionen sich nicht aufheben k�nnen ("needed = 1,occured = 0" und "needed = 0,occured=1" w�rden sonst gleich sein)
		result = 3 * result + 3 * getNeededVersion();
		result = 3 * result + 31 * getDependencyOccurredAtVersion();
		result = 3 * result + 41 * getKind().getCode();

		return result;
	}

	public boolean equals(Object o) {
		if(o == this) return true;
		if(o instanceof ConfigurationAreaDependency) {
			final ConfigurationAreaDependency configurationAreaDependancy = (ConfigurationAreaDependency)o;
			if(configurationAreaDependancy.getDependantArea().equals(getDependantArea())) {
				if(configurationAreaDependancy.getKind().getCode() == getKind().getCode()) {
					if(configurationAreaDependancy.getNeededVersion() == getNeededVersion()) {
						if(configurationAreaDependancy.getDependencyOccurredAtVersion() == getDependencyOccurredAtVersion()) return true;
					}
				}
			}
		}
		return false;
	}

	public String toString() {
		return String.format(
				"Version%5d ist abh�ngig von Version%5d des Bereichs %s (%s)",
				getDependencyOccurredAtVersion(),
				getNeededVersion(),
				getDependantArea(),
				getKind()
		);
//		final StringBuffer text = new StringBuffer();
//		text.append("Version ").append(getDependencyOccurredAtVersion()).append(" enth�lt")
//		text.append(ConfigurationAreaDependency.class.getSimpleName()).append(": " + "\n");
//		text.append("Version, in der die Ab�ngigkeit entdeckt wurde: ").append(getDependencyOccurredAtVersion()).append("\n");
//		text.append("Version, in der der Bereich mindestens vorliegen muss damit die Abh�ngigkeit aufgel�st werden kann: ").append(getNeededVersion()).append(
//				"\n"
//		);
//		text.append("Bereich, von dem der obere Bereich abh�ngig ist: ").append(getDependantArea()).append("\n");
//		text.append("Art der Abh�ngigkeit: ").append(getKind()).append("\n");
//		return text.toString();
	}
}
