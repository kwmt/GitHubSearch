package net.kwmt27.codesearch.model;

/**
 * https://developer.github.com/v3/repos/#list-your-repositories
 */
public enum SortType {

    Created("created"),
    Updated("updated"),
    Pushed("pushed"),
    FullName("full_name");

    private String type;

    SortType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
