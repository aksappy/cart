package ak.cart.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class StringUtilsTest {
	@Test
	public void testUniqueIdentifier() {
		String identifier = StringUtils.getUniqueIdentifier();
		assertNotNull(identifier);
		assertEquals(identifier.length(), 9);
	}
}
