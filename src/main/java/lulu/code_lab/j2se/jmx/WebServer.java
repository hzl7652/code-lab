package lulu.code_lab.j2se.jmx;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class WebServer implements WebServerMBean {

	private String logLevel;
	
	@Override
	public int getPort() {
		return 0;
	}

	@Override
	public String getLogLevel() {
		return logLevel;
	}

	@Override
	public void setLogLevel(String level) {
		this.logLevel = level;
	}

	@Override
	public boolean isStarted() {
		return false;
	}

	@Override
	public void stop() {
	}

	@Override
	public void start() {

	}

	public static void main(String[] args) throws Exception, MBeanRegistrationException, NotCompliantMBeanException,
			MalformedObjectNameException, NullPointerException {
		WebServer ws = new WebServer();
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		server.registerMBean(ws, new ObjectName("myapp:type=webserver,name=Port 8080"));
		
		while(true){}

	}

}
