package xyz.bt31.staffcontrol.core.user;

import xyz.bt31.staffcontrol.api.IUser;
import xyz.bt31.staffcontrol.api.IUserManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager implements IUserManager {

    private final Map<UUID, IUser> users = new ConcurrentHashMap<>();

    @Override
    public Collection<IUser> getAll() {
        return users.values();
    }

    @Override
    public IUser get(UUID id) {
        return users.computeIfAbsent(id, uuid ->
            new User(uuid, org.bukkit.Bukkit.getOfflinePlayer(uuid).getName()));
    }

    public void remove(UUID uuid) {
        users.remove(uuid);
    }
}
