package net.kwmt27.codesearch.model;

public interface ISearchModel {

    String getKeyword();

    int getNextPage();

    boolean hasNextPage();

    void clear();
}
