package com.sixlab.logistics.common.shared.security;

public enum Role {
    DELIVERY_AGENT(Authority.DELIVERY_AGENT, 1),
    TRADE_PARTNER(Authority.TRADE_PARTNER, 1),
    HUB_MANAGER(Authority.HUB_MANAGER, 3),
    MASTER(Authority.MASTER, 4);

    private final String authority;
    private final int level; //역할의 순위

    Role(String authority, int level) {
        this.authority = authority;
        this.level = level;
    }

    public static boolean isGreaterThen(Role role, Role currentUserRole) {
        if (role == null || currentUserRole == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        return role.level > currentUserRole.level;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String DELIVERY_AGENT = "ROLE_DELIVERY_AGENT";
        public static final String TRADE_PARTNER = "ROLE_TRADE_PARTNER";
        public static final String HUB_MANAGER = "ROLE_HUB_MANAGER";
        public static final String MASTER = "ROLE_MASTER";
    }

    public static Role fromAuthority(String authority) {
        for (Role role : Role.values()) {
            if (role.getAuthority().equals(authority)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown authority: " + authority);
    }
}
