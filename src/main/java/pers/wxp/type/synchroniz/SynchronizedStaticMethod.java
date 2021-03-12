package pers.wxp.type.synchroniz;

/**
 * @author wxp
 * @date 2017年10月11日 上午9:42:50
 * @Description: TODO(静态方法是属于类的而不属于对象的。同样的，synchronized修饰的静态方法锁定的是这个类的所有对象。)
 */
public class SynchronizedStaticMethod {

}

/**
 * @author wxp
 * @date 2017年10月11日 上午9:44:39
 * @Description: TODO(syncThread1和syncThread2是SyncThread的两个对象，
 *               但在thread1和thread2并发执行时却保持了线程同步。这是因为run中调用了静态方法method，而静态方法是属于类的
 *               ，所以syncThread1和syncThread2相当于用了同一把锁。)
 */
class SyncThread implements Runnable {
	private static int count;

	public SyncThread() {
		count = 0;
	}

	public synchronized static void method() {
		for (int i = 0; i < 5; i++) {
			try {
				System.out.println(Thread.currentThread().getName() + ":" + (count++));
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void run() {
		method();
	}

	public static void main(String[] args) {
		SyncThread syncThread1 = new SyncThread();
		SyncThread syncThread2 = new SyncThread();
		Thread thread1 = new Thread(syncThread1, "SyncThread1");
		Thread thread2 = new Thread(syncThread2, "SyncThread2");
		thread1.start();
		thread2.start();
	}
}