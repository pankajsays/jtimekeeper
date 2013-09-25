package pankaj.says.jtimekeeper;

import org.junit.Test;

public class JTimeKeeperTest {

	@Test
	public void test() throws InterruptedException {

		System.setProperty("jtimekeeper.enabled", "true");

		// Test Transaction Id
		String transactionId = "999";

		// first line of processing to record timestamp of start of processing
		JTimeKeeper.recordTime(transactionId, "processing-start");

		// ..
		// .. some processing and validation
		// .. Assume it took 43 milliseconds.
		Thread.sleep(43);
		// ..

//		// ..
//		// .. after validation end, another call to record time
//		// .. to capture the timestamp.
//		JTimeKeeper.recordTime(transactionId, "validation-end");
//		// ..
//
//		// ..
//		// .. some more processing and possible data retrieval
//		Thread.sleep(178);
//		// ..
//
//		// ..
//		// .. after data retrieval, some more processing
//		JTimeKeeper.recordTime(transactionId, "data-retrieval-end");
//		// ..
//
//		// ..
//		// .. some more processing and final response preparation.
//		Thread.sleep(84);
//		// ..

		// .. recording some additional attributes which we want to get in final
		// .. log message.
		JTimeKeeper.recordAttrribute(transactionId, "consumer:" + "985");
		// ..

		// .. Last line of processing we get the time statistics and prints the
		// .. same to the console
		System.out.println(JTimeKeeper.getTimeStats(transactionId));

	}

}
