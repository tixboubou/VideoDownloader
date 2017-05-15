package me.zheteng.android.videosaver.parser;

import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * 工具类
 */
public class ParserUtils {
    /**
     * Copied from {@link TextUtils#isEmpty(CharSequence)}
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     *
     * @return true if str is null or zero length
     *
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        if (str == null || str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

}
