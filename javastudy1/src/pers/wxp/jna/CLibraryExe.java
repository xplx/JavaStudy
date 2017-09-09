package pers.wxp.jna;

public class CLibraryExe {
	public static void main(String[] args) {

		CLibrary.INSTANCE.printf("Hello, World/n");

		for (int i = 0; i < args.length; i++) {

			CLibrary.INSTANCE.printf("Argument %d: %s/n", i, args[i]);

		}
	}
}
