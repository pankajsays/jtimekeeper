# Introduction #

JTimekeeper can be easily integrated with any java based application.
The steps for integration are given below:

# Details #

JTimekeeper uses following system properties to configure itself.
```
jtimekeeper.enabled
jtimekeeper.map.size
```

| **jtimekeeper.enabled** | This property must have values either true or false. if it is true, then the jtimekeeper is enabled and initialize itself when the class loads in memory. For all other values, the jtimekeeper remains disabled. |
|:------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **jtimekeeper.map.size** | This is an optional property. It has a default value of 1,200. It can be used to specify the maximum number of statistics it can keep in memory at any given time. For example, if this property has value specified 5, and at a given time, we want to record the statistics from say 6 parallel transactions, for the sixth transction, jtimekeeper will try to cleanup the map to see if there are any old statistics entry (not updated in last nnn seconds, if yes, it removes them from the map and save the new entry. if it is unable to reduce the map size, it disables itself.  |


### Step 1 ###
Add the jtimekeeper.jar file to the project in which you want to utilize jtimekeeper.
### Step 2 ###
Once you have added the jar file to the project build path, now you can add the following statements in the code.
  * Let's consider for a transaction for which you want to measure time statistics, the first line of code that is executed in Class1. The control goes from Class1 to Class2 and so on. Let's assume the last line of code that is executed is in Class5.
  * We will need to know a common id which generally is transaction id in all places of code where we will can jtimekeeper API.

  * In Class1, add the following line:
> > `TimeRecorder.recordTime(transactionId, "processing-start");`


> You can change the label name "processing-start" label to your liking.

  * In Class5, before the line line of the code which is executed for a given transaction, add the following line:
> > `System.out.println(TimeRecorder.getTimeStats(transactionId));`
> > In place of SysOut, you can use a logger as well.

  * With only these two statements, you will get something like this in the console or logger:
> > `JTimeKeeper[0]^999^OK^processing-start^1380080318845^getStats^306^TOTAL^306`


> Here, 999 is transaction id, the big number after processing-start is time in milliseconds for first call to recordTime method for given transaction id, 306 millisecond is the time elapsed between first call to recordTime and getStats method.

  * Now you can add as many recordTime method calls for a given transaction id. Also you can add a method call to recordAttribute method like:
> > `JTimeKeeper.recordAttrribute(transactionId, "consumer:" + "985");`


> Here it is simply storing the attribute passed which is appended in the final String retured by getStats method.

  * A typical log message from JTimeKeeper looks like this:

> `JTimeKeeper[0]^999^OK^consumer:985^processing-start^1380080686923^validation-end^44^data-retrieval-end^178^getStats^84^TOTAL^306`

> In the above message, we have an attribute - "consumer:985"
> Time of request validation - 44 ms, Time of data retrieval - 178 ms, Time of response preparation - 84 ms, Total time of processing of request - 306 ms.


### Application Configuration ###
In the application in which you are trying to integrate the jtimekeeper, add the following system property:
> ` jtimekeeper.enabled=true`

### Thats It. ###