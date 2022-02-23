package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldLogTraceTest {

    LogTrace logTrace = new FieldLogTrace();

    @Test
    void begin_end() {
        TraceStatus start1 = logTrace.begin("start1");
        TraceStatus start2 = logTrace.begin("start2");
        logTrace.end(start2);
        logTrace.end(start1);
    }

    @Test
    void begin_exception() {
        TraceStatus start1 = logTrace.begin("start1");
        TraceStatus start2 = logTrace.begin("start2");
        logTrace.exception(start2, new IllegalStateException());
        logTrace.exception(start1, new IllegalStateException());
    }

}