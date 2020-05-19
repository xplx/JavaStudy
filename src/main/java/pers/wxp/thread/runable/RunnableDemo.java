package pers.wxp.thread.runable;

/**
 * 实现runnable方法
 * @author wxp
 *
 */
class RunnableDemo implements Runnable {
	/**属性*/
	private Thread t;
	private String threadName;      

	RunnableDemo(String name) {     //方法
		threadName = name;
		System.out.println("Creating " + threadName);
	}

	/**
	 * @author wxp run表示运行状态
	 */
	@Override
	public void run() {
		System.out.println("Running " + threadName);
		while(true){
			System.out.println("Running " + threadName);
		}
	}
	/**
	 * @author wxp start()就绪状态
	 */
	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

	public static void main(String args[]) {
		RunnableDemo R1 = new RunnableDemo("Thread-1");
		RunnableDemo R2 = new RunnableDemo("Thread-2");
		R1.start();		
		R2.start();
	}
}
