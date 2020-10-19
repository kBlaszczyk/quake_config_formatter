package de.orchound;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntriesFormatterTest {

	private final EntriesFormatter entriesFormatter = new EntriesFormatter();
	private final List<String> input = Arrays.asList(
		"bind MOUSE2 \"+moveup\"",
		"bind MOUSE3 \"+button2\"",
		"bind MOUSE4 \"vstr weapPML\"",
		"seta cg_ammoWarning \"1\"",
		"seta cg_autoAction \"0\"",
		"seta cg_autoswitch \"0\"",
		"seta g_teamAutoJoin \"0\"",
		"seta g_teamForceBalance \"0\"",
		"seta m_accel \"0\"",
		"seta match_readypercent \"51\"",
		"seta model \"doom/pm\"",
		"seta r_picmip \"6\"",
		"seta r_textureMode \"GL_LINEAR_MIPMAP_LINEAR\"",
		"seta r_vertexLight \"0\"",
		"seta r_width \"1920\"",
		"seta weapMG \"weapon 2;cg_crosshairsize 35;cg_drawcrosshair 11\"",
		"seta weapPML \"weapon 1;cg_crosshairsize 30;cg_drawcrosshair 6\"",
		"seta weapSG \"weapon 3;cg_crosshairsize 40;cg_drawcrosshair 7\""
	);

	@Test
	void testTest() {
		input.forEach(entriesFormatter::addEntry);

		String expected = String.join(System.lineSeparator(), Arrays.asList(
			"bind MOUSE2 \"+moveup\"",
			"bind MOUSE3 \"+button2\"",
			"bind MOUSE4 \"vstr weapPML\"",
			"",
			"seta cg_ammoWarning \"1\"",
			"seta cg_autoAction \"0\"",
			"seta cg_autoswitch \"0\"",
			"",
			"seta g_teamAutoJoin \"0\"",
			"seta g_teamForceBalance \"0\"",
			"seta m_accel \"0\"",
			"seta match_readypercent \"51\"",
			"seta model \"doom/pm\"",
			"",
			"seta r_picmip \"6\"",
			"seta r_textureMode \"GL_LINEAR_MIPMAP_LINEAR\"",
			"seta r_vertexLight \"0\"",
			"seta r_width \"1920\"",
			"",
			"seta weapMG \"weapon 2;cg_crosshairsize 35;cg_drawcrosshair 11\"",
			"seta weapPML \"weapon 1;cg_crosshairsize 30;cg_drawcrosshair 6\"",
			"seta weapSG \"weapon 3;cg_crosshairsize 40;cg_drawcrosshair 7\"",
			""
		));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);
		entriesFormatter.print(writer);
		writer.flush();

		assertEquals(expected, outputStream.toString());
	}
}