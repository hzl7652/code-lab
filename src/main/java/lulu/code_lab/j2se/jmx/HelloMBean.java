package lulu.code_lab.j2se.jmx;
public interface HelloMBean { 
 
    public void sayHello(); 
    public int add(int x, int y); 
    
    public String getName(); 
     
    public int getCacheSize(); 
    public void setCacheSize(int size);
    
    public JavaBean getJavaBean();
} 