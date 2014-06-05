package jadx.tests.internal.trycatch;

import jadx.api.InternalJadxTest;
import jadx.core.dex.nodes.ClassNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class TestTryCatch4 extends InternalJadxTest {

	public static class TestCls {
		private Object test(Object obj) {
			FileOutputStream output = null;
			try {
				output = new FileOutputStream(new File("f"));
				return new Object();
			} catch (FileNotFoundException e) {
				System.out.println("Exception");
				return null;
			}
		}
	}

	@Test
	public void test() {
		ClassNode cls = getClassNode(TestCls.class);
		String code = cls.getCode().toString();
		System.out.println(code);

		assertThat(code, containsString("try {"));
		assertThat(code, containsString("output = new FileOutputStream(new File(\"f\"));"));
		assertThat(code, containsString("return new Object();"));
		assertThat(code, containsString("} catch (FileNotFoundException e) {"));
		assertThat(code, containsString("System.out.println(\"Exception\");"));
		assertThat(code, containsString("return null;"));
		assertThat(code, not(containsString("output = output;")));
	}
}