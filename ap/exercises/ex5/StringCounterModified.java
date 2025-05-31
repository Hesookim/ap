package ap.exercises.ex5;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StringCounterModified {
    private List<Counter> counterList;

    public StringCounterModified() {
        this.counterList = new ArrayList<>();
    }

    public void add(String item) {
        for (Counter ic : counterList) {
            if (ic.item.equals(item)) {
                ic.count++;
                return;
            }
        }
        counterList.add(new Counter(item, 1));
    }

    public List<Counter> getTop(int k) {
        return counterList.stream().sorted((a, b) -> Integer.compare(b.count, a.count)).limit(k).collect(Collectors.toList());
    }
}