/*
 * Copyright (c) 2017 Anzen Soluciones S.A. de C.V. Mexico D.F. All rights reserved. THIS
 * SOFTWARE IS CONFIDENTIAL INFORMATION PROPIETARY OF ANZEN SOLUCIONES. THIS INFORMATION
 * SHOULD NOT BE DISCLOSED AND MAY ONLY BE USED IN ACCORDANCE THE TERMS DETERMINED BY THE
 * COMPANY ITSELF.
 */
package pruebaHSM.pruebaHSM;

import es.anzen.real.Realsecure;

/**
 * <p>
 * </p>
 * @author acevedito @version pruebaHSM @since pruebaHSM @category
 */
public class StressTest implements Runnable {

	private static final int ECB = 1;
	private static final int CBC = 0;
	private Realsecure secure;

	private int limit;

	/**
	 * <p>
	 * </p>
	 */
	public StressTest(int limit, Realsecure secure) {
		this.limit = limit;
		this.secure = secure;
	}

	@Override
	public void run() {

		System.out.println("EncryptData " + Thread.currentThread().getName());
		try {
			secure.OpenConnectionCLAN();
			secure.protectPassword("AF67B30E6E1396AFF8F29B2ABC6581AD", "88D6D5", "Prosa");

			for (int j = 0; j < limit; j++) {
				byte iv[] = null;

				byte[] outString1 = secure.EncryptData("FE7FA84879711656B81AB6D08C911AF0", null, ECB, null,
						"LEO SOLER FERNANDEZ    " + j);

				iv = new byte[8];
				for (int i = 0; i < iv.length; i++)
					iv[i] = 0;
				byte[] outString2 = secure.EncryptData("FE7FA84879711656B81AB6D08C911AF0", null, CBC, iv,
						"LEO SOLER FERNANDEZ    " + j);

				secure.DecryptData("FE7FA84879711656B81AB6D08C911AF0", null, ECB, null, outString1);

				secure.DecryptData("FE7FA84879711656B81AB6D08C911AF0", null, CBC, iv, outString2);

				byte dataByte1[] = { (byte) 0x4C, (byte) 0x45, (byte) 0x4F, (byte) 0x20, (byte) 0x20, (byte) 0x20,
						(byte) 0x20, (byte) 0x20 }; // LEO
				secure.EncryptData("FE7FA84879711656B81AB6D08C911AF0", null, ECB, null, dataByte1);

				byte dataByte2[] = { (byte) 0x58, (byte) 0x71, (byte) 0x73, (byte) 0xF9, (byte) 0x64, (byte) 0x29,
						(byte) 0xA0, (byte) 0x7D };
				secure.DecryptData("FE7FA84879711656B81AB6D08C911AF0", null, ECB, null, dataByte2);

				System.out.println(Thread.currentThread().getName());

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				secure.CloseConnectionCLAN();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
