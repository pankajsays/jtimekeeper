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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pankaj.says.jtimekeeper.util.Config;

class JTimeRecorderProcessor {
	private Map<String, JTimeRecorder> timeRecorderMap;
	private int mapClearCount = 0;

	public JTimeRecorderProcessor(int mapSize) {
		timeRecorderMap = new ConcurrentHashMap<String, JTimeRecorder>(mapSize);
	}

	public void recordTime(String transactionId, boolean firstTime) {
		JTimeRecorder timeKeeper = getTimeKeeper(transactionId);
		timeKeeper.addTimeStat("INIT", System.currentTimeMillis());
	}

	public void recordTime(String transactionId, String place) {
		JTimeRecorder timeKeeper = getTimeKeeper(transactionId);
		timeKeeper.addTimeStat(place, System.currentTimeMillis());
	}

	public void recordAttrribute(String transactionId, String attribute) {
		JTimeRecorder timeKeeper = getTimeKeeper(transactionId);
		timeKeeper.saveAttribute(attribute);
	}

	public String getTimeStats(String transactionId) {
		JTimeRecorder timeKeeper = getTimeKeeper(transactionId);
		timeRecorderMap.remove(transactionId);
		return timeKeeper.getStats(timeRecorderMap.size());
	}

	private JTimeRecorder getTimeKeeper(String transactionId) {
		JTimeRecorder timeKeeper = timeRecorderMap.get(transactionId);
		if (timeKeeper == null) {
			checkBucketSize();
			timeKeeper = new JTimeRecorder(transactionId);
			timeRecorderMap.put(transactionId, timeKeeper);
		}

		return timeKeeper;
	}

	private void checkBucketSize() {
		long currentTime = System.currentTimeMillis();
		@SuppressWarnings("unused")
		int entriesRemoved = 0;
		for (JTimeRecorder timeKeeper : timeRecorderMap.values()) {
			if (timeKeeper.getLastAccessTime() + (Config.TIME_BETWEEN_MAP_CLEANUP * 1000) > currentTime) {
				timeRecorderMap.remove(timeKeeper.getTransctionId());
				entriesRemoved++;
			}
		}
		
		int currentMapSize = timeRecorderMap.size();

		if (currentMapSize > JTimeKeeper.getMaxMapSize()) {
			mapClearCount++;
			if (mapClearCount > Config.DEFAULT_MAP_CLEAR_COUNT) {
				JTimeKeeper.setStatEnabled(false);
				System.err.println("TimeRecorder disabled. Map size Limit: " + JTimeKeeper.getMaxMapSize() + ", current Map Size: "
						+ currentMapSize);
				timeRecorderMap.clear();
			}
			else{
				System.err.println("TimeRecorder map cleared. Attempt:" + mapClearCount + ", Map size Limit:" + JTimeKeeper.getMaxMapSize() + ", current Map Size:"
						+ currentMapSize);
				
			}
		}
	}

}
