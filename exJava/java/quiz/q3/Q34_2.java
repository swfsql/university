import java.util.*;
import java.io.*;

public class Q34_2 {
  public static void main(String args[]) throws Exception {
    List<HeavyWorker> workerList = new ArrayList<HeavyWorker>();
    for (int i = 0; i < 3; i++) {
      HeavyWorker heavyWorker = new HeavyWorker("pre" + i, "suf" + i);
      workerList.add(heavyWorker);
    }

    List<Result<String>> resultList = WorkerUtil.workAll(workerList);
    for(Result<String> r : resultList) {
      System.out.println(r.getValue());
    }
  }
}

interface Worker<V> {
  public V work();
}

class Result<V> {
  private V value;

  public void setValue(V value) {
    this.value = value;
  }

  public V getValue() {
    return value;
  }
}

class HeavyWorker implements Worker<String> {
  protected String arg1;
  protected String arg2;

  public HeavyWorker(String arg1, String arg2) {
    this.arg1 = arg1;
    this.arg2 = arg2;
  }

  public String work() {
    return "Heavy : " + arg1 + arg2;
  }
}

// sends: workerList (List<heavyWorker>)
// returns: List<Result<String>>

class WorkerUtil {
  public static <T extends String> List<Result<T>> workAll(Collection<? extends Worker<T>> tasks) {
    List<Result<T>> resultList = new ArrayList<Result<T>>();
    for (Worker<T> t : tasks) {
      T value = t.work();
      Result<T> result = new Result<T>();
      result.setValue(value);
      resultList.add(result);
    }
    return resultList;
  }
}

