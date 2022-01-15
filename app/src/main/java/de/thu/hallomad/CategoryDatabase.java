package de.thu.hallomad;

import java.util.Arrays;

public class CategoryDatabase {
    private final static String[] CATEGORIES = {
            "Linux", "BASH", "PHP", "Docker", "HTML", "MySQL", "JavaScript", "DevOps", "Kubernetes"
    };

    static {
        Arrays.sort(CATEGORIES);
    }

    public String[] getCATEGORIES() {
        return CATEGORIES;
    }
}
