
import org.junit.Test;

public class A {

  @Test
  public void test() throws InterruptedException {
    foo();
    Thread.sleep(1000); // Noncompliant {{Remove this use of "Thread.sleep()".}}
    bar();
  }

}
