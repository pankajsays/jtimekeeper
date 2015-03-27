jtimekeeper is a java based lightweight utility which can be used for recording response time statistics of execution of various requests. it prints the statistics in a delimited format which can be easily imported in a spreadsheet program (such as excel) for quick analysis.

It is very lightweight and can be kept enabled in production environments.

```
// Test Transaction Id
String transactionId = "999";

// first line of processing to record timestamp of start of processing
TimeRecorder.recordTime(transactionId, "processing-start");

// ..
// .. some processing 
// .. Assume it took 43 milliseconds.
Thread.sleep(43);
// ..

// .. recording some additional attributes which we want to get in final
// .. log message.
TimeRecorder.recordAttrribute(transactionId, "consumer:" + "985");
// ..

// .. Last line of processing we get the time statistics and prints the
// .. same to the console
System.out.println(TimeRecorder.getTimeStats(transactionId));

```

`JTimeKeeper[0]^999^OK^consumer:985^processing-start^1380082124098^getStats^45^TOTAL^45`

### JTimeKeeper Log Data in a Spreadsheet ###
<img src='https://jtimekeeper.googlecode.com/svn/wiki/images/jtimekeeper3.png' title='jtimekeeper3' />