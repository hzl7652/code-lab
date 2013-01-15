package lulu.code_lab.j2se.jmx;
public interface WebServerMBean {
    public int getPort();

    public String getLogLevel();
    public void setLogLevel(String level);

    public boolean isStarted();
    public void stop();
    public void start();
}