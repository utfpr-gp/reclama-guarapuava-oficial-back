package br.edu.utfpr.reclamaguarapuava.model;

public enum Profile {
    ADMIN(1, "ROLE_ADMIN"), USER(2, "ROLE_USER"), MANAGER(3, "ROLE_MANAGER");

    private Integer code;
    private String description;

    Profile(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Profile toEnum(Integer code) {
        if (code != null) {
            for (Profile profile : Profile.values()) {
                if (code.equals(profile.code)) {
                    return profile;
                }
            }
        }

        throw new IllegalArgumentException("Profile code invalid");
    }
}
