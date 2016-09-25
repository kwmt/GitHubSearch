package net.kwmt27.codesearch.entity.payloads;

import java.util.ArrayList;
import java.util.List;

public class GollumEntity {
    private List<PageEntity> mPages = new ArrayList<>();

    public List<PageEntity> getPages() {
        if(mPages == null) {return new ArrayList<>();}
        return mPages;
    }
}
