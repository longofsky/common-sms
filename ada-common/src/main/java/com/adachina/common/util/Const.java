package com.adachina.common.util;

/**
 * @Description
 * @Author litianlong
 * @Date 2019-03-22 11:35
 */
public class Const {
	private Const() {
		throw new IllegalStateException("Const Utility class");
	}
	/**
	 * 老kafka集群
	 */
	public static final String KAFKA_SERVER_GROUP_OTHER = "OTHER";
	/**
	 * 支付kafka集群
	 */
	public static final String KAFKA_SERVER_GROUP_ACCT = "ACCT";
	/**
	 * TP kafka集群
	 */
	public static final String KAFKA_SERVER_GROUP_TP = "TP";
	/**
	 * 房源同步kafka集群
	 */
	public static final String KAFKA_SERVER_GROUP_HS = "HS";
	
	public static final String SYSTEM_CONFIG = "systemconfig.properties";
	public static final String SYSTEM_CONFIG_KEY = "confFilePath";
	public static final String KAFKA_CONFIG = "kafka.properties";
	public static final String CACHE_CONFIG = "cache.properties";
	public static final String DISTRIBUTEDLOCK_CONFIG = "lock.properties";
	/**
	 * 日志打印间隔
	 */
	public static final String LOG_INTERVAL = "log_interval";
	/**
	 * 消息发送时间
	 */
	public static final String MESSAGE_SEND_TIME = "messageSendTime";
	public static final String MESSAGE_DATA = "messageData";
	public static final String MSG_CONSUMER_INTERVAL = "msg_consumer_interval";
	/**
	 * 配置动态加载间隔
	 */
	public static final String CONFIG_INTERVAL = "config_interval";
	public static final String PERFORMANCE_INTERVAL = "performance_interval";
	/**
	 * 检测consumer多久未有消息消费 
	 */
	public static final String CHECK_CONSUMER_INTERVAL = "check_consumer_interval";
	/**
	 * 检测配置地址是否有变动 
	 */
	public static final String CHECK_KAFKA_PATH_KEY = "check_kafka_path_key";
	/**
	 * 检测配置地址是否有变动
	 */
	public static final String CHECK_KAFKA_ZK_PATH = "check_kafka_zk_path_key";
	/**
	 * 消息发送失败时存储到本地文件目录
	 */
	public static final String FILE_DIR = "fileDir";
	
	public static final String IS_DISK = "is_disk";
	public static final String IS_PACKAGE_MSG= "is_package_msg";
	public static final String KAFKA_SERVER_GROUP = "kafka_server_group";
	public static final String KAFKA_SERVER = "kafka_server_";
	public static final String KAFKA_ZOOEEEPER = "kafka_zookeeper_";
	public static final String TOPIC_KEY = "topic_key_";
	public static final String GROUP_KEY = "group_key_";
	/**
	 * 存入redis中的key 白名单
	 */
	public static final String REDIS_WHITE_LIST = "redis_white_list";
	public static final String TOKEN_REDIS_WHITE_LIST = "token_redis_white_list";
	/**
	 * memcache地址
	 */
	public static final String MEMCACHE_SERVERS = "memcache_servers";
	public static final String TOKEN_MEMCACHE_SERVERS = "token_memcache_servers";
	/**
	 * 存cache失效时间 单位：s
	 */
	public static final String EXPIRED_TIME = "expired_time";
	public static final String TOKEN_EXPIRED_TIME = "token_expired_time";
	public static final String MAX_EXPIRED_TIME = "max_expired_time";
	public static final String TOKEN_MAX_EXPIRED_TIME = "token_max_expired_time";
	public static final String TOKEN_CHANNEL = "token_channel";
	/**
	 * 连接超时时间
	 */
	public static final String CONNECT_TIMEOUT = "connect_timeout";
	public static final String TOKEN_CONNECT_TIMEOUT = "token_connect_timeout";
	/**
	 * 连接池数量
	 */
	public static final String POOL_SIZE = "pool_size";
	public static final String TOKEN_POOL_SIZE = "token_pool_size";
	/**
	 * redis地址
	 */
	public static final String REDIS_SERVERS = "redis_servers";
	public static final String TOKEN_REDIS_SERVERS = "token_redis_servers";
	/**
	 *	控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	 */
	public static final String MAX_ACTIVE = "max_active";
	public static final String TOKEN_MAX_ACTIVE = "token_max_active";
	/**
	 * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
	 */
	public static final String MAX_IDLE = "max_idle";
	public static final String TOKEN_MAX_IDLE = "token_max_idle";
	/**
	 * 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException。
	 */
	public static final String MAX_WAIT = "max_wait";
	public static final String TOKEN_MAX_WAIT = "token_max_wait";
	/**
	 * 当调用borrow Object方法时，是否进行有效性检查
	 */
	public static final String TEST_ON_BORROW = "test_on_borrow";
	public static final String TOKEN_TEST_ON_BORROW = "token_test_on_borrow";
	/**
	 * 当调用return Object方法时，是否进行有效性检查
	 */
	public static final String TEST_ON_RETURN = "test_on_return";
	public static final String TOKEN_TEST_ON_RETURN = "token_test_on_return";
	
	public static final String LOCK_SERVERS = "lock_servers";
	
	public static final String MAX_TIMEOUT = "max_timeout";
	
	/**
	 * 数据结构初始化大小
	 */
	public static final Integer DS_INITIAL_SIZE_MAP = 16;
}
