# Introduction #

jtimekeeper is a light weight utility for capturing response time statistics of a java based application.
it has a very small footprint and it has been deployed in production environments.
It can be called by invoking method as:

`JTimeKeeper.recordTime(transactionId, "VALIDATION");`

It needs a unique identifier such as transaction id to keep track of recorded statistics associated with each request.
Jtimekeeper can be integrated with the application by simply adding the jar file to the classpath.

# Usage Example #

In a sample usage scenario, put the following statement in the code as:

`JTimeKeeper.recordTime(transactionId, "LABEL");`

if you put this statement at the beginning where the transaction processing starts say print A, at point B, at point C and finally at point D where the transaction processing completes. Let's assume that we used string "A", "B", "C", etc, in place of LABEL in above example usage.

Now we can get the recorded statistics by calling the method:

`JTimeKeeper.getTimeStats(transactionId)`

getTimeStats method should be the last statement of processing. In the above example, it should be called at point D.

JTimekeeper provides statistics in an easy to import format which can be analyzed in a spreadsheet program such as Excel.
A sample performance statistics logging output of jtimekeeper as used in above example is:

```
JTimeKeeper[0]^100^OK^A^1378241891008^B^73^C^5^getStats^78^TOTAL^156
```

In the above example:
| **Field** | **Value** | **Meaning** |
|:----------|:----------|:------------|
| Log Statement Identifier| `JTimeKeeper[0]` | JTimeKeeper is fixed String for every log message so that it can be used in grep. <br>Zero in square bracket shows size of current bucket where time statistics is saved. <br>
<tr><td> Current Jtimekeeper Bucket Size</td><td><code>100</code> </td><td>It shows the max size of the HashMap where the statistics are stored </td></tr>
<tr><td> Fixed Value (OK or ERR)</td><td><code>OK</code> </td><td>It shows that JtimeKeeper worked as expected. Always "OK" in current version. This field is for future use </td></tr>
<tr><td> Label </td><td> <code>A</code> </td><td> Label string passed to first call to recordTime method for given transaction id. </td></tr>
<tr><td> Time of first recordTime method call </td><td> <code>1378241891008</code> </td><td> This field has millisecond value of time when recordTime method is called for given transaction id. </td></tr>
<tr><td> Label </td><td> <code>B</code> </td><td> Label string passed to second call to recordTime method for given transaction id. </td></tr>
<tr><td> Time-Stat </td><td> <code>73</code> </td><td> This field has the time in milliseconds between call to recordTime at point B and point A </td></tr>
<tr><td> Label </td><td> <code>C</code> </td><td> Label string passed to third call to recordTime method for given transaction id. </td></tr>
<tr><td> Time-Stat </td><td> <code>5</code> </td><td> This field has the time in milliseconds between call to recordTime at point C and point B </td></tr>
<tr><td> Fixed Label</td><td><code>getStats</code> </td><td>This field is a fixed label </td></tr>
<tr><td> Time-Stat </td><td> <code>78</code> </td><td> This field has the time in milliseconds between call to recordTime at point D and point C </td></tr>
<tr><td> Fixed Label</td><td><code>TOTAL</code> </td><td>This field is a fixed label, it precedes the total of time of processing.  </td></tr>
<tr><td> Final Time-Stat </td><td> <code>156</code> </td><td> This field has the time in milliseconds between getStats method and first call to recordTime method for given transaction id </td></tr>