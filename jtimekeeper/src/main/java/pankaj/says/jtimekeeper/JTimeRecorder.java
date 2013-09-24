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

import java.util.ArrayList;
import java.util.List;

import pankaj.says.jtimekeeper.util.Config;

class JTimeRecorder {

	private String transctionId;
	private List<TimePlacePair> timeplaceList;
	private List<String> attributeList;
	private long lastAccessedTime;

	public JTimeRecorder(String transactionId) {
		this.transctionId = transactionId;
		timeplaceList = new ArrayList<JTimeRecorder.TimePlacePair>();
		attributeList = new ArrayList<String>();
		lastAccessedTime = System.currentTimeMillis();
	}

	public void addTimeStat(String place, long time) {
		timeplaceList.add(new TimePlacePair(place, time));
		lastAccessedTime = time;
	}

	public void saveAttribute(String attribute) {
		attributeList.add(attribute);
	}

	public String getStats(int currentMapSize) {
		
		addTimeStat("getStats", System.currentTimeMillis());
		
		StringBuilder result = new StringBuilder("JTimeKeeper[").append(currentMapSize).append("]");
		result.append(Config.DEFAULT_DELIMITER).append(transctionId)
				.append(Config.DEFAULT_DELIMITER);

		if(timeplaceList.size() ==0){
			result.append("ERR.EMPTY");
			return result.toString();
		}
		result.append("OK").append(Config.DEFAULT_DELIMITER);
	
		for (String attribute : attributeList) {
			result.append(attribute).append(Config.DEFAULT_DELIMITER);
		}

		long totalTime = 0;
		long lastTime = 0;
		long timeDifference;
		for (TimePlacePair timePlacePair : timeplaceList) {

			timeDifference = timePlacePair.time - lastTime;
			result.append(timePlacePair.place).append(Config.DEFAULT_DELIMITER)
					.append(timeDifference).append(Config.DEFAULT_DELIMITER);

			totalTime += timeDifference;
			lastTime = timePlacePair.time;
		}
		totalTime -= timeplaceList.get(0).time;
		return result.append("TOTAL").append(Config.DEFAULT_DELIMITER)
				.append(totalTime).toString();
	}
	
	public long getLastAccessTime(){
		return lastAccessedTime;
	}

	public String getTransctionId() {
		return transctionId;
	}

	private class TimePlacePair {
		public TimePlacePair(String place, long time) {
			this.place = place;
			this.time = time;
		}

		String place;
		long time;
	}
}
