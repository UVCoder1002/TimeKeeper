package Manager;

import Stopwatch.StopWatch;

public abstract class StopwatchListener extends TimeListener {
    StopWatch stopWatch;

    protected StopwatchListener(StopWatch stopWatch){
        this.stopWatch = stopWatch;
    }

}