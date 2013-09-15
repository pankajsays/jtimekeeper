/**
-----------------------------------------------------------------------------
jtimekeeper is available for use under the following license, commonly known
as the 3-clause (or "modified") BSD license:
-----------------------------------------------------------------------------
Copyright (c) 2013, Pankaj Sharma (pankaj.says@gmail.com)
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:
1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. The name of the author may not be used to endorse or promote products
   derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR 'AS IS' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
-----------------------------------------------------------------------------
**/
package pankaj.says.jtimekeeper;

import pankaj.says.jtimekeeper.util.Config;
import pankaj.says.jtimekeeper.util.Constants;

public class JTimeKeeper {

	private static boolean statEnabled;
	private static int maxMapSize;

	private static JTimeRecorderProcessor processor;

	//@formatter:off
	static{
		try { statEnabled = Boolean.valueOf(System.getProperty(Constants.STAT_ENABLED_KEY)); } catch(Exception e) {statEnabled = false;}
		try { maxMapSize = Integer.valueOf(System.getProperty(Constants.STAT_BUCKET_SIZE));} catch(Exception e) {maxMapSize = Config.DEFAULT_MAP_SIZE;}
		init();
	}
	//@formatter:off

	
	public static void setStatEnabled(boolean statEnabled){
		JTimeKeeper.statEnabled = statEnabled;
	}
	
	public static int getMaxMapSize() {
		return maxMapSize;
	}

	private static void init() {
		if (statEnabled)
			processor = new JTimeRecorderProcessor(maxMapSize);
	}

	public static void recordTime(String transactionId, boolean firstTime) {
		if (statEnabled) {
			try { processor.recordTime(transactionId, firstTime); } catch (Exception e) { e.printStackTrace(); }
		}
	}

	public static void recordTime(String transactionId, String place) {
		if (statEnabled) {
			try { processor.recordTime(transactionId, place); } catch (Exception e) { e.printStackTrace(); }
		}
	}

	public static void recordAttrribute(String transactionId, String attribute) {
		if (statEnabled) {
			try { processor.recordAttrribute(transactionId, attribute); } catch (Exception e) { e.printStackTrace(); }
		}
	}

	public static String getTimeStats(String transactionId) {
		if (statEnabled) {
			try { return processor.getTimeStats(transactionId); } catch (Exception e) { e.printStackTrace(); }
		}
		return null;
	}

}
