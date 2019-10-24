package io.rainrobot.maxdispachreq;

import java.time.LocalDateTime;

public interface IExperationSchdualer {
    void setExperation(LocalDateTime experation, Runnable runnable);
}
