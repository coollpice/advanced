package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace{

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX = "<X-";

    private ThreadLocal<TraceId> traceHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceHolder.get();
        long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}",traceId.getId() , addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    private void syncTraceId() {
        TraceId traceId = traceHolder.get();
        if (traceId == null) {
            traceHolder.set(new TraceId());
        }else{
            traceHolder.set(traceId.createNextId());
        }
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus traceStatus, Exception e) {
        long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - traceStatus.getStartTimeMs();
        TraceId traceId = traceStatus.getTraceId();

        if (e == null) {
            log.info("[{}] {}{} time={}ms" , traceId.getId(),addSpace(COMPLETE_PREFIX, traceId.getLevel()),traceStatus.getMessage(),resultTimeMs);
        }else{
            log.info("[{}] {}{} time={}ms ex={}" , traceId.getId(),addSpace(EX, traceId.getLevel()),traceStatus.getMessage(),resultTimeMs , e.toString());
        }
        releaseTraceId();
    }

    private void releaseTraceId() {
        TraceId traceId = traceHolder.get();
        if (traceId.isFirstLevel()) {
            traceHolder.remove();
        }else{
            traceHolder.set(traceId.createPreviousId());
        }
    }

    private String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|    ");
        }
        return sb.toString();
    }
}
