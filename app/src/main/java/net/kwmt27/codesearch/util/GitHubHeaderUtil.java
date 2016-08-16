package net.kwmt27.codesearch.util;

import android.net.Uri;

import java.util.List;
import java.util.Map;

public class GitHubHeaderUtil {

    public static int extractLink(Map<String, List<String>> headerMap, String rel) {
        if (headerMap == null || !headerMap.containsKey("link")) {
            return 0;
        }
        List<String> values = headerMap.get("link");
        if (values.size() > 0) {
            String linkValue = values.get(0);
            String[] splitLinkValue = linkValue.split(",");
            for (String v : splitLinkValue) {
                if (v.contains(rel)) {
                    v = v.trim();
                    int startIndex = v.indexOf("<");
                    int endIndex = v.indexOf(">");
                    String url = v.substring(startIndex, endIndex);
                    Uri uri = Uri.parse(url);
                    String pageString = uri.getQueryParameter("page");
                    return Integer.valueOf(pageString);
                }
            }
        }
        // ここまでこないはず
        return 0;
    }

}
