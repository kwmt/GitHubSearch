package net.kwmt27.githubsearch.model;

public interface ISearchModel {

    String getKeyword();

    int getNextPage();

    boolean hasNextPage();

    void clear();
}
