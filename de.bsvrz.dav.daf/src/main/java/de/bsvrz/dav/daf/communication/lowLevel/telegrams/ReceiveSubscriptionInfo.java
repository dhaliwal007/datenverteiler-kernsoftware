/*
 * Copyright 2009 by Kappich Systemberatung, Aachen
 * Copyright 2007 by Kappich Systemberatung, Aachen
 * Copyright 2004 by Kappich+Kni� Systemberatung, Aachen
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

import de.bsvrz.dav.daf.main.ReceiveOptions;
import de.bsvrz.dav.daf.main.ReceiverRole;
import de.bsvrz.sys.funclib.debug.Debug;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Kappich Systemberatung
 * @version $Revision: 6339 $
 */
public class ReceiveSubscriptionInfo {

	private static final Debug _debug = Debug.getLogger();

	/** Die Basisanmeldeinformationen */
	private BaseSubscriptionInfo _baseSubscriptionInfo;


	/** Anmeldeoptionen der Empfangsanmeldungen */
	private ReceiveOptions _receiveOptions;

	/** Die Applikationsrolle */
	private ReceiverRole _receiverRole;

	/** Der Index der letzten Datensatz der vom Datenverteiler zur Applikation geschickt wurde. */
	private long _lastDataIndex = 0;

	/** Der letzte Zustand des Fehlerstatus */
	private byte _lastErrorState;

	/** Creates new ReceiveSubscriptionInfo */
	public ReceiveSubscriptionInfo() {
	}

	/**
	 * Erzeugt ein neues Objekt mit den gegebenen Parametern.
	 *
	 * @param baseSubscriptionInfo Basisanmeldeinformationen
	 * @param receiveOptions       Anmeldeoptionen der Empfangsanmeldungen
	 * @param receiverRole         ApplikationsRole
	 */
	public ReceiveSubscriptionInfo(
			BaseSubscriptionInfo baseSubscriptionInfo, ReceiveOptions receiveOptions, ReceiverRole receiverRole) {
		_baseSubscriptionInfo = baseSubscriptionInfo;
		_receiveOptions = receiveOptions;
		_receiverRole = receiverRole;
	}

	/**
	 * Gibt die Basisanmeldeinformationen zur�ck.
	 *
	 * @return Basisanmeldeinformationen
	 */
	public final BaseSubscriptionInfo getBaseSubscriptionInfo() {
		return _baseSubscriptionInfo;
	}

	/**
	 * Gibt an, ob Interesse an nachgelieferten oder an aktuellen Datens�tzen besteht.
	 *
	 * @return <code>true:</code> nachgelieferte Datens�tze erw�nscht, <code>false:</code> nachgelieferte Datens�tze nicht erw�nscht
	 */
	public final boolean getDelayedDataFlag() {
		return _receiveOptions.withDelayed();
	}

	/**
	 * Gibt an, ob Interesse an nur den ge�nderten Datens�tzen oder an allen Datens�tzen besteht.
	 *
	 * @return <code>true:</code> nur ge�nderte Datens�tze erw�nscht, <code>false:</code> alle Datens�tze erw�nscht
	 */
	public final boolean getDeltaDataFlag() {
		return _receiveOptions.withDelta();
	}

	/**
	 * Gibt an, ob die Applikation als ein normaler Empf�nger f�r diese Datums angemeldet ist oder nicht.
	 *
	 * @return <code>true:</code> Applikation ist normaler Emf�nger, <code>false:</code> Applikation ist kein normaler Empf�nger
	 */
	public final boolean isReceiver() {
		return _receiverRole.isReceiver();
	}

	/**
	 * Gibt an, ob die Applikation als Senke f�r dieses Datum angemeldet ist oder nicht.
	 *
	 * @return <code>true:</code>Applikation ist als Senke angemeldet, <code>false:</code>Applikation ist nicht als Senke angemeldet.
	 */
	public final boolean isDrain() {
		return _receiverRole.isDrain();
	}

	/**
	 * Gibt die Informationen �ber die Empfangsoptionen zur�ck.
	 *
	 * @return Informationen �ber die Empfangsoptionen
	 */

	public final ReceiveOptions getReceiveOptions() {
		return _receiveOptions;
	}

	/**
	 * Gibt die Informationen �ber die Empfangsapplikationsrole zur�ck.
	 *
	 * @return Informationen �ber die Empfangsapplikationsrole
	 */
	public final ReceiverRole getReceiverRole() {
		return _receiverRole;
	}

	/**
	 * Gibt den letzten Index des Datensatzes, den die Applikation empfangen hat, zur�ck.
	 *
	 * @return Index des Datensatzes
	 */
	public final long getLastDataIndex() {
		return _lastDataIndex;
	}

	/**
	 * Setzt den letzten Index des Datensatzes, den die Applikation empfangen hat.
	 *
	 * @param newDataIndex Datenindex
	 */
	public final void setLastDataIndex(long newDataIndex) {
//		final short attributeGroupCode = baseSubscriptionInfo.getAttributeGroupCode();
//		final boolean normalIndex = (lastDataIndex != 0) && ((lastDataIndex & 0x0000000000000003L) == 0) && ((newDataIndex & 0x0000000000000003L) == 0);
//		if((lastDataIndex != newDataIndex) && normalIndex) {
//			long oldSequence = (lastDataIndex >>> 2) & 0x3fffffff;
//			long newSequence = (newDataIndex >>> 2) & 0x3fffffff;
//			if((oldSequence + 1 != newSequence)) {
//				_debug.warning("Datensatzindex nicht fortlaufend" +
//				               ", Objekt: " + baseSubscriptionInfo.getObjectID() +
//				               ", Attributgruppe: " + attributeGroupCode +
//				               ", Aspekt: " + baseSubscriptionInfo.getAspectCode() +
//				               ", letzter Index: " + (lastDataIndex>>> 32) + "#" + ((lastDataIndex & 0xffffffffL) >> 2) + "#" + (lastDataIndex & 3) +
//				               ", aktueller Index: " + (newDataIndex >>> 32) + "#" + ((newDataIndex & 0xffffffffL) >> 2) + "#" + (newDataIndex & 3)
//				);
//			}
//		}
		_lastDataIndex = newDataIndex;
	}

	/**
	 * Gibt den letzten Fehlerstatus zur�ck.
	 *
	 * @return letzter Fehlerstatus
	 */
	public final byte getLastErrorState() {
		return _lastErrorState;
	}

	/**
	 * Setzt den letzten Fehlerstatus.
	 *
	 * @param error Fehlerstatus
	 */
	public final void setLastErrorState(byte error) {
		_lastErrorState = error;
	}

	/**
	 * Gibt eine Kopie des Objektes zur�ck.
	 *
	 * @return Kopie des Objektes
	 */
	public final ReceiveSubscriptionInfo cloneObject() {
		return new ReceiveSubscriptionInfo(
				_baseSubscriptionInfo, _receiveOptions, _receiverRole
		);
	}

	/**
	 * Aktualisiert diese Empfangsanmeldeinformationen durch Vereinigung mit einer weiteren Empfangsanmeldeinformation
	 *
	 * @param receiveSubscriptionInfo Weitere Empfangsanmeldeinformation
	 *
	 * @return <code>true<code> bei �nderung dieser Empfangsanmeldeinformation, </code>false</code> sonst
	 */
	public final boolean updateSubscriptionInfo(ReceiveSubscriptionInfo receiveSubscriptionInfo) {
		BaseSubscriptionInfo _baseSubscriptionInfo = receiveSubscriptionInfo.getBaseSubscriptionInfo();
		ReceiveOptions options = receiveSubscriptionInfo.getReceiveOptions();
		ReceiverRole role = receiveSubscriptionInfo.getReceiverRole();

		boolean changed = false;
		if((_baseSubscriptionInfo == null) || (options == null) || (role == null)) {
			return false;
		}
		if(_baseSubscriptionInfo.getObjectID() != this._baseSubscriptionInfo.getObjectID()) {
			return false;
		}
		boolean delayed = getDelayedDataFlag();
		boolean delta = getDeltaDataFlag();
		if(!delayed) {
			if(options.withDelayed()) {
				delayed = true;
				changed = true;
			}
		}
		if(delta) {
			if(!options.withDelta()) {
				delta = false;
				changed = true;
			}
		}
		if(changed) {
			_receiveOptions = new ReceiveOptions(delta, delayed);
		}
		// wenn receiverRole Empf�nger ist, dann darf keine Senke angemeldet werden
		// wenn receiverRole Senke ist, dann darf keine weitere Senke angemeldet werden
		if(_receiverRole.isReceiver() || _receiverRole.isDrain()) {
			if(role.isDrain()) {
				// zu einem Empf�nger oder einer Senke soll noch eine Senke angemeldet werden
				throw new IllegalStateException("Ung�ltige Anmeldung. Zu einem Empf�nger oder einer Senke darf keine weitere Senke angemeldet werden.");
			}
		}
		return changed;
	}

	/**
	 * Gibt einen String zurr�ck, der diesen Datensatz beschreibt.
	 *
	 * @return String, der diesen Datensatz beschreibt
	 */
	public final String parseToString() {
		String str = "Empfangsanmeldeinformationen:\n";
		str += _baseSubscriptionInfo.toString() + "\n";
		str += "Nachgeliefert Flag   : " + getDelayedDataFlag() + "\n";
		str += "Delta Daten Flag     : " + getDeltaDataFlag() + "\n";
		str += "Empfangsapplikation  : " + isReceiver() + "\n";
		str += "Senkeapplikation     : " + isDrain() + "\n";
		return str;
	}

	/**
	 * Schreiben eines Datensatzes in den �bergegebenen DataOutputStream
	 *
	 * @param out DataOutputStream
	 *
	 * @throws IOException, wenn ein Fehler beim Schreiben in den Ausgabestream auftritt.
	 */
	public final void write(DataOutputStream out) throws IOException {
		_baseSubscriptionInfo.write(out);
		out.writeBoolean(_receiveOptions.withDelayed());
		out.writeBoolean(_receiveOptions.withDelta());
		out.writeBoolean(_receiverRole.isDrain());
		// Ein Byte L�nge des nicht mehr ben�tigten Indikatorbitfelds
		out.writeByte(0);
	}

	/**
	 * Lesen eines Datensatzes vom �bergegebenen DataInputStream
	 *
	 * @param in DataInputStream
	 *
	 * @throws IOException, wenn ein Fehler beim Lesen des Streams auftritt.
	 */
	public final void read(DataInputStream in) throws IOException {
		_baseSubscriptionInfo = new BaseSubscriptionInfo();
		_baseSubscriptionInfo.read(in);
		boolean delayedDataFlag = in.readBoolean();
		boolean deltaDataFlag = in.readBoolean();
		_receiveOptions = new ReceiveOptions(deltaDataFlag, delayedDataFlag);
		boolean applikationStatus = in.readBoolean();
		_receiverRole = new ReceiverRole(applikationStatus);
		int size = (int)in.readByte();
		if(size > 0) {
			byte[] ignoredAttributesIndicator = new byte[size];
			for(int i = 0; i < size; ++i) {
				ignoredAttributesIndicator[i] = in.readByte();
			}
		}
	}

	/**
	 * Gibt die L�nge dieses Telegrams zur�ck
	 *
	 * @return die L�nge dieses Telegrams
	 */
	public int getLength() {
		return 18;
	}
}
