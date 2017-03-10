package pruebaHSM.pruebaHSM;

import java.io.IOException;
import java.math.BigInteger;

import es.realsec.protector.CryptosecLANException;
import es.realsec.protector.Realsecure;

/**
 * Hello world!
 */
public class App {
	
	private static final int ECB = 1;
	private static final int CBC = 0;
	
	public static void main(String args[]) {
		byte iv[] = null;

		Realsecure secure = new Realsecure("", 12001, 3);
		byte[] outByte = null;
		// aqui habia un string
		byte[] outString = null;

		try {
			secure.OpenConnectionCLAN();

			// Proteger Password String
			outString = secure.protectPassword("E0ABB1827FEDDE1CC6E65D8A5F31C08E", "54FB70", "Mi Password");
			System.out.println("password string cifrado: " + String.format("%02x", new BigInteger(1, outString)));
			
			// Proteger password byte[]
			byte dataByte[] = { (byte) 0x4C, (byte) 0x45, (byte) 0x4F }; // LEO
			outByte = secure.protectPassword("E0ABB1827FEDDE1CC6E65D8A5F31C08E", "54FB70", dataByte);
			System.out.println("password byte array cifrado: " + String.format("%02x", new BigInteger(1, outByte)));

			// Cifrar datos 3DES ECB String
			byte[] outString1 = secure.EncryptData("2E7FD6E87508E2DF2E7FD6E87508E2DF", null, ECB, null,
					"LEO SOLER FERNANDEZ     ");// cifrado datos ECB:
												// 576A23F2A95706F17418FBF079975A0D6B641EE3F8105EC4
			System.out.println("cifrado datos ECB: " + String.format("%02x", new BigInteger(1, outString1)));

			// Cifrar datos 3DES CBC String
			iv = new byte[8];
			for (int i = 0; i < iv.length; i++)
				iv[i] = 0;
			byte[] outString2 = secure.EncryptData("2E7FD6E87508E2DF2E7FD6E87508E2DF", null, CBC, iv,
					"LEO SOLER FERNANDEZ     ");// cifrado datos CBC:
												// 576A23F2A95706F14F5337117EB47ED5203F94EA0CBC3C0D
			System.out.println("cifrado datos CBC: " + String.format("%02x", new BigInteger(1, outString2)));

			// Descifrar datos String
			outString = secure.DecryptData("2E7FD6E87508E2DF2E7FD6E87508E2DF", null, ECB, null,
//					"576A23F2A95706F17418FBF079975A0D6B641EE3F8105EC4"); // descifrado
					outString1);
																			// datos ECB:
																			// LEO SOLER
																			// FERNANDEZ
			System.out.println("descifrado datos ECB: " + new String(outString));

			// Descifrar datos String
			outString = secure.DecryptData("2E7FD6E87508E2DF2E7FD6E87508E2DF", null, CBC, iv,
//					"576A23F2A95706F14F5337117EB47ED5203F94EA0CBC3C0D"); // descifrado
					outString2);
																			// datos CBC:
																			// LEO SOLER
																			// FERNANDEZ
			System.out.println("descifrado datos CBC: " + new String(outString));

			// cifrar datos byte[]
			byte dataByte1[] = { (byte) 0x4C, (byte) 0x45, (byte) 0x4F, (byte) 0x20, (byte) 0x20, (byte) 0x20,
					(byte) 0x20, (byte) 0x20 }; // LEO
			outByte = secure.EncryptData("2E7FD6E87508E2DF2E7FD6E87508E2DF", null, ECB, null, dataByte1); // descifrado
																										// dato:
																										// 0x58,
																										// 0x71,
																										// 0x73,
																										// 0xF9,
																										// 0x64,
																										// 0x29,
																										// 0xA0,
																										// 0x7D
			System.out.println("descifrado dato: " + String.format("%02x", new BigInteger(1, outByte))); //asHex(outByte));
			
			// descifrar datos byte[]
			byte dataByte2[] = { (byte) 0x58, (byte) 0x71, (byte) 0x73, (byte) 0xF9, (byte) 0x64, (byte) 0x29,
					(byte) 0xA0, (byte) 0x7D };
			outByte = secure.DecryptData("2E7FD6E87508E2DF2E7FD6E87508E2DF", null, ECB, null, dataByte2);// descifrado
																										// dato:
																										// 0x4C,
																										// 0x45,
																										// 0x4F,
																										// 0x20,
																										// 0x20,
																										// 0x20,
																										// 0x20,
																										// 0x20
			System.out.println("descifrado dato: " + String.format("%02x", new BigInteger(1, outByte))); //asHex(outByte));

		} catch (CryptosecLANException | IOException e) {
			e.printStackTrace();
		} finally {
			try {
				secure.CloseConnectionCLAN();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
