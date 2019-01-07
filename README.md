This is a tool that support many JVM running code in different computer. that thread can be synchronized.

following step.

1. add DisCur-1.0.5.jar to your class path.
2. DisCur.json configure in your class path.
	{
		"IPs":["IP1","IP2"],  //thread synchronzied server IPs
		"port":2000,  // synchronzied server listenning port
		"scanServerIntervalms":1000 // as client, detect server loop time length
	}
3. Coding 
	DisCur.init();//just call once
	DisCur.lock("keyStr");// here we use String as lock object
	try{
		DisCur.myWait();// current thread wait. currently only support one layer thread.
		// DisCur.myWait(1000);
		// DisCur.myWait();
		// DisCur.myNotifyAll();
		// DisCur.myNotify();
	}finally{
		DisCur.unlock("keyStr");
	}