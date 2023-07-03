package com.poethan.hearthstoneclassic.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConstants {
    public static final int STATUS_INIT = 0;
    public static final int STATUS_VALID = 1;

    // 空闲状态
    public static final int ACTIVE_FREE = 1;
    public static final int ACTIVE_SEARCHING = 2;
    public static final int ACTIVE_IN_COMBAT = 3;
    // 不接受任何请求
    public static final int ACTIVE_BUSY = 4;

    public static final String USER_NAME_SYSTEM = "SYSTEM";
}
