package xyz.bt31.staffcontrol.core.user;

import org.bukkit.entity.Player;
import xyz.bt31.staffcontrol.api.*;

import java.util.*;

public class User implements IUser {

    private final UUID uuid;
    private final String name;
    private VanishType vanishType = VanishType.NONE;
    private short glassColor = 0;
    private boolean frozen = false;
    private boolean chatting = false;
    private boolean staffMode = false;
    private boolean online = true;
    private IGui currentGui;
    private IAction queuedAction;
    private final List<IReport> reports = new ArrayList<>();
    private final List<IWarning> warnings = new ArrayList<>();
    private final List<String> playerNotes = new ArrayList<>();
    private final Map<AlertType, Boolean> alertOptions = new EnumMap<>(AlertType.class);

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setVanishType(VanishType vanishType) {
        this.vanishType = vanishType;
    }

    @Override
    public VanishType getVanishType() {
        return vanishType;
    }

    @Override
    public void setGlassColor(short color) {
        this.glassColor = color;
    }

    @Override
    public short getGlassColor() {
        return glassColor;
    }

    @Override
    public List<IReport> getReports() {
        return reports;
    }

    @Override
    public List<IWarning> getWarnings() {
        return warnings;
    }

    @Override
    public List<String> getPlayerNotes() {
        return playerNotes;
    }

    @Override
    public boolean shouldNotify(AlertType alertType) {
        return alertOptions.getOrDefault(alertType, true);
    }

    @Override
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    @Override
    public boolean isFrozen() {
        return frozen;
    }

    @Override
    public Optional<Player> getPlayer() {
        return Optional.ofNullable(org.bukkit.Bukkit.getPlayer(uuid));
    }

    @Override
    public void setCurrentGui(IGui gui) {
        this.currentGui = gui;
    }

    @Override
    public Optional<IGui> getCurrentGui() {
        return Optional.ofNullable(currentGui);
    }

    @Override
    public void setQueuedAction(IAction action) {
        this.queuedAction = action;
    }

    @Override
    public void addPlayerNote(String s) {
        playerNotes.add(s);
    }

    @Override
    public void addReport(IReport report) {
        reports.add(report);
    }

    @Override
    public void addWarning(IWarning warning) {
        warnings.add(warning);
    }

    @Override
    public void removeWarning(UUID uuid) {
        warnings.removeIf(w -> w.getUuid().equals(uuid));
    }

    @Override
    public IAction getQueuedAction() {
        return queuedAction;
    }

    @Override
    public boolean isChatting() {
        return chatting;
    }

    @Override
    public void setChatting(boolean b) {
        this.chatting = b;
    }

    public boolean isStaffMode() {
        return staffMode;
    }

    public void setStaffMode(boolean staffMode) {
        this.staffMode = staffMode;
    }

    @Override
    public void setAlertOption(AlertType alertType, boolean isEnabled) {
        alertOptions.put(alertType, isEnabled);
    }

    @Override
    public boolean isOnline() {
        return online;
    }

    @Override
    public void setOnline(boolean b) {
        this.online = b;
    }
}
