/**
 * 
 */
package com.adachina.common.util.disseq;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @Author litianlong
 * @Date 2019-03-22 11:26
 */
public class DisSequnceUtil {
	private static final Logger logger = LoggerFactory.getLogger(DisSequnceUtil.class);

	private final long workerId;
	private final static long TWEPOCH = 1361753741828L;
	private long sequence = 0L;
	private final static long WORKERID_BITS = 4L;
	public final static long MAX_WORKER_ID = -1L ^ -1L << WORKERID_BITS;
	private final static long SEQUENCE_BITS = 10L;
	private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
	private final static long TIMES_TAMPLET_SHIFT = SEQUENCE_BITS + WORKERID_BITS;
	public final static long SEQUENCE_MASK = -1L ^ -1L << SEQUENCE_BITS;
	private long lastTimestamp = -1L;

	public DisSequnceUtil(final long workerId) {
		super();
		if (workerId > DisSequnceUtil.MAX_WORKER_ID || workerId < 0) {
			throw new IllegalArgumentException(
					String.format("worker Id can't be greater than %d or less than 0", DisSequnceUtil.MAX_WORKER_ID));
		}
		this.workerId = workerId;
	}
	public synchronized long nextId() {
		long timestamp = this.timeGen();
		if (this.lastTimestamp == timestamp) {
			this.sequence = (this.sequence + 1) & DisSequnceUtil.SEQUENCE_MASK;
			if (this.sequence == 0) {
				timestamp = this.tilNextMillis(this.lastTimestamp);
			}
		} else {
			this.sequence = 0;
		}
		if (timestamp < this.lastTimestamp) {
			logger.warn(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
					this.lastTimestamp - timestamp));
		}
		this.lastTimestamp = timestamp;
		long nextId = (timestamp - TWEPOCH << TIMES_TAMPLET_SHIFT) | (this.workerId << DisSequnceUtil.WORKER_ID_SHIFT)
				| (this.sequence);
		return nextId;
	}

	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}
	
	public void testTilNextMillis(long lastTimestamp){
		tilNextMillis(lastTimestamp);
	}
}
