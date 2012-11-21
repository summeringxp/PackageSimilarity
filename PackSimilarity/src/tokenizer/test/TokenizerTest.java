package tokenizer.test;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tokenizer.Config;
import tokenizer.TokenJava;
import tokenizer.Tokenizer;

/**
 * The Class TokenizerTest.
 *
 * @author Jiachen YANG &lt;jc-yang@ist.sjtu.edu.cn&gt;
 */
public class TokenizerTest {
	static{
		System.setProperty("org.slf4j.simplelogger.defaultlog", "debug");
	}
	
	private Tokenizer tokenizer = null;
	private List<TokenJava> v = null;
	private Logger logger = LoggerFactory.getLogger(TokenizerTest.class);

	@Before
	public void setUp() {
		tokenizer = new Tokenizer();
	}

	/**
	 * Test tokenize1.
	 *
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testTokenize1() throws FileNotFoundException, IOException {
		v = tokenizer.tokenize(Config.list[0], new FileInputStream(Config.list[0])); // io.c
		
		int count=1;
		for (TokenJava token : v) {
			logger.debug("{}: {}", count++, token);
		}
		assertEquals(1893, v.size());
	}

	/**
	 * Test jgments.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testJgments() throws IOException{
		long start=System.currentTimeMillis();
		for(String path: Config.list){
			tryTokenizer(path);
		}
		long end=System.currentTimeMillis();
		System.out.println(end-start);
	}
	
	@Test
	public void testJgments2() throws IOException{
		long start=System.currentTimeMillis();
		
		tryTokenizer("test/FileAlterationObserver.java");
		
		long end=System.currentTimeMillis();
		System.out.println(end-start);
	}
	
	@Test
	public void testJgments3() throws IOException{
		long start=System.currentTimeMillis();
		
		tryTokenizer("test/PreparedStatement.java");
		
		long end=System.currentTimeMillis();
		System.out.println(end-start);
	}
	
	
	public void tryTokenizer(String filepath) throws IOException{
		int v=tokenizer.getFileHash(filepath);

		System.out.println(v);
	}

}
