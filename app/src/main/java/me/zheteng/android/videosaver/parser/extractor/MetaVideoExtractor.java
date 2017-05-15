package me.zheteng.android.videosaver.parser.extractor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import me.zheteng.android.videosaver.parser.Extractor;
import me.zheteng.android.videosaver.parser.ParserUtils;
import me.zheteng.android.videosaver.parser.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 从Meta信息里抽取视频地址
 */
public class MetaVideoExtractor implements Extractor {
    @NonNull
    @Override
    public List<Video> extract(String html) {
        Pattern pattern = Pattern.compile("<meta([^>]+property=\"([^\"]*)\"[^>]+content=\"([^\"]*)\"|"
                        + "[^>]+content=\"([^\"]*)\"[^>]+property=\"([^\"]*)\")[^>]*>",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);
        Map<String, String> hits = new HashMap<>();
        while (matcher.find()) {
            if (matcher.group(2) != null) {
                hits.put(matcher.group(2), matcher.group(3));
            } else if (matcher.group(5) != null) {
                hits.put(matcher.group(5), matcher.group(4));
            }
        }

        String videoUrl = findVideoUrl(hits, "og:video:url");
        if (ParserUtils.isEmpty(videoUrl)) {
            videoUrl = findVideoUrl(hits, "og:video");
        }
        if (ParserUtils.isEmpty(videoUrl)) {
            videoUrl = findVideoUrl(hits, "og:video:secure_url");
        }

        List<Video> list = new ArrayList<>();
        if (!ParserUtils.isEmpty(videoUrl)) {
            list.add(new Video(videoUrl));
        }

        return list;

    }

    @Nullable
    private String findVideoUrl(Map<String, String> hits, String key) {
        String videoUrl = null;
        for (Map.Entry<String, String> entry : hits.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(key)) {
                videoUrl = entry.getValue();
                break;
            }
        }
        return videoUrl;
    }
}
