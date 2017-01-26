package core;

import static org.junit.Assert.*;

import org.junit.Test;

public class ActivePlayerTest {

	@Test
	public void testPlayerActivity() {
		Settings setting = new Settings();
		assertTrue(setting.isPlayerActive(setting.getPlayerBlack()));
		assertTrue(setting.isPlayerActive(setting.getPlayerGreen()));
		assertTrue(setting.isPlayerActive(setting.getPlayerRed()));
		assertTrue(setting.isPlayerActive(setting.getPlayerWhite()));
	}
	
	@Test
	public void testPlayerInactivity() {
		Settings setting = new Settings();
		setting.removeActivePlayer(setting.getPlayerWhite());
		
		assertTrue(setting.isPlayerActive(setting.getPlayerBlack()));
		assertTrue(setting.isPlayerActive(setting.getPlayerGreen()));
		assertTrue(setting.isPlayerActive(setting.getPlayerRed()));
		assertFalse(setting.isPlayerActive(setting.getPlayerWhite()));

		setting.removeActivePlayer(setting.getPlayerBlack());
		assertFalse(setting.isPlayerActive(setting.getPlayerBlack()));
		
		setting.removeActivePlayer(setting.getPlayerGreen());
		assertFalse(setting.isPlayerActive(setting.getPlayerGreen()));
		
		setting.removeActivePlayer(setting.getPlayerRed());
		assertFalse(setting.isPlayerActive(setting.getPlayerRed()));
	}

}
