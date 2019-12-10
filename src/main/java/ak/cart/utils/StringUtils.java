package ak.cart.utils;

import java.util.UUID;

public final class StringUtils {
	private StringUtils() {

	}

	public static String getUniqueIdentifier() {
		return UUID.randomUUID().toString().substring(0, 10).replaceAll("-", "").toUpperCase();
	}
}
