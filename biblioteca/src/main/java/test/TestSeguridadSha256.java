package test;

import seguridad.Sha256;

public class TestSeguridadSha256 {
	public TestSeguridadSha256() {
	}

	public static void main(String[] args) {
		try {
			System.out.println(Sha256.getSha256("BASICO"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}