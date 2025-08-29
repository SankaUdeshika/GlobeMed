package com.jiat.globemed.service;

public interface PermissionComponent {
    void add(PermissionComponent component);
    void remove(PermissionComponent component);
    void showPermissions();
    boolean hasPermission(String action);
}