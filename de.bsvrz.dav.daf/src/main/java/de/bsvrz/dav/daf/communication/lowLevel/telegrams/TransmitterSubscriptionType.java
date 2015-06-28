/*
 * Copyright 2013 by Kappich Systemberatung Aachen
 * 
 * This file is part of de.bsvrz.dav.daf.
 * 
 * de.bsvrz.dav.daf is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 * 
 * de.bsvrz.dav.daf is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with de.bsvrz.dav.daf; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package de.bsvrz.dav.daf.communication.lowLevel.telegrams;

/**
 * @author Kappich Systemberatung
 * @version $Revision: 0000 $
 */
public enum TransmitterSubscriptionType {
	/**
	 * Anmeldung als Sender bei einem remote-Zentraldatenverteiler, welcher die Funktion einer Senke �bernimmt.
	 */
	Sender(TransmitterSubscriptionsConstants.SENDER_SUBSCRIPTION),
	/**
	 * Anmeldung als Empf�nger bei einem remote-Zentraldatenverteiler, welcher die Funktion einer Quelle �bernimmt.
	 */
	Receiver(TransmitterSubscriptionsConstants.RECEIVER_SUBSCRIPTION);

	private final byte _byteValue;

	TransmitterSubscriptionType(final byte byteValue) {

		_byteValue = byteValue;
	}

	public byte toByte(){
		return _byteValue;
	}

	public static TransmitterSubscriptionType fromByte(byte b){
		switch(b){
			case TransmitterSubscriptionsConstants.SENDER_SUBSCRIPTION: return Sender;
			case TransmitterSubscriptionsConstants.RECEIVER_SUBSCRIPTION: return Receiver;
			default:
			throw new IllegalArgumentException("Unbenannter Anmeldungstyp: " + b);
		}
	}
}
