package de.thu.hallomad;

import java.util.Arrays;

public class CategoryDatabase {
    /**
     * The category database is holding data for Category grid adapter
     */
    private final static String[] CATEGORIES = {
            "Linux", "BASH", "PHP", "Docker", "HTML", "MySQL", "JavaScript", "DevOps", "Kubernetes"
    };

    /**
     * Sort the category in alphabetic order
     */
    static {
        Arrays.sort(CATEGORIES);
    }

    /**
     * getter to get the category data
     *
     * @return the array of category
     */
    public String[] getCATEGORIES() {
        return CATEGORIES;
    }
}
